package com.standardlib

fun main() {

    // A List is a general-purpose, generic container
    // for storing an ordered collection of elements
    val places = listOf("Paris", "London", "Bucharest")
    val nums = listOf(50, 45, 3, 25, 64, 8)

    println("An immutable list $places")

    val mutablePlaces = mutableListOf("Paris", "London",
        "Bucharest")
    mutablePlaces.add("new capital added")

    println("A mutable list: $mutablePlaces")

    val scores = mutableMapOf("Eric" to 9, "Mark" to 12, "Wayne" to
            1)

    println("A mutable map $scores")

    mutableVsImmutable()

    printSorted(nums)
    printSortedNonKotlin(nums)
}

fun noSideEffectList(names: List<String>) {
    println(names)
}
fun sideEffectList(names: MutableList<String>) {
    names.add("Joker")
}
fun mutableVsImmutable() {
    val people = mutableListOf("Brian", "Stanley", "Ringo")
    noSideEffectList(people) // [Brian, Stanley, Ringo]
    sideEffectList(people) // Adds a Joker to the list
    noSideEffectList(people) // [Brian, Stanley, Ringo, Joker]
}

fun printSorted(numbers: List<Int>) {
    val sorted = numbers.sorted()
    for (element in sorted) {
        println(element)
    }
}

fun printSortedNonKotlin(numbers: List<Int>) {
// Check for the case if the list is empty.
// If it is, there’s nothing to print
    if (numbers.isEmpty())
        return
// currentCount keeps track of the number of print statements made. minValue
// stores the last printed value.
    var currentCount = 0
    var minValue = Int.MIN_VALUE
// The algorithm begins by printing all values matching the minValue and updates
// the currentCount according to the number of print statements made
    for (value in numbers) {
        if (value == minValue) {
            println("values matching the minValue $value")
            currentCount += 1
        }
    }
    while (currentCount < numbers.size) {
        // Using the while loop, the algorithm finds the
        // lowest value bigger than minValue
        // and stores it in currentValue
        var currentValue = numbers.max()
        for (value in numbers) {
            if (value in (minValue + 1) until currentValue) {
                currentValue = value
            }
        }
        // The algorithm then prints all values of currentValue
        // inside the array while
        // updating currentCount.
        for (value in numbers) {
            if (value == currentValue) {
                println("prints all values of currentValue $value")
                currentCount += 1
            }
        }
        // minValue is set to currentValue, so the next iteration will try to find the next
        //minimum value
        minValue = currentValue
    }
}