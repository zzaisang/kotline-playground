package com.example.kotlinplayground.common.security

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginUser(val administrator: Boolean = false)
