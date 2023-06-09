package com.example.kotlinplayground.common.filter

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.system.measureTimeMillis

@Component
class LoggingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)
    private val traceId: String = "traceId"
    private val pattern: String = "yyyyMMddHHmmssSSS"
    private val randomStringLength: Long = 4

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)
        val traceId = generateTraceId()

        MDC.put(this.traceId, traceId)

        val elapsed = measureTimeMillis {
            filterChain.doFilter(wrappedRequest, wrappedResponse)
        }

        HttpLog(
            wrappedRequest = wrappedRequest,
            wrappedResponse = wrappedResponse,
            elapsedTime = elapsed
        ).let {
            log.info(ObjectMapper().writeValueAsString(it))
        }

        wrappedResponse.copyBodyToResponse()
    }

    /**
     * Generate a traceId to be exposed to the log together.
     * TraceId consists of four digits in case and number.
     */
    private fun generateTraceId(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..randomStringLength)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
            .let {
                ZonedDateTime.now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern(pattern))
                    .plus(it)
            }
    }
}

data class HttpLog(
    val traceId: String,
    val meta: Meta,
    val httpRequest: HttpRequest,
    val httpResponse: HttpResponse,
) {

    constructor(
        wrappedRequest: ContentCachingRequestWrapper,
        wrappedResponse: ContentCachingResponseWrapper,
        elapsedTime: Long,
    ) : this(
        traceId = MDC.get("traceId"),
        meta = Meta(wrappedRequest),
        httpRequest = HttpRequest(wrappedRequest),
        httpResponse = HttpResponse(wrappedResponse, elapsedTime)
    )

    data class Meta(
        val uri: String,
    ) {

        constructor(wrappedRequest: ContentCachingRequestWrapper) : this(
            uri = wrappedRequest.requestURI
        )
    }

    data class HttpRequest(
        val queryParam: String,
        val body: JsonNode,
    ) {

        constructor(wrappedRequest: ContentCachingRequestWrapper) : this(
            queryParam = with(wrappedRequest) { getQueryParamFromRequest(this) },
            body = ObjectMapper().readTree(wrappedRequest.contentAsByteArray)
        )
    }

    data class HttpResponse(
        val status: Int,
        val elapsed: String,
        val payload: JsonNode,
    ) {

        constructor(
            wrappedResponse: ContentCachingResponseWrapper,
            elapsed: Long,
        ) : this(
            status = HttpStatus.valueOf(wrappedResponse.status).value(),
            elapsed = "$elapsed ms",
            payload = with(wrappedResponse) { getPayloadFromResponse(this) },
        )
    }
}

fun HttpServletRequest.getQueryParamFromRequest(wrappedRequest: ContentCachingRequestWrapper): String {
    return wrappedRequest.parameterMap.takeIf {
        it.isNotEmpty()
    }?.map { (key, value) ->
        "key=$key, value=${value.toList().joinToString(",")}"
    }?.joinToString(",") ?: ""
}

fun HttpServletResponse.getPayloadFromResponse(wrappedResponse: ContentCachingResponseWrapper): JsonNode {
    return ObjectMapper()
        .registerKotlinModule()
        .readTree(wrappedResponse.contentAsByteArray)
}
