package com.example.kotlinplayground.common.exception

class ApiException(
    errorCode: ErrorCode
) : RuntimeException() {
    val errorCode: ErrorCode = errorCode
}