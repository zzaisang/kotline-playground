package com.example.kotlinplayground.exception

import org.springframework.http.HttpStatus

class ErrorResponse (
    val status: HttpStatus,
    val detail: String? = null,
    val instance: String? = null,
    val traceId: String? = null
)