package com.algorithms.greedy

import java.lang.Integer.max


/**
 * given an array of N integers
 *
 * find the highest product by multiplying 3 elements
 *
 * constraints
 * 3 <= N <= 5e5
 */

fun main() {

    val a1 = listOf(1,2,3,4)
    val a2 = listOf(0,-1,10,7,5)
    var currTestCase = a2

    // need to sort the array 1st
    // pick the highest 3 elements and multiply them together
    // consider an edge case where there are 2 negative values and one positive
    // 2 negatives multiplied returns positive

    currTestCase = currTestCase.sorted()

    println(currTestCase)

    val takeLastThreeFromEnd = currTestCase.takeLast(3)
    val lastThreeMult = takeLastThreeFromEnd.reduce{
            i, j -> i * j
    }
    println("lastThreeSum : $lastThreeMult")

    val takeFirstTwoFromStart = currTestCase.take(2)
    val takeLastFromEnd = currTestCase.takeLast(1)
    val combinedLists = mergeTwoLists(takeFirstTwoFromStart, takeLastFromEnd)
    println("combinedLists : $combinedLists")

    val combinedListsMult = combinedLists.reduce{
            i, j -> i * j
    }
    println("combinedListsMult : $combinedListsMult")

    val maxSolution = max(lastThreeMult, combinedListsMult)

    println("The max value from 3 elements is : $maxSolution")

}

fun <T> mergeTwoLists(a: List<T>, b: List<T>):List<T> = a + b