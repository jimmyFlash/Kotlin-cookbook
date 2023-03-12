package com.algorithms.greedy

/**
 * given an array A of a random number of permutations
 * ( a permutation of a set is, loosely speaking, an arrangement
 * of its members into a sequence or linear order)
 * numbers range from 1 to n
 * given b , the number of swaps we can make in A
 *
 * find the largest permutation possible
 * 1 <= n < 10e6
 * 1 <= b < 10e9
 *
 * to solve this problem we know
 * that the largest permutation = the length of the array
 * we need to associate each element in the array with and index
 * so when we swap we can update the index with the swapped value
 * a map data structure is our best choice
 *
 */
fun main() {

    val case1 = mutableListOf(1,3,2) to 1  // sol : [3,1,2]
    val case2 = mutableListOf(3,2,4,1,5) to 3 // sol [5,4,3,1,2]

    val permutationArr = case2.first
    var numberOfSwaps = case2.second  //  number of swap tries

    var position = 0  // starting index
    // tracks the number of positions left after each permutation
    var maxLen = permutationArr.size

    // convert the array to map of <Int value, index>
    val dictionaryMap: MutableMap<Int, Int> = permutationArr.mapIndexed{ // map transformation with index count
            index_, i -> i to index_ }
        .toMap() // convert to a map<Int, Int>
        .toMutableMap() // make it mutable

    println("original array: $permutationArr, mapped : $dictionaryMap")

    // as long the numberOfSwaps > 0 and postion is less than the array size
    while( numberOfSwaps > 0 && position < permutationArr.size){

        val j: Int? = dictionaryMap[maxLen] // position of max value element in the dictionary

        println("from dictionary current item at $maxLen (j), is $j")
        if(position == j){ // if the current index equals the max item index from map
            continue
        }else{
            numberOfSwaps--
            val currentElementInArray = permutationArr[position]
            val elementFromDicPosition = permutationArr[j!!]
            println("step 1")
            println("get element at position $position = $currentElementInArray")
            println("get element at j $j = $elementFromDicPosition\n")
            // switch values in the array of current item and dictionary item position
            permutationArr[position] = elementFromDicPosition
            permutationArr[j] = currentElementInArray
            println("step 2")
            println("update array element at position $position  = $elementFromDicPosition")
            println("update array element at $j = $currentElementInArray\n")
            // update positions in dictionary also
            val dicElemtRelCurrIndx =  dictionaryMap[permutationArr[position]]
            val dicElemtRelCurrMaxLen =  dictionaryMap[permutationArr[j]]
            println("step 3")
            println("get dictionary element at corr. to element arr ${permutationArr[position]} = $dicElemtRelCurrIndx")
            println("get dictionary element at corr. to element arr ${permutationArr[j]} = $dicElemtRelCurrMaxLen\n")
            dictionaryMap[permutationArr[position]] = dicElemtRelCurrMaxLen!!
            dictionaryMap[permutationArr[j]] = dicElemtRelCurrIndx!!
            println("step 4")
            println("update dictionary element at ${permutationArr[position]}  = $dicElemtRelCurrMaxLen")
            println("update dictionary element at ${permutationArr[j]} = $dicElemtRelCurrIndx\n")
            println("current swapped array: $permutationArr, updated dictionary: $dictionaryMap")
        }
        position++
        maxLen--
    }
    println("swapped array: $permutationArr, updated dictionary: $dictionaryMap")

}