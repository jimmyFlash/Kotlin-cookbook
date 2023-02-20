package com.standardlib

import com.utils.toBinaryString


fun main() {

//    println("-8 in binary ${(-8).toBinaryString()}, 8 in binary ${(8).toBinaryString()}}")
    bitwiseAndConjection(
        0,
        1
    )
    bitwiseAndConjection(
        1,
        1
    )
    bitwiseAndConjection(
        0,
        0
    )

    bitwiseAndConjection(
        0b10011, // 19
        0b11110, // 30
        0b10010 // 18
    )

    println()
    bitWiseOrDisjunction(
        0,
        1
    )

    bitWiseOrDisjunction(
        1,
        1
    )

    bitWiseOrDisjunction(
        0,
        0
    )

    bitWiseOrDisjunction(
        0b101001, // 41
        0b110011, // 51
        0b111011 // 59
    )

    println()
    bitWiseXorExclusiveDisjunction(
        0,
        1
    )
    bitWiseXorExclusiveDisjunction(
        1,
        1
    )
    bitWiseXorExclusiveDisjunction(
        0,
        0
    )
    bitWiseXorExclusiveDisjunction(
        0b110101,
        0b101010,
        31  //11111
    )

    println()
    val inverted = bitWiseInversion(0b101100)

    bitwiseAndConjection(
        inverted,
        0b111111,
        0b010011
    )

    println()
    assert(binaryShiftLeft(0b110011, 2) == 0b11001100)

    println()
    assert(
        binaryShiftRightTypes(
            -0b1100110011, 22, ShiftRightTypes.SHIFT_RIGHT_UNSIGNED
        ) == 0b1111111111
    )

    assert( // the first 22 bytes are zeroes
        binaryShiftRightTypes(
            0b1100110011, 2, ShiftRightTypes.SHIFT_RIGHT
        ) == 0b11001100
    )
    assert( // the first bit is 1, it means that the number is negative
        binaryShiftRightTypes(
            0b11111111111111111111110011001101,
            2,
            ShiftRightTypes.SHIFT_RIGHT
        ) == 0b111111111111111111111100110011
    )

    /*
        BitWise operators are  really basic, and on most CPUs,
        they’ll take only one cycle to compute.
        The right shift represents integer division by 2^bits,
        while the left shift is multiplication by 2^bits.

        Unlike bitwise operators, actual multiplication and division might take more than one cycle.
        Then, there’s the ability to pack information really tightly,each bit meaning something specific.
        Then we can use a mask and an and operator to check for this specific property
     */
    println()
    assert(12 shr 2 == 3) // 12 / 2^2 == 12 / 4
    assert(3 shl 3 == 24) // 3 * 2^3 == 3 * 8

    val skyIsBlueMask = 0b00000000000001000000000000
    assert(isSkyBlue(0b10011100111101011101010101, skyIsBlueMask))

    val skyIsBlueFlag = 0b00000000000001000000000000
    val sunIsShinningFlag = 0b00000000000000100000000000
    val skyIsBlueAndSunShines = skyIsBlueFlag or sunIsShinningFlag // 0b00000000000001100000000000
    assert(skyIsBlueAndSunShines == 0b00000000000001100000000000)

    println(assert(23.toBinaryString().toInt() == 23) {
        println("Not matching")
    })
}

fun isSkyBlue(worldProperties: Int, mask: Int): Boolean {

    val maskCheck = worldProperties and mask
    println("input $worldProperties mask $mask -> result:  $maskCheck")
    return maskCheck != 0
}

fun bitwiseAndConjection(b1: Int, b2: Int, assertion: Int = -1) {

    val andOp = b1 and b2
    print("Binary representation param1 ${b1.toBinaryString()}, param2 ${b2.toBinaryString()}: ")
    println("& operation $b1 and $b2 = $andOp")
    if (assertion != -1) {
        assert(andOp == assertion)
    }
}


fun bitWiseOrDisjunction(b1: Int, b2: Int, assertion: Int = -1) {

    val orOp = b1 or b2
    print("Binary representation param1 ${b1.toBinaryString()}, param2 ${b2.toBinaryString()}: ")
    println("| operation $b1 or $b2 = $orOp")
    if (assertion != -1) {
        assert(orOp == assertion)
    }

}

fun bitWiseXorExclusiveDisjunction(b1: Int, b2: Int, assertion: Int = -1) {
    // we will get 0 if the corresponding operand bits are both 0 or both 1:
    val xorOp = b1 xor b2
    print("Binary representation param1 ${b1.toBinaryString()}, param2 ${b2.toBinaryString()}: ")
    println("^ operation $b1 xor $b2 = $xorOp")
    if (assertion != -1) {
        assert(xorOp == assertion)
    }
}

/**
 *  all 0 become 1 and vice versa.
 *  However, we have to remember that the Int type has 32 bytes and Long has 64.
 * That means, to get correct results for shorter binary numbers,
 *  we have to mask them with (and)
 */
fun bitWiseInversion(b: Int): Int {

    val invrt = b.inv()
    print("Binary representation param1 ${b.toBinaryString()}: ")
    println("~ operation $b .inv() = $invrt")
    return invrt
}

/**
 *  take a binary number and shift it by the specified number
 *  of positions to the left or to the right:
 */
fun binaryShiftLeft(b: Int, shiftPos: Int): Int {

    val shlVal = b shl shiftPos

    print("Binary representation param1 ${b.toBinaryString()}: ")
    println("<< operation $b shl $shiftPos = $shlVal : (${shlVal.toBinaryString()})")
    return shlVal
}

/**
 *  There is only one left shift, but we have two right shifts: signed and unsigned.
 *  The unsigned right shift copies zeros to the left of the number and drops the rightmost bits.
 *  The result of an unsigned shift is always positive, even if we shift a negative number
 */
fun binaryShiftRightTypes(
    b: Long, pos: Int,
    srOperation: ShiftRightTypes = ShiftRightTypes.SHIFT_RIGHT
): Int {

    val resSR: Int
    return when (srOperation) {
        ShiftRightTypes.SHIFT_RIGHT -> {
            resSR = (b shr pos).toInt()
            print("Binary representation param1 ${b.toBinaryString()}: ")
            println(">> operation $b shr $pos = $resSR : (${resSR.toBinaryString()})")
            resSR
        }

        ShiftRightTypes.SHIFT_RIGHT_UNSIGNED -> {
            resSR = (b ushr pos).toInt()
            print("Binary representation param1 ${b.toBinaryString()}: ")
            println(">>> operation $b ushr $pos = $resSR : (${resSR.toBinaryString()})")
            resSR
        }
    }

}

enum class ShiftRightTypes {
    SHIFT_RIGHT,
    SHIFT_RIGHT_UNSIGNED
}

