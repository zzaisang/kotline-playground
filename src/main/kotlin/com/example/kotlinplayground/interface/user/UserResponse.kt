package com.example.kotlinplayground.`interface`.user

import com.example.kotlinplayground.common.annotation.NoArg

@NoArg
data class UserResponse(
    val name: String,
)

@NoArg
data class UserDetailsResponse(
    val age: Int,
    val email: String,
)
