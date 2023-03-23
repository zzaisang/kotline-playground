package com.example.kotlinplayground

import org.junit.jupiter.api.Test

class Hello {

    @Test
    fun hello() {

        val traceKey = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        println(traceKey.toList().shuffled().take(4).toString().replace(",",""))

    }
}