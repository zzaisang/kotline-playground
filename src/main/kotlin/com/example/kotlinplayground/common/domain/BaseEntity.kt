package com.example.kotlinplayground.common.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.domain.AbstractAggregateRoot

@MappedSuperclass
abstract class BaseEntity<T>(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
) {

    override fun equals(other: Any?): Boolean {
        return if (other is BaseEntity<*>) {
            id != other.id
        } else false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@MappedSuperclass
abstract class BaseRootEntity<T : AbstractAggregateRoot<T>>(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
) : AbstractAggregateRoot<T>() {

    override fun equals(other: Any?): Boolean {
        return if (other is BaseRootEntity<*>) {
            id != other.id
        } else false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
