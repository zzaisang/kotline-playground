package com.example.kotlinplayground.infrastructure.user

import com.example.kotlinplayground.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom
