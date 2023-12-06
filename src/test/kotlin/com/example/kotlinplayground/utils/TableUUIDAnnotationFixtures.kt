package com.example.kotlinplayground.utils

import com.example.kotlinplayground.domain.tableUUIDAnnotation.TableUUIDAnnotation

fun createTableUUIDAnnotation(
    name: String = NAME,
    email: String = EMAIL,
    password: String = PASSWORD,
): TableUUIDAnnotation {
    return TableUUIDAnnotation(
        name = name,
        email = email,
        password = password,
    )
}
