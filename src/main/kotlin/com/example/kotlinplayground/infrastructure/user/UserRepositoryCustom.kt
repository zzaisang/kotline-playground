package com.example.kotlinplayground.infrastructure.user

import com.example.kotlinplayground.domain.user.User

interface UserRepositoryCustom {

    fun bulkSave(users: List<User>)

    fun truncate()
}
