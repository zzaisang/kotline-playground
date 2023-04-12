package com.example.kotlinplayground.utils

import com.example.kotlinplayground.NoArg
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ReflectionUtilsKtTest : DescribeSpec({

    describe("Reflection 할 대상의 DTO 에") {
        context("@NoArg 가 존재하는 경우") {
            val beforeDto = BeforeDto("data1")
            val afterDto = convertObject<BeforeDto, AfterDto>(beforeDto)
            it("성공적으로 변경이 된다.") {
                afterDto.data1 shouldBe beforeDto.data1
            }
        }
        context("@NoArg 가 존재하지 않는 경우") {
            val beforeDto = AfterDto("data1")
            it("NoSuchMethod Exception 이 발생한다.") {
                shouldThrow<NoSuchMethodException> { convertObject<AfterDto, BeforeDto>(beforeDto) }
            }
        }
    }
})

data class BeforeDto(
    val data1: String,
)

@NoArg
data class AfterDto(
    val data1: String,
)
