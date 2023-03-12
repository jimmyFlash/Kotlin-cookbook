package com.algorithms.greedy

import java.lang.Integer.max

/**
 * n number of kids standing in line,
 * they are each ID-ed by an integer value
 * candy is being distributed on the children based on :-
 *      1 - each kid get at least  one candy
 *      2- kids with higher ID than other get more candy
 *
 * find the minimum number of candies required
 *
 * 1 <= n <= 10e5
 *
 */


fun main() {

    val students = listOf(1,2,7,4,3,3,1) // list of students with their ranks in random

    val studentTotal = students.size
    val minCandyGiven = 1

    // create an ordered list of students with each paired to an index of position
    val pairedStudentsList:List<Pair<Int, Int>>  = students
        .sorted()
        .mapIndexed{ i, student -> student to i}.toMutableList()

    println("students indexed mutable list : $pairedStudentsList")

    // create an array of ints with all default value of 1 machine the size of student array
    val candies = IntArray(studentTotal){ 1 }

    // loop through the list of pairs of student, index
    for ((_, index)  in pairedStudentsList){

        // if the index is > 0 and the student at index has rank > the previous one
        if(index > 0 && students[index] > students[index - 1]){
            // find the max of either the current student candies or the previous student candies + default min candy give
            // assign that value to the matching index in the candies array
            candies[index] = max( candies[index], candies[index-1] + minCandyGiven)
        }

        // if the index is less than number of students -1 and current student rank is higher than the next one
        // find the max rank of current student candy compared to the next student + default min candy given
        if(index < (studentTotal-1)  && students[index] > students[index + 1]){
            candies[index] = max(candies[index], candies[index+1] + minCandyGiven)
        }

    }

    println(candies.contentToString())
}