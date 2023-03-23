package com.example.kotlinplayground

import com.example.kotlinplayground.fixture.createHelloEntity
import com.example.kotlinplayground.utils.ReflectionUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.full.memberProperties

class RefectionTest {

    @Test
    fun reflectionTest() {

//        fun HelloEntity.toHelloResultReflection() = with(::HelloResult) {
//            val propertiesByName = HelloEntity::class.memberProperties.associateBy { it.name }
//            callBy(args = parameters.associateWith { parameter ->
//                when (parameter.name) {
//                    else -> propertiesByName[parameter.name]?.get(this@toHelloResultReflection)
//                }
//            })
//        }

        val helloEntity = createHelloEntity(id = 9999L, name = "김짜이상", age = 21)

//        val toHelloDtoReflection = helloEntity.toHelloResultReflection()
//
//        assertEquals(toHelloDtoReflection.name, helloEntity.name)
//        println(toHelloDtoReflection.toString())
    }

    @Test
    fun reflectionTest_By_Utils() {
        val helloEntity = createHelloEntity(id = 5555L, name = "김상재김상재김상재", age = 500000)

        val helloResult = ReflectionUtils().convertObject<HelloEntity, HelloResult>(helloEntity)

        println(helloResult)

    }
}

class HelloEntity(
    var id: Long,
    var name: String,
    var age: Int
){
//    fun toHelloResultReflection() = with(::HelloResult) {
//        val propertiesByName = HelloEntity::class.memberProperties.associateBy { it.name }
//        callBy(args = parameters.associateWith { parameter ->
//            when (parameter.name) {
//                else -> propertiesByName[parameter.name]?.get(this@HelloEntity)
//            }
//        })
//    }
}

data class HelloResult(
    val name: String?,
    val age: Int?
) {
    constructor() : this("",0)
}
//    constructor(helloEntity: HelloEntity?) : this(
//        name = helloEntity?.name,
//        age = helloEntity?.age
//    )

