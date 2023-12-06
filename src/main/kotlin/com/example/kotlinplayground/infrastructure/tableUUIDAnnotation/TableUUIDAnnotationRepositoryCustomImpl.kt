package com.example.kotlinplayground.infrastructure.tableUUIDAnnotation

import com.example.kotlinplayground.common.utils.creteSequentialUUID
import com.example.kotlinplayground.domain.tableUUIDAnnotation.TableUUIDAnnotation
import com.example.kotlinplayground.infrastructure.user.BATCH_SIZE
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class TableUUIDAnnotationRepositoryCustomImpl(
    private val jdbcTemplate: JdbcTemplate
) : TableUUIDAnnotationRepositoryCustom {

    override fun bulkSave(tableUUIDAnnotations: List<TableUUIDAnnotation>) {
        tableUUIDAnnotations.chunked(BATCH_SIZE).forEach { chunkedUserList ->
            batchSave(chunkedUserList)
        }
    }

    private fun batchSave(userList: List<TableUUIDAnnotation>) {
        jdbcTemplate.batchUpdate(
            """
                INSERT INTO table_uuid_annotation (id, name, email, password,created_at)
                VALUES (?, ?, ?, ?,?)
            """.trimIndent(),
            userList,
            BATCH_SIZE
        ) { ps, user ->
            ps.setString(1, creteSequentialUUID())
            ps.setString(2, user.name)
            ps.setString(3, user.email)
            ps.setString(4, user.password)
            ps.setTimestamp(5, Timestamp.valueOf(user.createdAt))
        }
    }

    override fun truncate() {
        jdbcTemplate.execute("TRUNCATE TABLE table_uuid")
    }
}
