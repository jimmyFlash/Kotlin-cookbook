package com.algorithms.greedy

import kotlin.math.max

/**
 * given a list of interview intervals
 * each defined by start and end time
 * find the least number of meeting rooms required
 *
 * constraints
 * 1 <= n <= 10e5
 * 1 <= A[i][0] <= A[i][1] < 10e9
 */
fun main() {

    // the list of intervals for meetings
    val meetings = listOf(listOf(5,10), listOf(15, 20), listOf(0,30))
    // manages the state of meeting rooms based on start, end of each interval
    val meetingRoomManager = mutableListOf<Pair<Int, Int>>()
    // max number of rooms reached
    var maxNumberOfRooms = 0
    // counter of the overall all state of used meeting rooms
    var currRoomCnt= 0

    // iterate over the list of meeting intervals
    for (meeting in meetings){
        val (start, end) = meeting // deconstruct each meeting interval into start and end variables
        // create pairs of start, 1 for request new room and pairs of end, -1 for left rooms
        // add both pairs to the room manager list
        meetingRoomManager.add(start to 1)
        meetingRoomManager.add(end to -1)
    }

    // sort the list of pairs by the value of the 1st pair value
    meetingRoomManager.sortBy {
        it.first
    }

    println("sorted meetings intervals $meetingRoomManager")

    // loop through meetings manager list
    meetingRoomManager.forEach {
        currRoomCnt += it.second  // update counter with second pair value
        // update the max number of rooms with the max value of either it's previous value saved or
        // value of current counter
        maxNumberOfRooms = max(maxNumberOfRooms, currRoomCnt)
    }

    println("Max number of meeting rooms to cover all meetings is : $maxNumberOfRooms")
}

