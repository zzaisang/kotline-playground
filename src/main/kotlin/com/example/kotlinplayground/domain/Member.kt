package com.example.kotlinplayground.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Member (
    var name: String
){

    @Id
    @GeneratedValue
    val id: Long? = null


}

