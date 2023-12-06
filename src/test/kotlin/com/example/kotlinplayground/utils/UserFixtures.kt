package com.example.kotlinplayground.utils

import com.example.kotlinplayground.domain.user.User

const val NAME = "name"
const val PASSWORD = "password"
const val EMAIL = "email"

fun createUser(
    name: String = NAME,
    password: String = PASSWORD,
    email: String = EMAIL,
) = User(
    name = name,
    email = email,
    password = password,
)
