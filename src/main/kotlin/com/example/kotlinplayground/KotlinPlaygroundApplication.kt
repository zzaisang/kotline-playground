package com.example.kotlinplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<KotlinPlaygroundApplication>(*args)
}


