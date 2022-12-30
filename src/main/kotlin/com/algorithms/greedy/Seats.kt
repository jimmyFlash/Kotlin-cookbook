package com.algorithms.greedy

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.min

const val MOD_MAX_INT = 10000002
/**
 * There's a row n seats, empty seats and filled seats
 * they are represented respectfully by symbols :
 * (.) for empty seats and (x) for filled seats
 * given that either of the (x) can move (i) places
 * to sit together
 *
 * Find the minimum number of moves required to
 * move people to sit together
 *
 * 1>= n >= 1e6
 */
fun main() {

    val text1 = "..x..x."
    val text2 = ".x..x..xx."

    val bestSeatsCombination  = findClosestSeats(text2)
    println("min best answer is= $bestSeatsCombination")
}

fun findClosestSeats(allSeatsOrder:String): Int{
    var ans = Int.MAX_VALUE

    // convert the string to char array then to mutable list
    val listIndex = allSeatsOrder.trim().toCharArray().toMutableList()


    // create a list of pairs from each character paired to its position index
    // remove the character pairs that match (.)
    val filtered = listIndex
        .mapIndexed{index, seat -> seat to index}
        .toMutableList()
        .filter { it.first != '.' }
    // println("Occupied seats $filtered")


    // extract the indices to a separate list
    val xSeatIndices = filtered.map {it.second}
    println("Occupied seats indices $xSeatIndices")

    // imagine moving the seats next to each other across the theatre all the way to the beginning
    // of the seats row. 1st seat at index (x) previously will move to index (0),
    // next seat at index (y) will move next to the previous seat at index (0) but it will be next to
    // it at index (1), will end up with 4 seats for example at indices [0,1,2,3]
    // meaning that to move to index 0 for each seat = previous_index - 0_index + new_index_relative_to_1st_seat
    // to generalize = previousIndex - index_in_Array_of_occupied_seats_only
    val offsetIndices = xSeatIndices.mapIndexed { currentIndex, previousIndex ->
        previousIndex - currentIndex
    }
    println("list of diff. between values and their indices: $offsetIndices")

    // make sure the list is empty if so , return 0
    if (xSeatIndices.isEmpty())
        return 0

    // iterate over the string indices
    for (seat in allSeatsOrder.indices){
        val median = floor(offsetIndices.size.toDouble() / 2)
        println("current seat index $seat, type:${allSeatsOrder[seat]}")
        var offsetTotal = 0
        // iterate over the x-indices list
        for (xSeat in offsetIndices){
            // calculate the abs difference between xSeat and the
            // other seats empty/occupied
            offsetTotal += abs(xSeat - seat)
            // integer modulo
            offsetTotal %= MOD_MAX_INT
            println("diff. $xSeat - $seat  total: $offsetTotal")
        }
        ans = min(ans, offsetTotal)

    }
    return ans
}