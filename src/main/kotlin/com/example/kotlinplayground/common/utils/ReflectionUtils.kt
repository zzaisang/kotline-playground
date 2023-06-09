package com.example.kotlinplayground.common.utils

import com.example.kotlinplayground.common.annotation.NoArg
import com.example.kotlinplayground.common.annotation.ReflectionTarget
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class ReflectionUtils

inline fun <reified T : Any, reified R : Any> convertObject(source: T): R {
    val target = R::class.java.getConstructor().newInstance()
    val sourceFields = T::class.java.declaredFields.associateBy { it.name }
    println(T::class.annotations)
    T::class.memberProperties.forEach {
        println(it.name)
        it.findAnnotation<ReflectionTarget>()
    }
    T::class.java.declaredFields.forEach {
        for (annotation in it.annotations) {
            if (annotation is ReflectionTarget) println(it)
        }
    }
    R::class.java.declaredFields.forEach { targetField ->
        for (annotation in targetField.annotations) {
            if (annotation is NoArg) {
                //                println("${annotation.clazz.simpleName}")
            }
        }
        val sourceField = sourceFields[targetField.name]
        if (sourceField != null) {
            sourceField.isAccessible = true
            targetField.isAccessible = true
            targetField.set(target, sourceField.get(source))
        }
    }
    return target
}
