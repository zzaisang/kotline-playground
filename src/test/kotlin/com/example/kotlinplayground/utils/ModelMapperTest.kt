package com.example.kotlinplayground.utils

import io.kotest.core.spec.style.DescribeSpec
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import kotlin.system.measureTimeMillis

class ModelMapperTest : DescribeSpec({

    beforeEach {
        fun modelMapper() = ModelMapper().apply {
            configuration.isFieldMatchingEnabled = true
            configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
        }
    }

    describe("ModelMapper Library 로 Dto Convert를 이용해서") {
        context("변환을 진행하면") {
            val beforeDto = BeforeDto_ModelMapper("data1")
            measureTimeMillis {
                it("정상적으로 변환된다.") {
                    ModelMapper().map(beforeDto, AfterDto_ModelMapper::class.java)
                }.let { println("ModelMapper 소요시간 : $it ms") }
            }
        }
    }
})

data class BeforeDto_ModelMapper(
    val data1: String,
)

data class AfterDto_ModelMapper(
    val data1: String,
)
