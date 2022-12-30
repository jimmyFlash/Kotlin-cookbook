package com.datastructures.queues

import com.datastructures.queues.base.StackImpl
import com.utils.example

class StackQueue<T> : Queue<T> {

    private val leftStack = StackImpl<T>()
    private val rightStack = StackImpl<T>()

    override fun enqueue(element: T): Boolean {
        rightStack.push(element)
        return true
    }

    override fun dequeue(): T? {
        if (leftStack.isEmpty) { // Check to see if the left stack is empty
            transferElements() // transfer the elements from the right stack  in reversed order
        }
        return leftStack.pop() // Remove the top element from the left stack
    }

    override val count: Int
        get() = leftStack.count + rightStack.count

    // O(n)
    override fun peek(): T? {

        if (leftStack.isEmpty) {
            // If the left stack is empty, you use transferElements(). That way,
            //leftStack.peek() will always return the correct element or null
            transferElements()
        }
        // If the left stack is not empty, the
        //element on top of this stack is at the front of the queue
        return leftStack.peek()
    }

    // To check if the queue is empty, simply check that both the left and right stack are
    //empty O(1)
    override val isEmpty: Boolean
        get() = leftStack.isEmpty && rightStack.isEmpty

    /**
     * transfer the elements  from the right stack into the left stack.
     * That needs to happen whenever the left stack is empty
     * pop elements from the right stack and push them into the left
     * stack.
     */
    private fun transferElements() {
        var nextElement = rightStack.pop()
        while (nextElement != null) {
            leftStack.push(nextElement)
            nextElement = rightStack.pop()
        }
    }

    override fun toString(): String {
        return "Left stack: \n$leftStack \n Right stack:  \n$rightStack"
    }
}



fun main() {

    "Queue with Double Stack" example {
        val queue = StackQueue<String>().apply {
            enqueue("Ray")
            enqueue("Brian")
            enqueue("Eric")
        }
        println(queue)
        queue.dequeue()
        println(queue)
        println("Next up: ${queue.peek()}")
    }

}