package com.example.kotlinplayground.common.utils

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(val secretKey: String)
