package com.example.kotlinplayground.domain.tableUUIDAnnotation

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

@Entity
@Table(name = "table_uuid_annotation")
class TableUUIDAnnotation(

    @Id
    @GeneratedValue(generator = "sequential-uuid")
    @GenericGenerator(name = "sequential-uuid",
        strategy = "com.example.kotlinplayground.common.domain.SequentialUUIDGenerator"
    )
    @Column(columnDefinition = "varchar(36)")
    val id: String = "",

    val name: String,
    val email: String,
    val password: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    ) {

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other !is TableUUIDAnnotation) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
