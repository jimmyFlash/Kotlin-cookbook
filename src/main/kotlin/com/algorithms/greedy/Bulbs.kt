package com.algorithms.greedy

import kotlin.math.abs

/*
    Give N set of bulbs either ON (1) or OFF (0)
    turning on i bulb causes all the bulbs to the right their state

    Find the min number of switches to turn all lights on

    If the bulb is on (1) continue if it's off (0) flip the rest

    Constraints
    1 <= N <= 1e5
    A[i] = 0 || 1
 */


fun main() {

    /*
       Space complexity is O(1) because the storage only takes
       a constant value which is the flips count

      time complexity is linear O(n)

    */
    val testBulbSet1 = mutableListOf(1, 1, 0, 1, 0, 0, 1)
    val testBulbSet2 = mutableListOf(1, 0, 1)
    val currentTestcase = testBulbSet2
    var flips = 0  // counter for how many times the state changed

    println("original array $currentTestcase")
    // loop through the elements in the list
    for (bulb in 0 until currentTestcase.size){
        // keep reference to the value representing current bulb state
        val bulbLast = currentTestcase[bulb]

        // even flips return the bulb state to it original value
        if(flips % 2 != 0)
            currentTestcase[bulb] = abs(bulbLast - 1)

        // if the current bulb state is 0 (off), update it and increment flips
        if (currentTestcase[bulb] == 0) {
            currentTestcase[bulb] = 1
            flips++
        }
    }
    println("Number of flips is : $flips, Current array shape is : $currentTestcase")
}