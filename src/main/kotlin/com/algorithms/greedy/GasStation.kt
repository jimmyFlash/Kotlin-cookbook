package com.algorithms.greedy

/**
 * given an n number of gas stations (A) the lie along a circular route
 *
 * each station has A[n] amounts of gas
 * to travel from i -> i + 1 there's B[n] gas cost to be deducted
 *
 * find the earliest station from where you can travel around the entire circle and back
 * without running out of gas
 *
 * Return -1 if not possible
 *
 * constraints :-
 *  1>= N >= 1E6
 *
 *
 */

fun main() {

    val gasFill = listOf(3, 5, 2, 1, 7)
    val gasCost = listOf(4, 2, 1, 9 ,1)
    val start = 0
    val currStationInx = 0
    val siz = gasFill.size

    /*
    to travel in a circle you can start at any index of gas station, you travel to the next
    all the way to the end and then back to where you started
    this is why we will duplicate the elements in the gas station and cost lists and
    append them to the end of each list
     */
    val dupGas = gasFill.toMutableList() + gasFill
    val dupCost = gasCost.toMutableList() + gasCost
    val combineGasCost = dupGas.zip(dupCost)
    println(" Best start: ${solveGasStationsRound(combineGasCost, start, currStationInx, siz)}")
}

fun solveGasStationsRound(combinedList:List<Pair<Int,Int>>,
                          start:Int, currStationInx:Int, sizeLimit:Int):Int{
    var gasFill = currStationInx
    val siz = combinedList.size
    var startPoint = start
    // iterate through all gas stations
    for ( i in 0..siz ){
        // if the current index is = the start position + all station in circle
        // return the start point because you reached did a full circle and reached point
        // where you started
        if (i == startPoint + sizeLimit)
            return startPoint

        // update the travel to the current gas station cost
        // subtraction the filled
        gasFill += combinedList[i].first - combinedList[i].second

        if(gasFill < 0){
            startPoint = i +1
            gasFill = 0
        }
    }
    return  -1
}