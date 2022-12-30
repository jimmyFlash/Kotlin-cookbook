package com.algorithms.greedy

/**
 * given a list of intervals :[start, end]
 * find the length of maximal set of mutually disjoint intervals
 *
 * constraints
 * 1<= N <= 1e5
 * 1 <= A[i][0] <= A[i][1] <= 1e9
 *
 */
fun main() {

    var count = 1
    // array of intervals unsorted
    val intervalsList1 :MutableList<List<Int>> = mutableListOf(
        listOf(1, 2), listOf(2, 10), listOf(4, 6)
    )

    // sort the array by value of second item in child arrays
    intervalsList1.sortBy { it[1] }

    println("Sorted intervals list: $intervalsList1")

    var (prevStart, prevEnd) = intervalsList1[0] // destructure the 1st element array

    // iterate though all child array elements in the sorted 2d array
    for (intervalList in intervalsList1){
        // does the current interval start before end of previous one (overlapping)
        if(intervalList[0] <= prevEnd){
            continue // skip
        }else{
            // update the previous start and end variables
            prevStart = intervalList[0]
            prevEnd = intervalList[1]
            count++  // update interval counter
        }
    }
    // print total number of intervals
    println(count)
}