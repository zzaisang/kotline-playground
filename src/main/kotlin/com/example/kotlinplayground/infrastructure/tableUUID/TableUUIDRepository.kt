package com.example.kotlinplayground.infrastructure.tableUUID

import com.example.kotlinplayground.domain.tableUUID.TableUUID
import org.springframework.data.jpa.repository.JpaRepository

interface TableUUIDRepository : JpaRepository<TableUUID, String>, TableUUIDRepositoryCustom

