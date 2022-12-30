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

package com.datastructures.lists

import com.datastructures.lists.util.Node

class DoublyLinkedList<T> {

  private var head: Node<T>? = null
  private var tail: Node<T>? = null

  val first: Node<T>?
    get() = head

  fun isEmpty(): Boolean {
    return head == null
  }

  /**
   * Adding a value at the end of the list
   * @param value value of the node to add at the end
   *
   * time complexity is O(1)
   */
  fun append(value: T) {
    // notice the previous parameter is set to tail the next is null by default
    val newNode = Node(value = value, previous = tail)
    if (isEmpty()) {
      head = newNode
      tail = newNode
      return
    }
    tail?.next = newNode
    tail = newNode
  }

  /**
   * tries to retrieve a node in the list based on the given index. Since you can
   * only access the nodes of the list from the head node, you’ll have to make iterative
   * traversals
   * @param index index of node in list
   *
   * time complexity O(n)
   */
  fun node(index: Int): Node<T>? {
    // create a new reference to head and keep track of the current number of
    //traversals
    var currentNode = head
    var currentIndex = 0 // index at head is 0

    // Using a while loop, you move the reference down the list until you reach the
    //desired index. Empty lists or out-of-bounds indexes will result in a null return
    //value
    while (currentNode != null && currentIndex < index) {
      currentNode = currentNode.next
      currentIndex += 1
    }

    return currentNode
  }

  /**
   * removes a node
   *
   * time complexity is O(1)
   */
  fun remove(node: Node<T>): T {

    // get reference to the nodes previous and next
    val prev = node.previous
    val next = node.next

    if (prev != null) { // if there's previous node

      prev.next = next
    } else {
      // set the head to the removed node next reference
      head = next
    }

    // update the next node's previous reference to the previous node
    next?.previous = prev

    // if there's no next node (removing tail node), set the tail reference to previous node
    if (next == null) {
      tail = prev
    }

    // clear the next, previous references of the node to be removed
    node.previous = null
    node.next = null

    // return the value of the removed node
    return node.value
  }

  override fun toString(): String {
    if (isEmpty()) {
      return "Empty list"
    }
    return head.toString()
  }

}

fun main() {
  val doubLst = DoublyLinkedList<Int>().apply{
    append(1)
    append(2)
    append(3)
    append(4)

    val nodeToRemove = node(2)
    val nextBeforeRemove = nodeToRemove?.next
    val prevBeforeRemove = nodeToRemove?.previous

    println("Node to remove: ${nodeToRemove?.value}, next: ${nodeToRemove?.next?.value}, " +
            "previous: ${nodeToRemove?.previous?.value}")

    if (nodeToRemove != null) {
      remove(nodeToRemove)
    }

    println("Check references on next after removed node:${nextBeforeRemove?.value}  " +
            "previous node value:${nextBeforeRemove?.previous?.value}," +
            " next node value:${nextBeforeRemove?.next?.value}")
    println("Check references on prev after removed node:${prevBeforeRemove?.value}  " +
            "next node value:${prevBeforeRemove?.next?.value}, " +
            "previous node value:${prevBeforeRemove?.previous?.value}")
  }

  println(doubLst)
}