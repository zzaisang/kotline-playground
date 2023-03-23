package com.example.kotlinplayground.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Person {
    @Id
    var id: Long? = null
}