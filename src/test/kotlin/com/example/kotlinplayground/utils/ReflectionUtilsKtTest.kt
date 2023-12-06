package com.example.kotlinplayground.utils

import com.example.kotlinplayground.common.annotation.NoArg
import com.example.kotlinplayground.common.utils.convertObject
import com.example.kotlinplayground.domain.user.UserDetails
import com.example.kotlinplayground.domain.user.UserInfo
import com.example.kotlinplayground.`interface`.user.UserResponse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.system.measureTimeMillis

class ReflectionUtilsKtTest : DescribeSpec({

    describe("Reflection 할 대상의 DTO 에") {
        context("@NoArg 가 존재하는 경우") {
            val beforeDto = BeforeDto("data1")
            measureTimeMillis {
                val afterDto = convertObject<BeforeDto, AfterDto>(beforeDto)
                it("성공적으로 변경이 된다.") {
                    afterDto.data1 shouldBe beforeDto.data1
                }
            }.let { println("Reflection 소요시간 : $it ms") }
        }

        context("@NoArg 가 존재하지 않는 경우") {
            val beforeDto = AfterDto("data1")
            it("NoSuchMethod Exception 이 발생한다.") {
                shouldThrow<NoSuchMethodException> { convertObject<AfterDto, BeforeDto>(beforeDto) }
            }
        }
    }

    describe("복잡한 Dto에 대해서") {
        context("Reflection을 진행하면 ") {
            val beforeDto =
                BeforeComplexDto(listOf(BeforeComplexDetailDto("zzaisang", 32), BeforeComplexDetailDto("zzaisang_999", 100)))
            it("정상적으로 변환이 된다.") {
                convertObject<BeforeComplexDto, AfterComplexDto>(beforeDto)
            }
        }
    }

    describe("Test package 외 다른 경로에서 지정시") {

        val userDetails = UserDetails(31, "zzai_sang@laon-medi.com")
        val userInfo = UserInfo("짜이상", userDetails)
        context("Reflection 이 ") {
            it("정상적으로 된다.") {
                val userResponse = convertObject<UserInfo, UserResponse>(userInfo)
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

data class BeforeComplexDto(
    val beforeComplexDetailDtoList: List<BeforeComplexDetailDto>,
)

//@ReflectionTarget(clazz = AfterComplexDetailDto::class)
data class BeforeComplexDetailDto(
    val name: String,
    val age: Int,
)

@NoArg
data class AfterComplexDto(
    val afterComplexDetailDto: AfterComplexDetailDto,
)

@NoArg
data class AfterComplexDetailDto(
    val name: String,
    val age: Int,
)
