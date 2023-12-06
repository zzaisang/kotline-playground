package com.example.kotlinplayground.infrastructure.tableUUID

import com.example.kotlinplayground.domain.tableUUID.TableUUID
import com.example.kotlinplayground.utils.createTableUUID
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.longs.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.system.measureTimeMillis

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@Commit
class TableUUIDRepositoryTest @Autowired constructor(
    private val tableUUIDRepository: TableUUIDRepository
) : DescribeSpec({

    extension(SpringExtension)

    beforeSpec {
        tableUUIDRepository.truncate()
    }

    describe("tableUUIDRepository") {
        context("tableUUID 를 생성해서 저장하면") {
            val tableUUID = tableUUIDRepository.save(createTableUUID())
            it("count 1이 된다.") {
                println("tableUUID.id = ${tableUUID.id}")
                tableUUIDRepository.count() shouldBeGreaterThanOrEqual 1
            }
            it("id 의 길이는 32 이다") {
                println("tableUUID.id = ${tableUUID.id}")
                tableUUID.id.length shouldBe 32
            }
        }
        context("tableUUID 를 bulk insert 로 100만건을 저장하면 ") {
            val createTableUUIDList = (1..1000000).map { createTableUUID() }.toList()
            measureTimeMillis {
                tableUUIDRepository.bulkSave(createTableUUIDList)
            }.apply { println("1Million Row Save elapsedTime = $this ms") }
            it("성공적으로 저장된다.") {
                tableUUIDRepository.count() shouldBeGreaterThanOrEqual 1000000
            }
            it("추후 1건을 저장 시 1초 이내에 저장된다.") {
                val elapsedTime = measureTimeMillis {
                    tableUUIDRepository.save(createTableUUID())
                }
                println("elapsedTime = $elapsedTime ms")
                elapsedTime shouldBeLessThanOrEqual 1000
            }
        }
        context("추가적으로 10만개를 추가하면") {
            measureTimeMillis {
                val createTableUUIDList = (1..100000).map { createTableUUID() }.toList()
                tableUUIDRepository.bulkSave(createTableUUIDList)
            }.apply { println("10만개 저장 시간 elapsedTime = $this ms") }
            it("성공적으로 저장된다.") {
                tableUUIDRepository.count() shouldBeGreaterThanOrEqual 1100000
            }
            it("추후 1건을 저장 시 1초 이내에 저장된다.") {
                measureTimeMillis {
                    tableUUIDRepository.save(createTableUUID())
                }.apply {
                    println("추가 1건 저장 elapsedTime = $this ms")
                    this shouldBeLessThanOrEqual 1000
                }
            }
            it("id 와 시간 최신순으로 조회해보면 동일한 값이 조회된다.") {
                var tableUUIDById: TableUUID
                measureTimeMillis {
                    tableUUIDById = tableUUIDRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).first()
                }.apply {
                    println("id 최신순 조회 elapsedTime = $this ms")
                }

                var tableUUIDByCreatedAt: TableUUID
                measureTimeMillis {
                    tableUUIDByCreatedAt =
                        tableUUIDRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).first()
                }.apply {
                    println("시간 최신순 조회 elapsedTime = $this ms")
                }

                tableUUIDById.id shouldBe tableUUIDByCreatedAt.id
                (tableUUIDById == tableUUIDByCreatedAt) shouldBe true
            }
        }
    }

})
