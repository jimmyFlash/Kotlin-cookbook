package com.algorithms.greedy

/**
 * given an array A of a random number of permutations
 * numbers range from 1 to n
 * given b , the number of swaps we can make in A
 *
 * find the largest permutation possible
 * 1 <= n < 10e6
 * 1 <= b < 10e9
 *
 */
fun main() {

    val case1 = mutableListOf(1,3,2) to 1  // sol : [3,1,2]
    val case2 = mutableListOf(3,2,4,1,5) to 3 // sol [5,4,3,1,2]

    val arrayA = case2.first
    var numberOfSwaps = case2.second  //  number of swap tries

    var index = 0
    // tracks the number of positions left after each permutation
    var maxLen = arrayA.size

    // convert the array to map of <Int value, index>
    val dictionaryMap: MutableMap<Int, Int> = arrayA.mapIndexed{ // map transformation with index count
            index_, i -> i to index_ }.toMap() // convert to a map<Int, Int>
        .toMutableMap() // make it mutable

    println("original array: $arrayA, mapped : $dictionaryMap")

    while( numberOfSwaps > 0 && index < arrayA.size){

        val j: Int? = dictionaryMap[maxLen] // position of max value element in the dictionary

        println("from dictionary current item at $maxLen, is $j")
        if(index == j){ // if the current index equals the item index
            continue
        }else{
            numberOfSwaps--
            val currentElementInArray = arrayA[index]
            val elementFromDicPosition = arrayA[j!!]
            // switch values in the array of current item and dictionary item position
            arrayA[index] = elementFromDicPosition
            arrayA[j] = currentElementInArray
            // update positions in dictionary also
            val dicElemtRelCurrIndx =  dictionaryMap[arrayA[index]]
            val dicElemtRelCurrMaxLen =  dictionaryMap[arrayA[j]]
            dictionaryMap[arrayA[index]] = dicElemtRelCurrMaxLen!!
            dictionaryMap[arrayA[j]] = dicElemtRelCurrIndx!!
        }
        index++
        maxLen--
    }
    println("swapped array: $arrayA, updated dictionary: $dictionaryMap")

}