package com.example.kotlinplayground.infrastructure.user

import com.example.kotlinplayground.domain.user.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

const val BATCH_SIZE = 1000

@Repository
class UserRepositoryCustomImpl(
    private val jdbcTemplate: JdbcTemplate
) : UserRepositoryCustom {

    @Transactional
    override fun bulkSave(users: List<User>) {
        users.chunked(BATCH_SIZE).forEach { chunkedUserList ->
            batchSave(chunkedUserList)
        }
    }

    private fun batchSave(userList: List<User>) {
        jdbcTemplate.batchUpdate(
            """
                INSERT INTO users (name, email, password,created_at)
                VALUES (?, ?, ?, ?)
            """.trimIndent(),
            userList,
            BATCH_SIZE
        ) { ps, user ->
            ps.setString(1, user.name)
            ps.setString(2, user.email)
            ps.setString(3, user.password)
            ps.setTimestamp(4, Timestamp.valueOf(user.createdAt))
        }
    }

    @Transactional
    override fun truncate() {
        jdbcTemplate.execute("TRUNCATE TABLE users")
    }
}
