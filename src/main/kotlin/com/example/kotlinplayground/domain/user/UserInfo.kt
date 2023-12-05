package com.example.kotlinplayground.domain.user

import com.example.kotlinplayground.common.annotation.ReflectionTarget
import com.example.kotlinplayground.`interface`.user.UserDetailsResponse

data class UserInfo(
    val name: String,

    val userDetails: UserDetails,
)

@ReflectionTarget(clazz = UserDetailsResponse::class)
data class UserDetails(
    val age: Int,
    val email: String,
)
