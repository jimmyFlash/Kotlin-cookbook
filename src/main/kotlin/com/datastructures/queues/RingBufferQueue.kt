package com.datastructures.queues

import com.datastructures.queues.base.RingBuffer
import com.utils.example

class RingBufferQueue<T>(size: Int) : Queue<T> {

    private val ringBuffer: RingBuffer<T> = RingBuffer(size)

    /**
     * To append an element to the queue, you call write() on the ringBuffer. This
     * increments the 'write' pointer by one
     *
     * @return Boolean indicate whether the element has been successfully added
     * Since the queue has a fixed size
     * an O(1) operation
     */
    override fun enqueue(element: T): Boolean =
        ringBuffer.write(element)

    /**
     * checks if the queue is empty and, if so, returns null.
     * If not, it returns an item from the front of the buffer
     *
     * ring buffer increments the read pointer by one
     */
    override fun dequeue(): T? =
        if (isEmpty) null else ringBuffer.read()

    override val count: Int
        get() = ringBuffer.count

    override fun peek(): T? = ringBuffer.first

    override fun toString(): String = ringBuffer.toString()
}

fun main() {

    "Queue with Ring Buffer" example {
        val queue = RingBufferQueue<String>(10).apply {
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