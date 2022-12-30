package com.datastructures.queues

import com.datastructures.queues.base.StackImpl

/**
 * this is not the best implementation using ArrayList
 * because once elements is remove dall element sin teh arraylist need to shift
 * the places, not effective in large sets
 * all when size is full, array list needs to resize and may have unused space
 */
interface Queue<T> {

    // Inserts an element at the back of the queue and returns true if the
    //operation is successful
    fun enqueue(element: T): Boolean
    // Removes the element at the front of the queue and returns it
    fun dequeue(): T?

    val count: Int
        get

    // Checks if the queue is empty using the count property
    val isEmpty: Boolean
        get() = count == 0
    // Returns the element at the front of the queue without removing it
    fun peek(): T?
}

/**
 * time complexity depends on the queue implementation you select. For the
 * array-based queue, it’s overall _O(n) time complexity. dequeue takes _O(n)
 * time because it has to shift the elements to the left every time you remove the
 * first element.
 */
fun <T> Queue<T>.nextPlayer(): T? {
    // Get the next player by calling dequeue.
    // If the queue is empty, return null,
    // game has probably ended anyway
    val person = this.dequeue() ?: return null
    // enqueue the same person, this puts the player at the end of the queue
    this.enqueue(person)
    // Return the next player
    return person
}

/**
 * Return your reversed queue
 * time complexity is overall O(n).
 */

fun <T> Queue<T>.reverse() {
    //
    val aux = StackImpl<T>()
    // dequeue all the elements in the queue onto the stack
    var next = this.dequeue()
    while (next != null) {
        aux.push(next)
        next = this.dequeue()
    }
    // pop all the elements off the stack and insert them into the queue
    next = aux.pop()
    while (next != null) {
        this.enqueue(next)
        next = aux.pop()
    }
}