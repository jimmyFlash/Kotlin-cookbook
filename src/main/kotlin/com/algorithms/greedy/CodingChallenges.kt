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
    creatingGraphInListForm(arrayOf(
        intArrayOf(1,2),
        intArrayOf(1,3),
        intArrayOf(3,2),
        intArrayOf(3,4),
        intArrayOf(4,3),
        )
    )

    fibonnaci(8)

}

fun creatingGraphInListForm(pairs : Array<IntArray> ){
    // hashmap will use 1st item of each pair as key with list of children representing
    // the nodes it's connected to
    val graph:HashMap<Int, MutableList<Int>> = hashMapOf()

    // iterate through the array of pairs
    pairs.forEach {pair ->

        if (!graph.contains(pair[0])){
            // if current node isn't in the adjacency list
            // add it and create it dependency list starting with pair[1]
            graph[pair[0]] = mutableListOf(pair[1])
        }else{
            //if key is existent then add the pair[1] to existing mutable list
            val depNodeLst = graph[pair[0]]
            depNodeLst!!.add(pair[1])
            graph[pair[0]] = depNodeLst
        }
    }
    println("graph map $graph")
}

fun fibonnaci(num : Int){

    // initialize array to keep track of sub problems
    val results = Array(num + 1){0}
    // base cases
    results[1] = 1
    results[2] = 1

    for ( i in 3 .. num){
        results[i] = results[i-1] + results[i-2]
    }

    print("fib. results ${results.contentToString()}")
}