/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.datastructures.queues.base

/**
 * ring buffer has two “pointers” that keep track of two things
 * read and write
 *
 * ring buffer has a fixed size
 *
 * The 'read' pointer keeps track of the front of the queue
 * The 'write' pointer keeps track of the next available slot
 * so that you can override existing elements that have already been read.
 *
 * Each time that you add an item to the queue, the 'write' pointer increments by one
 *
 * whenever the read and write pointers are at the same index, that means the queue is empty.
 */
class RingBuffer<T>(private val size: Int) {

  // array list matching the size parameter
  private var array = ArrayList<T?>(size)
  private var readIndex = 0
  private var writeIndex = 0

  val count: Int
    get() = availableSpaceForReading

  // available space for reading = distance between ead and write pointers
  private val availableSpaceForReading: Int
    get() = (writeIndex - readIndex)

  val first: T?
    get() = array.getOrNull(readIndex)

  val isEmpty: Boolean
    get() = (count == 0)

  // available space for writing = size - reading space
  private val availableSpaceForWriting: Int
    get() = (size - availableSpaceForReading)

  val isFull: Boolean
    get() = (availableSpaceForWriting == 0)

  fun write(element: T): Boolean {
    return if (!isFull) {
      if (array.size < size) {
        array.add(element)
      } else {
        array[writeIndex % size] = element
      }
      writeIndex += 1
      true
    } else {
      false
    }
  }

  fun read(): T? {
    return if (!isEmpty) {
      val element = array[readIndex % size]
      readIndex += 1
      element
    } else {
      null
    }
  }

  override fun toString(): String {
    val values = (0 until availableSpaceForReading).map { offset ->
      "${array[(readIndex + offset) % size]!!}"
    }
    return values.joinToString(prefix = "[", separator = ", ", postfix = "]")
  }

}
