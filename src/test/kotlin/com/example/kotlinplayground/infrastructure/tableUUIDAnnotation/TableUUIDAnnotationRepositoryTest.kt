package com.example.kotlinplayground.infrastructure.tableUUIDAnnotation

import com.example.kotlinplayground.utils.createTableUUIDAnnotation
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.longs.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.system.measureTimeMillis

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@Commit
class TableUUIDAnnotationRepositoryTest @Autowired constructor(
    private val tableUUIDAnnotationRepository: TableUUIDAnnotationRepository,
) : DescribeSpec({

    extension(SpringExtension)

    beforeSpec {
        tableUUIDAnnotationRepository.truncate()
    }

    describe("tableUUIDAnnotationRepository") {
        context("tableUUIDAnnotationRepository 를 생성해서 저장하면") {
            val tableUUIDAnnotation = tableUUIDAnnotationRepository.save(createTableUUIDAnnotation())
            it("count 1이 된다.") {
                println("tableUUID.id = ${tableUUIDAnnotation.id}")
                tableUUIDAnnotationRepository.count() shouldBeGreaterThanOrEqual 1
            }
            it("id 의 길이는 32 이다") {
                println("tableUUID.id = ${tableUUIDAnnotation.id}")
                tableUUIDAnnotation.id.length shouldBe 32
            }
        }
        context("tableUUID 를 bulk insert 로 100만건을 저장하면 ") {
            val tableUUIDByteArrayList = (1..1000000).map { createTableUUIDAnnotation() }.toList()
            tableUUIDAnnotationRepository.bulkSave(tableUUIDByteArrayList)
            it("성공적으로 저장된다.") {
                tableUUIDAnnotationRepository.count() shouldBeGreaterThanOrEqual 1000000
            }
            it("추후 1건을 저장 시 1초 이내에 저장된다.") {
                val elapsedTime = measureTimeMillis {
                    tableUUIDAnnotationRepository.save(createTableUUIDAnnotation())
                }
                println("elapsedTime = $elapsedTime")
                elapsedTime shouldBeLessThanOrEqual 1000
            }
        }
        context("추가적으로 10만개를 추가하면") {
            val elapsedTime = measureTimeMillis {
                val tableUUIDByteArrayList = (1..100000).map { createTableUUIDAnnotation() }.toList()
                tableUUIDAnnotationRepository.bulkSave(tableUUIDByteArrayList)
            }
            it("60초 이내에 저장된다.") {
                println("elapsedTime = $elapsedTime")
                elapsedTime shouldBeLessThanOrEqual 60000
            }
        }
    }

})
