package com.example.kotlinplayground.aspect

import com.example.kotlinplayground.domain.ReqResLogging
import com.example.kotlinplayground.logger.logger
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Aspect
@Component
class ApiReqResAspect(
    private val objectMapper: ObjectMapper
) {

    val log = logger()

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun apiRestPointCut() {
    }

    @Around("apiRestPointCut()")
    fun reqResLogging(joinPoint: ProceedingJoinPoint): Any {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val traceId = request.getAttribute("traceId") as String
        val className = joinPoint.signature.declaringTypeName.split(".").let { it[it.lastIndex] }
        val methodName = joinPoint.signature.name
        val params = getParams(request)

        val serverIp = InetAddress.getLocalHost().hostAddress

        val reqResLogging = ReqResLogging(
            traceId = traceId,
            className = className,
            httpMethod = request.method,
            uri = request.requestURI,
            method = "$methodName()",
            params = params,
            logTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            serverIp = serverIp,
            requestBody = objectMapper.readTree(request.inputStream.readBytes())
        )

        val start = System.currentTimeMillis()
        try {
            val result = joinPoint.proceed()
            val elapsedTime = System.currentTimeMillis() - start
            val elapsedTimeStr = "${elapsedTime}ms"

            val logging = when (result) {
                is ResponseEntity<*> -> reqResLogging.copy(payLoad = result.body, elapsedTime = elapsedTimeStr)
                else -> reqResLogging.copy(payLoad = "{}")
            }
            log.info(objectMapper.writeValueAsString(logging))
            return result
        } catch (e: Exception) {
            log.error("{}", objectMapper.writeValueAsString(reqResLogging))
            throw e
        }
    }

    private fun getParams(request: HttpServletRequest): Map<String, String> {
        val jsonObject = mutableMapOf<String, String>()
        val paramNames = request.parameterNames
        while (paramNames.hasMoreElements()) {
            val paramName = paramNames.nextElement()
            val replaceParam = paramName.replace("\\.", "-")
            jsonObject[replaceParam] = request.getParameter(paramName)
        }
        return jsonObject
    }

}