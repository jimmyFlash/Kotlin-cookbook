package com.utils

fun Int.toBinaryString(): String = let { int ->
    IntProgression
        .fromClosedRange(rangeStart = Int.SIZE_BITS - 8, rangeEnd = 0, step = -8)
        .joinToString("") { bytePos ->
            (int shr bytePos and 0xFF).toString(2).padStart(8, '0')
        }
}

fun Long.toBinaryString(): String = let { long ->
    IntProgression
        .fromClosedRange(rangeStart = Long.SIZE_BITS - 8, rangeEnd = 0, step = -8)
        .joinToString("") { bytePos ->
            (long shr bytePos and 0xFF).toString(2).padStart(8, '0')
        }
}