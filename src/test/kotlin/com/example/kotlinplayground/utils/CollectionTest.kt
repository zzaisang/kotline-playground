package com.example.kotlinplayground.utils

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CollectionTest : DescribeSpec({

    describe("Collection 에서 ") {
        val list = listOf(1, 2, 3)
        context("indices 를 통해 ") {
            it("index 를 가져올 수 있다.") {
                println(list.indices)
                list.indices shouldBe 0..2
            }
            it("index 를 통해 값을 가져올 수 있다.") {
                list[list.indices.last] shouldBe 3
            }
            context("for 문으로 ") {
                it("index 를 가져올 수 있다.") {
                    for (i in list.indices) {
                        println(i)
                    }
                }
            }
        }
        context("for 문으로 until 로") {
            it("범위를 지정 할 수 있다.") {
                for (i in 0 until list.size) {
                    println(i)
                }
            }
        }
    }

    describe("총길이가 55인 배열에서") {
        val arr = (1..55).toList()
        context("10개씩 나누어서") {
            it("반복문을 돌릴 수 있다.") {
                for (i in arr.indices step 1000) {
                    println(i)
                }
            }
        }

        context("10개씩 나누어서") {
            it("10개씩 출력 할 수 있다") {
                for ((index, value) in arr.withIndex()) {
                    println(value)
                    if (index % 10 == 0) {
                        println("index = $index, value = $value")
                    }
                }
            }
        }
    }

    describe("5500 개의 Obj 객체를") {
        val list = (1..5500).map { Obj() }
        val chunkSize = 1000
        context("1000개씩 ") {
            val chunked = list.chunked(chunkSize)
            for ((index, value) in chunked.withIndex()) {
                it("1000개씩 묶어서 출력 할 수 있다.") {
                    println("index = $index, valueIdx = ${value.size}")
                }
            }
        }
    }

})

data class Obj(
    val name: String = "hello",
    val age: Int = 20,
)
