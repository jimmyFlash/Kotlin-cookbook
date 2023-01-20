package com.algorithms.greedy

/**
 * There are a number of mice Nm and a number of holes
 * Nh. It takes one mouse a whole 1 minute to travel to a
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
 */
fun main() {

    val miceArr = intArrayOf(3, 2, -4)
    val holesArr = intArrayOf(0, -2, 4)

    miceArr.sort()
    holesArr.sort()

    var ans = 0

    val zipped = miceArr.zip(holesArr)
    println("Zipped sorted arrays: $zipped")

    for ((mouse, hole) in zipped){
        ans = ans.coerceAtLeast(kotlin.math.abs(mouse - hole))
    }

    println("Answer is: $ans")
}