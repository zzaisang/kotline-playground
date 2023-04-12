package com.example.kotlinplayground.common.exception

import com.example.kotlinplayground.domain.ErrorCode

class ApiException(
    errorCode: ErrorCode
) : RuntimeException() {
    val errorCode: ErrorCode = errorCode
}