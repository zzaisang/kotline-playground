package com.example.kotlinplayground.utils

import com.example.kotlinplayground.common.annotation.ReflectionTarget
import com.example.kotlinplayground.domain.user.UserDetails
import com.example.kotlinplayground.domain.user.UserInfo
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
annotation class CustomAnnotation(val clazz: KClass<*>)

@CustomAnnotation(AnnotationTest::class)
data class TestClass(val value: String)

data class PropertyTestClass(
    val value: String,
    val testClass: TestClass,
)

data class AnnotationTest(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class FieldAnnotation(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class OtherAnnotation(val value: Int)

@FieldAnnotation("Hello")
data class OtherDataClass(val name: String)

data class MyClass(val field: OtherDataClass)

class ReflectionTest : StringSpec({

    "Reflection" {
        val userInfo = UserInfo("zzaisang", UserDetails(15, "email"))
        val kClass = userInfo::class

        val userDetails = kClass.memberProperties.findLast { "userDetails" == it.name }
        val reflectionTarget = userDetails?.annotations?.find {
            it is ReflectionTarget
        } as? ReflectionTarget

        println(userDetails)
        userDetails shouldNotBe null
    }

    "Test CustomAnnotation" {
        val testClass = TestClass("Hello")
        val customAnnotation = testClass::class.annotations.find {
            it is CustomAnnotation
        } as? CustomAnnotation

        val expectAnnotationClass = AnnotationTest::class
        customAnnotation?.clazz shouldBe expectAnnotationClass
    }

    "Test property's Annotation" {
        val propertyTestClass = PropertyTestClass("Test", TestClass("Hello"))
        val customAnnotation = propertyTestClass::class.members
            .filter { it.name == "testClass" }
            .flatMap { it.annotations }
            .filterIsInstance<CustomAnnotation>()

        println(customAnnotation.size)
    }


    "Check field annotations in MyClass" {
        val myClass = MyClass(OtherDataClass("John"))

        val fieldAnnotations = mutableListOf<Annotation>()
        myClass::class.members.forEach { member ->
            if (member is kotlin.reflect.KProperty<*>) {
                val field = member.call(myClass)
                //                val annotations = field::class.findAnnotation<FieldAnnotation>()

                //                annotations?.let { fieldAnnotations.add(it) }
            }
        }

        val expectedAnnotation = FieldAnnotation("Hello")
        fieldAnnotations.size shouldBe 1
        fieldAnnotations[0] shouldBe expectedAnnotation
    }

})
