package com.datastructures.lists

import com.datastructures.lists.util.Node


// class for creating a custom linked list
class KotlinLinkedListImpl<T> : MutableCollection<T>{

    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    //property itself is public, but its setter remains private
    override var size = 0
        private set

    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            append(element)
        }
        return true
    }

    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    // check linked list size
     override fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     *  Adding a value at the front of the list
     * @param value value of the node to add at the end
     *
     *  time complexity is O(1)
     */

    fun push(value: T): KotlinLinkedListImpl<T> {
        head = Node(value = value, next = head)
        // case of a single node linked list
        if (tail == null) {
            tail = head
        }
        size++
        return this@KotlinLinkedListImpl
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

        // if list is empty then the tail should be null
        if (isEmpty()) {
            tail = null
        }
        return result
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
        var currentIndex = 0

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
     * checks if the current collection is contained within our list
     *
     * time complexity for this method is O(n^2)
     * because it calls the method contains from within it own loop
     * which in turn also loops the linked list
     *
     */
    override fun containsAll(elements: Collection<T>): Boolean {
        for (searched in elements) {
            if (!contains(searched))
                return false
        }
        return true
    }

    /**
     * loop the linkedList checking if element exists
     * time complexity of O(n)
     * @param element the object to search in the list
     */
    override fun contains(element: T): Boolean {
        for (item in this) {
            if (item == element)
                return true
        }
        return false
    }


    override fun iterator(): MutableIterator<T> {
        return LinkedListIterator(this)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (!elements.contains(item)) {
                iterator.remove()
                result = true
            }
        }
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        for (item in elements) {
            result = remove(item) || result
        }
        return result
    }

    override fun remove(element: T): Boolean {
        //  Get an iterator that will help you iterate through the collection.
        val iterator = iterator()
        // Create a loop that checks if there are any
        // elements left, and gets the next one.
        while (iterator.hasNext()) {
            val item = iterator.next()
            // Check if the current element is the one you’re looking for,
            // and if it is, remove it.
            if (item == element) {
                iterator.remove()
                return true
            }
        }
        // Return a boolean that signals if an element has been removed
        return false
    }


    override fun toString(): String {
        return if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
    }
}

class LinkedListIterator<T>(
    private val list: KotlinLinkedListImpl<T>
    ) :MutableIterator<T> {

    private var index = 0
    private var lastNode: Node<T>? = null

    override fun next(): T {
        // You check that there are still elements to return.
        // If there aren’t, you throw an
        //exception
        if (index >= list.size)
            throw IndexOutOfBoundsException()

        // If this is the first iteration, there is no lastNode set,
        // so you take the first node of
        //the list. After the first iteration,
        // you can get the next property of the last node to
        //find the next one.
        lastNode = if (index == 0) {
           list.nodeAt(0)
        } else {
            lastNode?.next
        }
        // Since the lastNode property was updated, you need to update the index too
        index++
        return lastNode!!.value
    }

    override fun remove() {
        // when you’re at the beginning of the list. Using pop() will do
        //the trick.
        if (index == 1) {
            list.pop()
        } else {
            // If the iterator is somewhere inside the list, it needs to find the previous node.
            // That’s the only way to remove items from a linked list
            val prevNode = list.nodeAt(index - 2) ?: return
            // The iterator needs to step back so that next() returns the correct method the
            //next time. This means reassigning the lastNode and decreasing the index.
            list.removeAfter(prevNode)
            lastNode = prevNode
        }
        index--
    }

    /**
     * This method indicates whether your Iterable still has values to read
     */
    override fun hasNext(): Boolean {
        return index < list.size
    }
}

/**
 * extension to move though nodes in a list
 * starts with head node
 */
fun <T> KotlinLinkedListImpl<T>.printInReverse() {
    this.nodeAt(0)?.printInReverse()
}

/**
 * recursive extension function to print the reversed node values
 */
fun <T> Node<T>.printInReverse() {
    this.next?.printInReverse()
    // First, you check if you’ve reached the end of the list.
    // That’s the beginning of the reverse printing,
    // and you’ll not add an arrow there. The arrows start with the
    //second element of the reverse output.
    if (this.next != null) {
        print(" -> ")
    }
    // As the recursive statements unravel, the node data gets printed
    print(this.value.toString())
}


fun <T> KotlinLinkedListImpl<T>.getMiddle(): Node<T>? {
    /*
    In the while declaration, you bind the next node to fast.
    If there’s a next node, you update fast to the next node of fast,
    effectively traversing down the list twice. slow
    is updated only once.
    This is also known as the runner technique

    time complexity of this algorithm is O(n)
     */
    var slow = this.nodeAt(0)
    var fast = this.nodeAt(0)
    while (fast != null) {
        fast = fast.next
        if (fast != null) {
            fast = fast.next
            slow = slow?.next
        }
    }
    return slow
}

/**
 *
 * O(n) time complexity,
 * The only drawback is that you need a new
 * list, which means that the space complexity is also O(n).
 */
private fun <T> addInReverse(list: KotlinLinkedListImpl<T>, node: Node<T>) {
    // Get the next node of the list, starting from the one you’ve received as a
    //parameter.
    val next = node.next
    if (next != null) {
        // If there’s a following node, recursively call the same function;
        // however, now the starting node is the one after the current node.
        addInReverse(list, next)
    }
    // When you reach the end, start adding the nodes in the reverse order
    list.append(node.value)
}

// helper function
fun <T> KotlinLinkedListImpl<T>.reversed(): KotlinLinkedListImpl<T> {
    val result = KotlinLinkedListImpl<T>() // create new list
    val head = this.nodeAt(0) // get the head node
    if (head != null) { // if the list isn't empty, start recursive call
        addInReverse(result, head)
    }
    return result
}

/**
 * checks current list against another if one is empty return the other
 * otherwise return a new list that will hold both sorted item of the two lists
 * the type must implement the Comparable interface
 *
 */
fun <T : Comparable<T>> KotlinLinkedListImpl<T>.mergeSorted(
    otherList: KotlinLinkedListImpl<T>): KotlinLinkedListImpl<T> {

    // check if the current list is empty, return the other
    if (this.isEmpty())
        return otherList

    // check if the other list is empty, return the original
    if (otherList.isEmpty())
        return this

    val result = KotlinLinkedListImpl<T>()

    // You start with the first node of each list
    var left = nodeAt(0)
    var right = otherList.nodeAt(0)

    // The while loop continues until one of the lists reaches its end
    while (left != null && right != null) {

        // You compare the first nodes left and right to append to the result
        if (left.value < right.value) {
            left = append(result, left)
        } else {
            right = append(result, right)
        }
    }

    // handle the remaining nodes
    while (left != null) {
        left = append(result, left)
    }
    while (right != null) {
        right = append(result, right)
    }

    // if either of the above return a new list
    return result
}



/**
 * helper function
 * calls the append method of the KotlinLinkedListImpl to new node value to end of list
 * node values implement the Comparable interface
 * @return the next node from the current
 */
private fun <T : Comparable<T>> append(
    result: KotlinLinkedListImpl<T>,
    node: Node<T>): Node<T>? {

    result.append(node.value)
    return node.next
}


private fun main() {

    val other = KotlinLinkedListImpl<Int>()
    other.add(-1)
    other.add(0)
    other.add(2)
    other.add(2)
    other.add(7)

   val kotlinLinkedList = KotlinLinkedListImpl<Int> ()
       .apply {

        push(3)
        push(2)
        push(1)
        println("Current list $this")
        for (item in this) {
           println("print Double the value of $item >>  ${item * 2}")
        }


        add(4)
        add(5)
        add(6)
        add(7)
        add(8)
        add(9)
        add(10)
        println("Added new item to list print updated list : $this")
        remove(10)
        println("After remove (10) :: $this")

        retainAll(listOf(4,5,6,7,8))
        println("After retain (4,5,6,7,8):: $this")

        removeAll(listOf(7, 8))
        println("After remove (7,8) :: $this")

   }
    println("final list:  $kotlinLinkedList ")

    print("Print list in reverse : ")
    kotlinLinkedList.printInReverse()
    println()

    println("Get middle item: ${kotlinLinkedList.getMiddle()?.value}")

    println("Reversed: ${kotlinLinkedList.reversed()}")

    println("Merge to lists:-")
    println("Left: $kotlinLinkedList")
    println("Right: $other")
    println("Merged: ${kotlinLinkedList.mergeSorted(other)}")
}
