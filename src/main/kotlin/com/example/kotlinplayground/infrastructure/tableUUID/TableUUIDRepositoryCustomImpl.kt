package com.example.kotlinplayground.infrastructure.tableUUID

import com.example.kotlinplayground.domain.tableUUID.TableUUID
import com.example.kotlinplayground.infrastructure.user.BATCH_SIZE
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class TableUUIDRepositoryCustomImpl(
    private val jdbcTemplate: JdbcTemplate
) : TableUUIDRepositoryCustom {

    override fun bulkSave(tableUUIDs: List<TableUUID>) {
        tableUUIDs.chunked(BATCH_SIZE).forEach { chunkedUserList ->
            batchSave(chunkedUserList)
        }
    }

    private fun batchSave(userList: List<TableUUID>) {
        jdbcTemplate.batchUpdate(
            """
                INSERT INTO table_uuid (id, name, email, password, created_at)
                VALUES (?, ?, ?, ?,?)
            """.trimIndent(),
            userList,
            BATCH_SIZE
        ) { ps, user ->
            ps.setString(1, user.id)
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
