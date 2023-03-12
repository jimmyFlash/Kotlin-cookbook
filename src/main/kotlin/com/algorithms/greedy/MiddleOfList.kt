package com.algorithms.greedy

/**
 * Given the head of a singly linked list, return the middle node of the linked list
 * If there are two middle nodes, return the second middle node.
 * Example 1:
 * Input:
 * head = [1,2,3,4,5]
 * Output:
 * [3,4,5]
 * Explanation:
 * The middle node of the list is node 3.
 *
 * Example 2:
 * Input:
 * head = [1,2,3,4,5,6]
 * Output:
 * [4,5,6]
 * Explanation:
 * Since the list has two middle nodes with values 3 and 4, we return the second one.
 *
 * Constraints:
 * The number of nodes in the list is in the range
 * [1, 100]
 * 1 <= Node.val <= 100
 *
 * using the 2 pointer method :-
 * 1- start with two variables both initialized to the head node of the list (fast, sow)
 * 2- create a loop updates the value of fast as long as it's not null
 * 3- update the value of fast check if its current node 'next' has a vlue
 * 4- check if fast != null
 * 5- update the value of fast and slow each calling the 'next' property of its node
 * 6- return slow as the middle node
 *
 */

/**
 * class representing a singly linked list
 * each node references its next sibling and has a value
 */
data class ListNode(val value: Int) {
    // The next property has a nullable type.
    // This is because for the last node, next would have to be null
    var next: ListNode? = null
}

/**
 *  using the two pointer method.
 *  One pointer will iterate through the list twice as fast as the other.
 *  When the faster pointer reaches the end of the list,
 *  the slower pointer will be in the middle
 */
fun middleNode(head: ListNode?): ListNode? {

    // 1 -two variables both initialized to the head node
    var slow = head
    // We have to do a 'safe call' with head?.next because head could be null
    var fast = head

    // 2 - keep looping as long as value of fast is not null
    while(fast != null){
        // 3
        fast = fast.next
        // 4
        if(fast != null){
            // 5
            fast = fast.next
            slow = slow?.next
        }
    }

    return slow
}

fun main() {

    val count = 6 // number of node
    val start = 0 // start node value
    val simpleLinkedList = mutableListOf<ListNode>() // the linked list is backed by mutable list
    for (nodeValue in start until count  ){
        // create a new list node
        val newNode = ListNode(nodeValue)
        // add to list
        simpleLinkedList.add(newNode)
        // check the previous node value if it's > start node value
        if((nodeValue - 1) >= start ){
            // get the previous node in the list
            val previousNode = simpleLinkedList[nodeValue - 1]
            // update its 'next' tot reference current node as next sibling
            previousNode.next = newNode
        }

    }
    val head = simpleLinkedList[0]
    println(middleNode(head)?.value)
}