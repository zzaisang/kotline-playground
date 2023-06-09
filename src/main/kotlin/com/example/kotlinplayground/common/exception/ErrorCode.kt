package com.example.kotlinplayground.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val statusCode: HttpStatus
) {
    NO_DATA("데이터가 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
}

