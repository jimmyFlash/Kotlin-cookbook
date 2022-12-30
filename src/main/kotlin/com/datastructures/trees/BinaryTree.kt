package com.datastructures.trees

fun main() {
    val zero = BinaryNode(0)
    val one = BinaryNode(1)
    val five = BinaryNode(5)
    val seven = BinaryNode(7)
    val eight = BinaryNode(8)
    val nine = BinaryNode(9)
    seven.leftChild = one
    one.leftChild = zero
    one.rightChild = five
    seven.rightChild = nine
    nine.leftChild = eight
    val tree = seven

    println(tree)

    //traversal algorithms has both a time and space complexity of O(n).

    println("traverse tree in-order")
    tree.traverseInOrder { println(it) }
    println()

    println("traverse tree pre-order")
    tree.traversePreOrder { println(it) }
    println()

    println("traverse tree post-order")
    tree.traversePostOrder { println(it) }
    println()


    println("Tree height: ${tree.treeHeight()}")


    val serializedBTreeToList = tree.serializeBinaryTree()
    println("serialized tree to list: $serializedBTreeToList")
    val prntDiagram = tree.deserializeOptimized(serializedBTreeToList)
    println(prntDiagram)
}


