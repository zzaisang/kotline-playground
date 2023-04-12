package com.example.kotlinplayground.common.exception

import com.example.kotlinplayground.common.exception.ApiException
import com.example.kotlinplayground.common.exception.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ApiException::class)
    protected fun handleCustomException(
        request: HttpServletRequest,
        exception: ApiException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(exception.errorCode.statusCode)
            .body(
                ErrorResponse(
                    status = exception.errorCode.statusCode,
                    detail = exception.errorCode.message,
                    traceId = MDC.get("traceId"),
                )
            )
    }

}