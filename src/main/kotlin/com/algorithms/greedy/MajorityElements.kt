package com.algorithms.greedy

import com.utils.toBinaryString
import kotlin.math.floor
import kotlin.math.round

/*
Given an Array of integers of length N
Majority element occurs within > [N/2] frequency

Find the majority element

Constraints

1 <= N <= 1e6
 */

fun main() {

    val testArr = listOf(3, 2, 2, 4, 2, 2)
    // solution is 2 -> 6/2 = 3 > 2

    // the majority element occurrence rounded value
    val occurrence = round(testArr.size.toDouble()/2)
    val naiveSol = naiveSolution(testArr, occurrence)
    println("Solution1, high complexity :${naiveSol.key}")

    val bitWiseSolution = sneakySolution(testArr)
    println("Solutions2 optimised :$bitWiseSolution")
}

/**
 * @param elementsLst list of elements with occurrences
 * @param tgtFrequency target frequency value to filter against
 * @return a map of the highest key/value pair
 *
 *  time complexity is O(n)
 *  space complexity is O(n)
 */
fun naiveSolution(
        elementsLst : List<Int>,
        tgtFrequency : Double
    ): Map.Entry<Int, Int> {

    // create map grouping of each integer mapped to it's max occurrence count
    val groupByOccurrenceMap = elementsLst.groupingBy { it }.eachCount()

    return groupByOccurrenceMap.filter {entry ->
        entry.value > tgtFrequency
    }.maxBy { it.value }
}

/**
 * solution relies on using binary presentation of each number in the list
 * take the binary representation of ech number in memory
 * convert all numbers to binary then bitwise and the value of each bit
 * from each number as you iterate over the list
 * @param elementsLst list of elements with repetitions
 * @return max occurrence frequency number
 *
 * Space complexity is O(1)
 * time complexity is O(log(w)N)
 *
 */
fun sneakySolution( elementsLst : List<Int>): Int {

    val siz = elementsLst.size
    var ans = 0

    for (bit in 0..32) { // Ints ar represented in 23 bits
        var ones = 0 // ones counter

        for (num in elementsLst) { // iterating the list of numbers O(n)
            // since well be using binary representation
            // then we'll consider each bit a column
            // 1st column is 2^0, 2nd is 2^1 and so forth, hence,
            // we'll iterate using 2^n, achieved  using shift-left  starting with bit 1 (2^0)
            val bitColumnItr = 1 shl bit

            // using bitwise and check current bit position containing 1
            val checkOnes = bitColumnItr and num  // bitwise and with number in the list
            println("$bitColumnItr:${bitColumnItr.toBinaryString()} \nand\n$num:${num.toBinaryString()} " +
                    "\n= ${checkOnes.toBinaryString()}\n")
            // each time we have one we increment. the ones counter
            if( checkOnes != 0){
                ones += 1
            }
        }
        // if number ones is > half the list size
        if(ones > floor(siz.toDouble()/2).toInt()){
            // increment the answer by value = 2^bit shift-left starting with 1 (2^0)
            ans += (1 shl bit)
        }
    }
    return ans
}

