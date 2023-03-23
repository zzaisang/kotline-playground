package com.example.kotlinplayground.domain.developer

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Developer (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    private val name: String,

    private val age: Int
)

