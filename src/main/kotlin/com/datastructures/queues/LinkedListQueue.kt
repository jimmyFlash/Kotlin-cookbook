package com.datastructures.queues

import com.datastructures.lists.DoublyLinkedList

/**
 * implementation of the queue based on doubly linked list
 */

class LinkedListQueue<T> : Queue<T> {

    private val list = DoublyLinkedList<T>()
    private var size = 0

    override fun enqueue(element: T): Boolean {
        list.append(element)
        size++
        return true
    }

    override fun dequeue(): T? {
        val firstNode = list.first ?: return null
        size--
        return list.remove(firstNode)
    }

    override val count: Int
        get() = size

    override fun peek(): T? = list.first?.value

    override fun toString(): String = list.toString()
}

fun main() {
    val queue = LinkedListQueue<String>().apply {
        enqueue("Ray")
        enqueue("Brian")
        enqueue("Eric")

    }
    println(queue)
    queue.dequeue()
    println(queue)
    println("Next up: ${queue.peek()}")
}