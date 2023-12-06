package com.example.kotlinplayground.infrastructure.tableUUID

import com.example.kotlinplayground.domain.tableUUID.TableUUID

interface TableUUIDRepositoryCustom {

    fun bulkSave(tableUUIDs: List<TableUUID>)

    fun truncate()
}
