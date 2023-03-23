package com.example.kotlinplayground.domain.developer

import com.example.kotlinplayground.fixture.createDeveloper
import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper

class DeveloperTest {

    @Test
    fun name() {
        val developer = createDeveloper(name = "짜이상")

        val modelMapper = ModelMapper()
        val developerDto = modelMapper.map(developer, DeveloperDto::class.java)

        println(developerDto)

    }
}
