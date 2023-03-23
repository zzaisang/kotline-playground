package com.example.kotlinplayground.domain

data class ReqResLogging(
    val traceId: String,
    val className: String? = null,
    val httpMethod: String? = null,
    val uri: String? = null,
    val method: String? = null,
    val params: Map<String,Any>? = null,
    val logTime: String? = null,
    val serverIp: String? = null,
    val requestBody: Any? = null,
    val payLoad: Any? = null,
    val elapsedTime: String? = null
)
