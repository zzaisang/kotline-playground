package com.example.kotlinplayground.domain.user

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column
    val name: String,

    val email: String,

    val password: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    )
