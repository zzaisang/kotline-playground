package com.example.kotlinplayground.utils

import com.example.kotlinplayground.domain.tableUUID.TableUUID

fun createTableUUID(
    name: String = NAME,
    email: String = EMAIL,
    password: String = PASSWORD,
): TableUUID {
    return TableUUID(
        name = name,
        email = email,
        password = password,
    )
}
