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

    var slow = head
    // We have to do a 'safe call' with head?.next because head could be null
    var fast = head

    while(fast != null){
        fast = fast.next
        if(fast != null){
            fast = fast.next
            slow = slow?.next
        }
    }

    return slow
}

fun main() {

    val count = 6
    val start = 0
    val simpleLinkedList = mutableListOf<ListNode>()
    for (nodeValue in start until count  ){
        val n = ListNode(nodeValue)
        simpleLinkedList.add(n)
        if((nodeValue - 1) >= start ){
            val previousNode = simpleLinkedList[nodeValue - 1]
            previousNode.next = n
        }

    }
    val head = simpleLinkedList[0]
    println(middleNode(head)?.value)
}