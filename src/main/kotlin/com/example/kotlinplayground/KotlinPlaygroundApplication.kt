package com.example.kotlinplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
	runApplication<KotlinPlaygroundApplication>(*args)
}


