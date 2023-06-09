package com.example.kotlinplayground.domain.user

import com.example.kotlinplayground.common.domain.BaseRootEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "_user")
class User(

    @Column
    val name: String,

    val email: String,

    val password: String,

    id: Long = 0L,
) : BaseRootEntity<User>(id) {
}
