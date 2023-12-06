package com.example.kotlinplayground.common.utils

import com.fasterxml.uuid.Generators
import java.nio.ByteBuffer
import java.util.*

const val SEQUENTIAL_UUID_SIZE = 32

fun creatUUIDv1(): UUID {
    return Generators.timeBasedGenerator().generate()
}

fun creteSequentialUUID(): String {
    val uuidParts = creatUUIDv1().toString().split("-")
    return StringBuffer(SEQUENTIAL_UUID_SIZE).apply {
        append(uuidParts[2])
        append(uuidParts[1])
        append(uuidParts[0])
        append(uuidParts[3])
        append(uuidParts[4])
    }.toString()
}

fun createSequentialUUIDByteArray(): ByteArray {
    val creteSequentialUUID = creteSequentialUUID()
    return ByteBuffer.wrap(ByteArray(16)).apply {
        putLong(creteSequentialUUID.substring(0, 16).toLong(16))
        putLong(creteSequentialUUID.substring(16).toLong(16))
    }.array()
}
