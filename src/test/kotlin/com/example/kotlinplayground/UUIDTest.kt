package com.example.kotlinplayground

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

class UUIDTest : DescribeSpec({

    describe("UUID 테스트") {
        context("java.util.UUID 를 사용해서 UUID를 생성하면") {
            it("버전 4가 된다.") {
                UUID.randomUUID().version() shouldBe 4
            }
        }
    }

})
