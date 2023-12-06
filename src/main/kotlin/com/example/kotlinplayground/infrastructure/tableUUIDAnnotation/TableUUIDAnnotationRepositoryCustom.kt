package com.example.kotlinplayground.infrastructure.tableUUIDAnnotation

import com.example.kotlinplayground.domain.tableUUIDAnnotation.TableUUIDAnnotation

interface TableUUIDAnnotationRepositoryCustom {

    fun bulkSave(tableUUIDAnnotations: List<TableUUIDAnnotation>)

    fun truncate()
}
