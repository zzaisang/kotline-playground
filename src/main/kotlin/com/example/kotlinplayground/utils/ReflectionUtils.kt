package com.example.kotlinplayground.utils

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

class ReflectionUtils {

    inline fun <reified T : Any> applyFieldsFromMap(map: Map<String, Any?>, clazz: Class<T>): T {
        val instance = clazz.getDeclaredConstructor().newInstance()
        clazz.declaredFields.forEach { field ->
            if (map.containsKey(field.name)) {
                val value = map[field.name]
                if (value != null && field.type.isInstance(value)) {
                    field.isAccessible = true
                    field.set(instance, value)
                }
            }
        }
        return instance
    }

    inline fun <reified T : Any, reified R : Any> convertObject(source: T): R {
        val target = R::class.createInstance()
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


}
