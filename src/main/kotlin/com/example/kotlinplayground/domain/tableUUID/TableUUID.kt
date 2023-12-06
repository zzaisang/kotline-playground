package com.example.kotlinplayground.domain.tableUUID

import com.example.kotlinplayground.common.utils.creteSequentialUUID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "table_uuid")
class TableUUID(

    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = creteSequentialUUID(),

    val name: String,
    val email: String,
    val password: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other !is TableUUID) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
