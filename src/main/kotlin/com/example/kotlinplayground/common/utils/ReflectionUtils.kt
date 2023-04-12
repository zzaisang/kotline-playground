package com.example.kotlinplayground.common.utils

class ReflectionUtils

inline fun <reified T : Any, reified R : Any> convertObject(source: T): R {
    val target = R::class.java.getConstructor().newInstance()
    val sourceFields = T::class.java.declaredFields.associateBy { it.name }
    R::class.java.declaredFields.forEach { targetField ->
        val sourceField = sourceFields[targetField.name]
        if (sourceField != null) {
            sourceField.isAccessible = true
            targetField.isAccessible = true
            targetField.set(target, sourceField.get(source))
        }
    }
    return target
}
