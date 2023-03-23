package com.example.kotlinplayground.fixture

import com.example.kotlinplayground.HelloEntity
import com.example.kotlinplayground.domain.developer.Developer

fun createDeveloper(
    name: String = "짜이상",
    age: Int = 32,
    id: Long = 0L,
): Developer {
    return Developer(id, name, age)
}


fun createHelloEntity(
    name: String = "짜이상",
    age: Int = 32,
    id: Long = 0L
): HelloEntity {
    return HelloEntity(id, name, age)
}
