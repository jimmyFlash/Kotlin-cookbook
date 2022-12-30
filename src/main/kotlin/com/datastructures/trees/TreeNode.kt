package com.datastructures.trees

import com.datastructures.queues.ArrayListQueue

/**
 * type alias that references a lambda with tree node as parameter and returns Unit
 */
private typealias Visitor<T> = (TreeNode<T>) -> Unit


/**
 * Each node is responsible for a value
 * holds references to all of its children using a mutable list.
 * uses recursion to process the next node
 */
class TreeNode<T>(val value: T) {

    private val children: MutableList<TreeNode<T>> = mutableListOf()

    // adds a child node to a node.
    fun add(child: TreeNode<T>) = children.add(child)

    /**
     * just prints each node value recursively
     * Depth-first traversal starts at the root node and explores the tree as far as possible
     * along each branch before reaching a leaf and then backtracking
     */
    fun forEachDepthFirst(visit: Visitor<T>) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    /**
     * visits each node of the tree based on the
     * depth of the nodes. Starting at the root
     * every node on a level is visited before going
     * to a lower level
     */
    fun forEachLevelOrder(visit: Visitor<T>) {
        visit(this)
        val queue = ArrayListQueue<TreeNode<T>>()
        // start visiting the current node and putting all its children into the queue
        children.forEach {
            queue.enqueue(it)
        }
        var node = queue.dequeue()
        //start consuming the queue until it's empty
        while (node != null) {
            //Every time you visit a node, you also
            //put all it's children into the queue
            //This ensures that all node at the same level are
            //visited one after the other
            visit(node)
            node.children.forEach { queue.enqueue(it) }
            node = queue.dequeue()
        }
    }

    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null
        forEachLevelOrder {
            if (it.value == value) {
                result = it
            }
        }
        return result
    }

    /**
     * Print the values in a tree in an order based on their level. Nodes belonging to the
     * same level should be printed on the same line
     * This algorithm has a time complexity of O(n). Since you initialize the Queue data
     * structure as an intermediary container,
     * this algorithm also uses O(n) space
     */
    fun printEachLevel() {
        // You begin by initializing a Queue data structure to facilitate the level-order
        //traversal
        val queue = ArrayListQueue<TreeNode<T>>()
        //keep track of the
        //number of nodes you’ll need to work on before you print a new line
        var nodesLeftInCurrentLevel: Int
        queue.enqueue(this)
        // level-order traversal continues until your queue is empty
        while (queue.isEmpty.not()) {
            // Inside the first while loop, you begin by setting nodesLeftInCurrentLevel to
            //the current elements in the queue
            nodesLeftInCurrentLevel = queue.count
            // Using another while loop, you dequeue the first nodesLeftInCurrentLevel
            //number of elements from the queue. Every element you dequeue is printed
            //without establishing a new line. You also enqueue all the children of the node.
            while (nodesLeftInCurrentLevel > 0) {
                val node = queue.dequeue()
                node?.let {
                    print("${node.value} ")
                    node.children.forEach { queue.enqueue(it) }
                    nodesLeftInCurrentLevel--
                } ?: break
            }
            // generate the new line using println(). In the next iteration,
            //nodesLeftInCurrentLevel is updated with the count of the queue, representing
            //the number of children from the previous iteration.
            println()
        }
    }

}


fun main() {

    val tree = makeBeverageTree()
    println("<-----depth first print------>")
    tree.forEachDepthFirst { println(it.value) }
    println("<-----level first print------>")
    tree.forEachLevelOrder { println(it.value) }

    tree.search("ginger ale")?.let {
        println("Found node: ${it.value}")
    }
    tree.search("WKD Blue")?.let {
        println(it.value)
    }?: println("Couldn't find WKD Blue")

    tree.printEachLevel()
}

fun makeBeverageTree(): TreeNode<String> {
    val tree = TreeNode("Beverages")
    val hot = TreeNode("hot")
    val cold = TreeNode("cold")
    val tea = TreeNode("tea")
    val coffee = TreeNode("coffee")
    val chocolate = TreeNode("cocoa")
    val blackTea = TreeNode("black")
    val greenTea = TreeNode("green")
    val chaiTea = TreeNode("chai")
    val soda = TreeNode("soda")
    val milk = TreeNode("milk")
    val gingerAle = TreeNode("ginger ale")
    val bitterLemon = TreeNode("bitter lemon")

    tree.add(hot)
    tree.add(cold)

    hot.add(tea)
    hot.add(coffee)
    hot.add(chocolate)

    cold.add(soda)
    cold.add(milk)

    tea.add(blackTea)
    tea.add(greenTea)
    tea.add(chaiTea)

    soda.add(gingerAle)
    soda.add(bitterLemon)

    return tree
}