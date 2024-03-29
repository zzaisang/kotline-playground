package com.example.kotlinplayground

import com.fasterxml.uuid.Generators
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import java.util.*
import kotlin.system.measureTimeMillis

class UUIDTest : DescribeSpec({

    describe("UUID 테스트") {
        context("java.util.UUID 를 사용해서 UUID를 생성하면") {
            it("버전 4가 된다.") {
                UUID.randomUUID().version() shouldBe 4
            }
        }
        context("uuidGenerator 를 사용해서 ") {
            context("timeBasedGenerator 를 사용하면") {
                it("버전 1이 된다.") {
                    Generators.timeBasedGenerator().generate().version() shouldBe 1
                }
                it("UUID 의 사이즈가 36 이다.") {
                    Generators.timeBasedGenerator().generate().toString().length shouldBe 36
                }
                it("시간 기반으로 생성되기 때문에 시간이 지날수록 값이 커진다.") {
                    val uuid1 = Generators.timeBasedGenerator().generate()
                    val uuid2 = Generators.timeBasedGenerator().generate()
                    uuid1.timestamp() shouldBeLessThan uuid2.timestamp()
                }
                it("-으로 구분하면 5개의 그룹으로 나뉜다.") {
                    Generators.timeBasedGenerator().generate().toString().split("-").size shouldBe 5
                }
                context("다수를 생성 했을때.") {
                    val uuidList = (1..10).map {
                        Generators.timeBasedGenerator().generate().apply {
                            println("$this")
                        }
                    }.toList()

                    it("index 0 의 일정한 패턴이 존재하지 않는다.") {
                        val newUUIDIndex0 = Generators.timeBasedGenerator().generate().toString().split("-")[0]
                        uuidList.map { it.toString().split("-")[0] }.toList() shouldNotContain newUUIDIndex0
                    }
                    it("index 1 은 일정한 패턴을 가진다.") {
                        val newUUIDIndex1 = Generators.timeBasedGenerator().generate().toString().split("-")[1]
                        uuidList.map { it.toString().split("-")[1] }.toList() shouldContain newUUIDIndex1
                    }
                    it("index 2 은 일정한 패턴을 가진다") {
                        val newUUIDIndex2 = Generators.timeBasedGenerator().generate().toString().split("-")[2]
                        uuidList.map { it.toString().split("-")[2] }.toList() shouldContain newUUIDIndex2
                    }
                    it("index 3 은 일정한 패턴을 가진다") {
                        val newUUIDIndex3 = Generators.timeBasedGenerator().generate().toString().split("-")[3]
                        uuidList.map { it.toString().split("-")[3] }.toList() shouldContain newUUIDIndex3
                    }
                    it("index 4 은 일정한 패턴을 가지지 않는다.") {
                        val newUUIDIndex4 = Generators.timeBasedGenerator().generate().toString().split("-")[4]
                        uuidList.map { it.toString().split("-")[4] }.toList() shouldNotContain newUUIDIndex4
                    }
                }
                context("sequential 한 UUID 를 ") {
                    it("문자를 조합해서 만들 수 있다.") {
                        measureTimeMillis {
                            (1..100000).map {
                                val uuidParts = Generators.timeBasedGenerator().generate().toString().split("-")
                                uuidParts[2] + uuidParts[1] + uuidParts[0] + uuidParts[3] + uuidParts[4]
                            }.toList().size shouldBe 100000
                        }.apply {
                            println("sequential UUID 10만건 생성 elapsed time : $this ms")
                        }
                    }
                    it("String Buffer 를 사용해서 만들 수 있다.") {
                        measureTimeMillis {
                            (1..100000).map {
                                val uuidParts = Generators.timeBasedGenerator().generate().toString().split("-")
                                val stringBuffer = StringBuffer()
                                stringBuffer.append(uuidParts[2])
                                stringBuffer.append(uuidParts[1])
                                stringBuffer.append(uuidParts[0])
                                stringBuffer.append(uuidParts[3])
                                stringBuffer.append(uuidParts[4])
                                stringBuffer.toString()
                            }.toList().size shouldBe 100000
                        }.apply {
                            println("String Buffer sequential UUID 10만건 생성 elapsed time : $this ms")
                        }
                    }
                    it("String Builder 를 사용해서 만들 수 있다.") {
                        measureTimeMillis {
                            (1..100000).map {
                                val uuidParts = Generators.timeBasedGenerator().generate().toString().split("-")
                                val stringBuilder = StringBuilder()
                                stringBuilder.append(uuidParts[2])
                                stringBuilder.append(uuidParts[1])
                                stringBuilder.append(uuidParts[0])
                                stringBuilder.append(uuidParts[3])
                                stringBuilder.append(uuidParts[4])
                                stringBuilder.toString()
                            }.toList().size shouldBe 100000
                        }.apply {
                            println("String Builder sequential UUID 10만건 생성 elapsed time : $this ms")
                        }
                    }
                }
            }
        }
    }

})
