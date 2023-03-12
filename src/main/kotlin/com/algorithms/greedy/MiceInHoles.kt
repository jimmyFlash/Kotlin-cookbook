package com.algorithms.greedy

/**
 * There are a number of mice N-m and a number of holes
 * N-h. It takes one mouse a whole 1 minute to travel to a
 * hole.
 *
 * given an array of mice m_arr and a matching array of holes h_arr,
 * find the minimum time after which all mice are in their holes
 *
 * constraints :
 * 1 <= N <= 1e5
 * -1e9 <= m_arr[i] <= h_arr[i] <= 1e9
 *
 * example :
 *
 * input
 *  val m_arr = intArrayOf(3, 2, -4)
 *  val h_arr = intArrayOf(0, -2, 4)
 *
 * output
 *   2
 *
 *   analogy
 *   1- we need to sort both arrays mice and holes
 *   2- combine (zip) both sorted arrays to create array pair <mouse, hole>
 *   3- loop through array of pairs calculate the diff between each pair of mouse and its hole
 *   4-  yield the shortest time for mouse to get to its hole
 *
 */
fun main() {

    val miceArr = intArrayOf(3, 2, -4)
    val holesArr = intArrayOf(0, -2, 4)
    // 1- sorting
    miceArr.sort()
    holesArr.sort()

    var ans = 0

    // 2- combine (zip) sorted
    val zipped = miceArr.zip(holesArr)
    println("Zipped sorted arrays: $zipped")

    // 3- loop calculate
    for ((mouse, hole) in zipped){
        // 4 find shortest time mouse need to get to its hole
        ans = ans.coerceAtLeast(kotlin.math.abs(mouse - hole))
    }

    println("Answer is: $ans")
}