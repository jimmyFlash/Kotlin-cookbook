package com.algorithms.greedy

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.min

const val MOD_MAX_INT = 10000002 // Int 32 max modulo
/**
 * There's a row n seats, empty seats and filled seats
 * they are represented respectfully by symbols :
 * (.) for empty seats
 * (x) for filled seats
 * given that either of the (x) can move (i) places to sit together
 *
 * Find the minimum number of moves required to
 * move people to sit together
 *
 * 1>= n >= 1e6
 *
 * Solution analogy
 * 1- convert the string to list of chars
 * 2- create a mutable pairs<Char,Int> list of each seat mapped to its current index
 * 3- filter out the '.' character representing empty seats
 * 4- map the indices of filled seats in their own list
 * 5- calculate the difference between list of previous indices and current ones
 * 6- calculate the median, use it to get optimal seat index
 * 7- iterate the list of updated indices
 * 8- calculate the total offset movement by adding the diff. between each seat index and the media value
 * 9- the min value of movements (offset) is the return
 *
 */
fun main() {

    val text1 = "..x..x."
    val text2 = ".x..x..xx."
    val text3 = "xx..xx....xxx"

    val bestSeatsCombination  = findClosestSeats(text3)
    println("min best answer is= $bestSeatsCombination")
}

fun findClosestSeats(allSeatsOrder:String): Int{
    var ans = Int.MAX_VALUE

    // 1- convert the string to char array then to mutable list
    val listIndex = allSeatsOrder.trim().toCharArray().toMutableList()


    // 2- create a list of pairs from each character paired to its position index
    // 3- remove the character pairs that match (.)
    val filtered = listIndex
        .mapIndexed{index, seat -> seat to index}
        .toMutableList()
        .filter { it.first != '.' }
    // println("Occupied seats $filtered")


    // 4- extract the indices to a separate list
    val xSeatIndices = filtered.map {it.second}
    println("Occupied seats indices $xSeatIndices")

    // 5- imagine moving the seats next to each other across the theatre all the way to the beginning
    // of the seats row.
    // 1st seat at index(x) previously will move to index (0),
    // next seat at index (y) will move next to the previous seat at index (0) but it will be next to
    // it at index (1), will end up with 4 seats for example at indices [0,1,2,3]
    // meaning that to move to index 0 for each seat = previous_index - 0_index + new_index_relative_to_1st_seat
    // sequentially, to generalize = previousIndex - index_in_Array_of_occupied_seats_only
    val offsetIndices = xSeatIndices.mapIndexed { currentIndex, previousIndex ->
        previousIndex - currentIndex
    }
    println("list of diff. between values and their indices: $offsetIndices")

    // make sure the list is empty if so , return 0
    if (xSeatIndices.isEmpty())
        return 0


    // 6- using median will help us find the best spot to group all the occupied seats
    // with least moving of chairs which together will give us better time complexity
    // compared to iterating over all seats in the row
    val median = offsetIndices[floor(offsetIndices.size.toDouble() / 2).toInt()]
    println("median = $median")

    var offsetTotal = 0
    // 7
    for (xSeat in offsetIndices) {  // O(N)
        // 8
        // calculate the abs difference between xSeat and the
        // other seats empty/occupied
        offsetTotal += abs(xSeat - median)
        // integer modulo
        offsetTotal %= MOD_MAX_INT
        println("diff. $xSeat - $median  total: $offsetTotal")
    }
    ans = min(ans, offsetTotal)
    return ans
}