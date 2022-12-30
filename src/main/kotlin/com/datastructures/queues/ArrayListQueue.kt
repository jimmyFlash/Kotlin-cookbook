package com.datastructures.queues

import com.utils.example


class ArrayListQueue<T> : Queue<T> {

    // Using the features of ArrayList, you get the following
    // Get the size of the queue using the same property of the list
    // Return the element at the front of the queue, if there is any
    private val list = arrayListOf<T>()

    // enqueueing an element is an O(1) operation. This is
    //because the list has empty space at the back
    override fun enqueue(element: T): Boolean {
        list.add(element)
        return true
    }

    override fun dequeue(): T? =
        if (isEmpty)
            null
        else
            list.removeAt(0)

    override val count: Int
        get() = list.size

    override fun peek(): T? =
        if (isEmpty)
            null
        else
            list[0]

    override fun toString(): String = list.toString()
}

fun main() {

    val queue = ArrayListQueue<String>().apply {
        enqueue("Ray")
        enqueue("Brian")
        enqueue("Eric")
    }
    println(queue)
    queue.dequeue()
    println(queue)
    println("Next up: ${queue.peek()}")


    "Boardgame manager with Queue" example {
        val queue = ArrayListQueue<String>().apply {
            enqueue("Vincent")
            enqueue("Remel")
            enqueue("Lukiih")
            enqueue("Allison")
        }
        println(queue)
        println("===== boardgame =======")
        queue.nextPlayer()
        println(queue)
        queue.nextPlayer()
        println(queue)
        queue.nextPlayer()
        println(queue)
        queue.nextPlayer()
        println(queue)
    }

    "Reverse queue" example {
        val queue = ArrayListQueue<String>().apply {
            enqueue("1")
            enqueue("21")
            enqueue("18")
            enqueue("42")
        }
        println("before: $queue")
        queue.reverse()
        println("after: $queue")
    }

}