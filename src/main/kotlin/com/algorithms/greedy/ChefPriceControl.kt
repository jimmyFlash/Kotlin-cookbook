package com.algorithms.greedy

/**
 * Chef has N items in his shop (numbered 1 through N);
 * for each valid i, the price of the i-th item is Pi
 * Since Chef has very loyal customers, all N items are guaranteed to be sold regardless of their price.
 * However, the government introduced a price ceiling K, which means that for each item i such that Pi > K
 * its price should be reduced from Pi to K.
 *
 * Chef's revenue is the sum of prices of all the items he sells.
 * Find the amount of revenue which Chef loses because of this price ceiling.
 *
 * Input
 * The first line of the input contains a single integer T denoting the number of test cases.
 * The description of T test cases follows.
 * The first line of each test case contains two space-separated integers N and K.
 * The second line contains N space-separated integers P1, P2, .... Pn
 *
 * Output
 * For each test case, print a single line containing one integer ― the amount of lost revenue.
 *
 * Constraints
 * 1  <= T <= 100
 * 1 <= N <= 10,000
 * 1 <= Pi <= 1000 for each valid i
 * 1 <= K <= 1,000
 *
 * Subtasks
 * Subtask #1 (100 points): original constraints
 */

/*
    Sample 1:

    3
    5 4
    10 2 3 4 5
    7 15
    1 2 3 4 5 6 7
    5 5
    10 9 8 7 6

    output1:
    7
    0
    15

    Explanation:
    Test Case 1: The initial revenue is 10 + 2 + 3 + 4 + 5 = 2410+2+3+4+5=24. Because of the price ceiling, P1
    decreases from 10 to 4 and P5 decreases from 5 to 4.
    The revenue afterwards is 4 + 2 + 3 + 4 + 4 = 17 and the lost revenue is 24 - 17 = 724−17=7.

    Test Case 2: The initial revenue is 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28 For each valid i, Pi <= 15
    so there are no changes, the revenue after introduction of the price ceiling is the
    same and there is zero lost revenue.

    Test Case 3: The initial revenue is 10 + 9 + 8 + 7 + 6 = 40. Since Pi > 5 for each valid i,
    the prices of all items decrease to 5. The revenue afterwards is 5 * 5 = 25 and the
    lost revenue is 40 - 25 = 15.
 */

fun main() {
    val prices = intArrayOf(10, 2, 3, 4, 5)
    val priceCeiling = 4
    println("Revenue loss = ${solve(prices, priceCeiling)}")
}

fun solve(prices: IntArray, priceCeiling: Int): Int{

    /*
        The fold function allows to aggregate the values in an array;
        fold iterates through the array, and applies the provided function
        to the aggregated value <agg>, and the next element <e>.
        The first argument to fold is what the aggregated value is initialized with, in this case, 0.

        Start with an aggregated value of 0, then keep adding e-minOf(priceCeiling,e)
        to the aggregated value. If the price ceiling did not cause the price of the
        good to fall, that expression will be 0, otherwise, it will be equal to
        the incurred loss for that good
     */
    return prices.fold(0) { agg, price ->
        agg + (price- minOf(priceCeiling, price))
    }
}