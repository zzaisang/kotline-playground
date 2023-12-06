package com.example.kotlinplayground.infrastructure.tableUUIDAnnotation

import com.example.kotlinplayground.domain.tableUUIDAnnotation.TableUUIDAnnotation
import org.springframework.data.jpa.repository.JpaRepository

interface TableUUIDAnnotationRepository : JpaRepository<TableUUIDAnnotation, String>,
    TableUUIDAnnotationRepositoryCustom

