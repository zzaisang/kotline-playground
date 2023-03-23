package com.example.kotlinplayground.domain.dto

import org.junit.jupiter.api.Test
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberProperties

class HelloParamTest {

    @Test
    fun hello() {
//        println(HelloParam::class.java)

//        val classInfo = HelloParam::class

//        classInfo.memberProperties.forEach {
//            println("property ${it.name} of  type ${it.returnType}")
//        }


        fun HelloParam.toHelloDtoReflection() = with(::HelloDto) {
            val propertiesByName = HelloParam::class.memberProperties.associateBy { it.name }
            callBy(args = parameters.associateWith { parameter ->
                when (parameter.name){
                    else -> propertiesByName[parameter.name]?.get(this@toHelloDtoReflection)
                }
            })
        }

        val helloParam = HelloParam(
            hi = "hihi",
            hello = "hello",
            hahaha = 0L,
        )

        val helloDto = helloParam.toHelloDtoReflection()
        println(helloDto.toString())


    }

    @Test
    fun reflectionTest() {
        val kClass = HelloParam::class
        println(kClass.simpleName)
        println(kClass.qualifiedName)
        println(kClass.members)
        println(kClass.constructors)
        println(kClass.nestedClasses)

        fun foo(x: Int) = println(x)

        val KFunction = ::foo
        KFunction.call(42)
    }
}
