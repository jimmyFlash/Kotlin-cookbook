package com.algorithms.greedy

fun factorial(n: Int): Long {
    //  the require function throws an IllegalArgumentException
    //  when the predicate is not satisfied
    require(n >= 0){
        "Cannot calculate factorial of $n because it is smaller than 0"
    }
    return if (n <= 1) {
        1
    }else {
        factorial(n - 1) * n
    }
}

fun main() {
    val num = 5
    val factorial = factorial(num)
    println("factorial of $num is $factorial")
}