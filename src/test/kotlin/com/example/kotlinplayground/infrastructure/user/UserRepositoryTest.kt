package com.example.kotlinplayground.infrastructure.user

import com.example.kotlinplayground.utils.createUser
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.longs.shouldBeLessThanOrEqual
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
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
) : DescribeSpec({

    extension(SpringExtension)

    beforeSpec {
        userRepository.truncate()
    }

    describe("userRepository") {
        context("user 를 생성해서 저장하면") {
            it("id 를 반환한다.") {
                val user = userRepository.save(createUser())
                user.id shouldBeGreaterThan 0
                print("user.id = ${user.id}")
            }
        }

        context("user 를 bulk insert 로 100만건을 저장하면 ") {
            val createUserList = (1..1000000).map { createUser() }.toList()
            it("성공적으로 저장된다.") {
                measureTimeMillis {
                    userRepository.bulkSave(createUserList)
                }.apply {
                    print("1Million Row Save elapsedTime = $this ms")
                }
                userRepository.count() shouldBeGreaterThanOrEqual 1000000
            }

            it("추후 1건을 저장 시 1초 이내에 저장된다.") {
                val elapsedTime = measureTimeMillis {
                    userRepository.save(createUser())
                }
                print("elapsedTime = $elapsedTime")
                elapsedTime shouldBeLessThanOrEqual 1000
            }
        }
        context("추가적으로 10만개를 추가하면") {
            measureTimeMillis {
                val createUserList = (1..100000).map { createUser() }.toList()
                userRepository.bulkSave(createUserList)
            }.apply { print("10만개 저장 시간 elapsedTime = $this ms") }
            it("성공적으로 저장된다.") {
                userRepository.count() shouldBeGreaterThanOrEqual 1100000
            }
            it("추후 1건을 저장 시 1초 이내에 저장된다.") {
                val elapsedTime1 = measureTimeMillis {
                    userRepository.save(createUser())
                }
                print("추후 1개 저장 시간 elapsedTime = $elapsedTime1 ms")
                elapsedTime1 shouldBeLessThanOrEqual 1000
            }
        }
    }

})
