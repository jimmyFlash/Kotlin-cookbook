package com.datastructures.lists

import com.datastructures.lists.util.Node


// class for creating a custom linked list
class LinkedList<T> {

    // list must have head node, end node and a size
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    // check linked list size
    private fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     *  Adding a value at the front of the list
     * @param value value of the node to add at the end
     * @return the current linked list instance
     *
     *  time complexity is O(1)
     */
    fun push(value: T): LinkedList<T> {
        // create a new node, have it reference the current head
        // as next node since it will replace it
        head = Node(value = value, next = head)
        // case of a single node linked list the head is also the tail
        if (tail == null) {
            tail = head
        }
        size++ // update the list size
        return this@LinkedList
    }


    /**
     * Adding a value at the end of the list
     * @param value value of the node to add at the end
     *
     * time complexity is O(1)
     */
    fun append(value: T) {

        // if the list is empty, you’ll need to update both head and tail to the
        // new node. Since append on an empty list is functionally identical to push,
        // you invoke push to do the work for you.
        if (isEmpty()) {
            push(value)
            return
        }

        // create a new node after the current tail node. tail will
        // never be null here because you’ve already handled the case where
        // the list is empty in the if statement.
        tail?.next = Node(value = value)

        // Since this is tail-end insertion,
        // your new node is also the tail of the list
        tail = tail?.next
        size++
    }

    /**
     * tries to retrieve a node in the list based on the given index. Since you can
     * only access the nodes of the list from the head node, you’ll have to make iterative
     * traversals
     * @param index index of node in list
     *
     * time complexity O(n)
     */
    fun nodeAt(index: Int): Node<T>? {

        // create a new reference to head and keep track of the current number of
        //traversals
        var currentNode = head
        var currentIndex = 0  // index at head is 0

        // Using a while loop, you move the reference down the list until you reach the
        //desired index. Empty lists or out-of-bounds indexes will result in a null return
        //value
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    /**
     * inserts a new node at given index with value
     * @param value node value
     * @param afterNode the node instance to insert after
     *
     * time complexity is O(1)
     */
    fun insert(value: T, afterNode: Node<T>): Node<T> {
        // In the case where this method is called with the tail node, you call the
        //functionally equivalent append method. This takes care of updating tail.
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        // Otherwise, you create a new node and link its next property to the next node of
        //the list.
        val newNode = Node(value = value, next = afterNode.next)
        // You reassign the next value of the specified node to link it to the new node that
        //you just created
        afterNode.next = newNode
        size++
        return newNode
    }

    /**
     * remove a head node
     *
     * @return returns the value that was removed from the list
     * This value is optional since
     * it’s possible that the list is empty
     *
     * time complexity O(1)
     */
    fun pop(): T? {

        // decrement the size of the list of it's not empty
        if (!isEmpty())
            size--

        val result = head?.value // get the value of the head
        head = head?.next // move the reference of the next node after the head to become new head

        // if list is empty after head removal, then the tail should be null
        if (isEmpty()) {
            tail = null
        }
        return result
    }

    /**
     * Although you have a reference to the tail
     * node, you can’t chop it off without having
     * a reference to the node before it that's why you need to iterate
     * from start
     *
     * @return removed node value
     *
     * time complexity is O(n)
     */
    fun removeLast(): T? {

        // If head is null, there’s nothing to remove, so you return null
        val head = head ?: return null

        // If the list only consists of one node, pop will handle updating the head and tail references,
        // you can delegate this work to the pop function
        if (head.next == null)
            return pop()

        // update the size of the list
        size--

        // keep searching for the next node until current.next is null. This signifies
        // that current is the last node of the list
        var prev = head
        var current = head
        var next = current.next
        while (next != null) {
            prev = current
            current = next
            next = current.next
        }

        // Since current is the last node, you disconnect it using the prev.next reference.
        //You also make sure to update the tail reference
        prev.next = null
        tail = prev
        return current.value
    }

    /**
     * You’ll first find the node immediately before the node
     * you wish to remove and then unlink it
     *
     * @return the value of removed node at position
     *
     * time complexity of this operation is O(1),
     * but it requires you to have a
     * reference to a particular node beforehand
     *
     */
    fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value  // check the value of the next node referenced current

        // if the next node is the tail
        // reset the reference of the tail to be current node
        if (node.next == tail) {
            tail = node
        }
        // if the current node next value isn't null decrement the size of the list
        if (node.next != null) {
            size--
        }
        // update the current node next reference the node after the next (bypassing the removed node)
        node.next = node.next?.next
        return result
    }

    override fun toString(): String {
        return if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
    }
}

fun main() {

    // buildLinkedListNotOptimized()

    buildManagedLinkedList()
}

/*
    building long lists in this way is impractical
 */
fun buildLinkedListNotOptimized(){

    val node1 = Node(value = 1)
    val node2 = Node(value = 2)
    val node3 = Node(value = 3)
    node1.next = node2
    node2.next = node3
    println(node1)

}

fun buildManagedLinkedList(){

    val list = LinkedList<Int>().apply {

        // add nodes to the head of the list
        push(3)
        push(2)
        push(1)

        // add nodes to the end of the list
        append(4)
        append(5)
        append(6)

        println("Before inserting: $this")
        var middleNode = nodeAt(1)!!   // the head node for start
        for (i in 1..3) { // loop between nodes 1 and 3
            // insert new nodes after the current value of middleNode and update its reference
            middleNode = insert(-1 * i, middleNode)
        }
        println("After inserting: $this")

        val poppedValue =  pop()
        println("After popping list: $this")
        println("Popped value: $poppedValue")

        val removedValue =  removeLast()
        println("After removing last node: $this")
        println("Removed value: $removedValue")

        val index = 1
        val node =  nodeAt(index - 1)!!
        val removedValueAfterNode= removeAfter(node)
        println("After removing at index $index: $this")
        println("Removed value: $removedValueAfterNode")

    }
    println("Final list: $list")
}