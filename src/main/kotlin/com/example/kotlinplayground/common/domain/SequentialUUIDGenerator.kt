package com.example.kotlinplayground.common.domain

import com.example.kotlinplayground.common.utils.creteSequentialUUID
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

class SequentialUUIDGenerator : IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor, entity: Any): Serializable {
        return creteSequentialUUID()
    }
}
