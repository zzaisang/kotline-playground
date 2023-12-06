package com.example.kotlinplayground.common.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReflectionTarget(
    val clazz: KClass<*>,
)
