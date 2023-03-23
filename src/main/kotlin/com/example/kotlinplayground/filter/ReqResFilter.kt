package com.example.kotlinplayground.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

@Component
class ReqResFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        fun generateTraceId(): String {
            val traceKey = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            return LocalDateTime.now()
                .format(ofPattern("yyyyMMddHHmmssSSS", Locale.KOREA))
                .let {
                    it + "_" + traceKey.toList().shuffled().take(8).joinToString("")
                }
        }
        val traceId = generateTraceId()
        MDC.put("traceId", traceId)
        val requestWrapper = CachedBodyHttpServletRequest(request)
        requestWrapper.setAttribute("traceId", traceId)
        filterChain.doFilter(requestWrapper, response)
    }

}