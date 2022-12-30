package com.datastructures.trees

import java.lang.Integer.max

private typealias BinaryVisitor<T> = (T) -> Unit

class BinaryNode<T>(var value: T) {

    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    // find the minimum node in a subtree
    val min: BinaryNode<T>
        get() = leftChild?.min ?: this



    /**
     * If the current node has a left child, recursively visit this child first.
     * Then visit the node itself.
     * If the current node has a right child, recursively visit this child.
     *
     */
    fun traverseInOrder(visit: BinaryVisitor<T>) {
        leftChild?.traverseInOrder(visit)
        visit(value)
        rightChild?.traverseInOrder(visit)
    }

    /**
     * Visits the current node first.
     * Recursively visits the left and right child.
     */
    fun traversePreOrder(visit: BinaryVisitor<T>) {
        visit(value)
        leftChild?.traversePreOrder(visit)
        rightChild?.traversePreOrder(visit)
    }

    /**
     * same as the traverse per order algorithm but
     * when there is no left or right child nodes
     * the lambda is called with null argument
     * time complexity of O(n).
     */
    private fun traversePreOrderOrNull(visit: (T?) -> Unit) {
        visit(value)
        leftChild?.traversePreOrderOrNull(visit) ?: visit(null)
        rightChild?.traversePreOrderOrNull(visit) ?: visit(null)
    }


    /**
     * Recursively visits the left and right child.
     * Only visits the current node after the left and right
     * child have been visited recursively.
     */
    fun traversePostOrder(visit: BinaryVisitor<T>) {
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }



    /*
        Given a binary tree, find the height of the tree.
        The height of the binary tree is determined by the distance between the root and the furthest leaf.
        The height of a binary tree with a single node is zero since the single node is both the root and the
        furthest leaf.

        SOL: recursively call the height function. For every node you visit,
        you add one to the height of the highest child. If the node is null, you return -1.

        This algorithm has a time complexity of O(n) since you need to traverse through all
        the nodes. This algorithm incurs a space cost of O(n) since you need to make the
        same n recursive calls to the call stack
    */
    fun treeHeight(node: BinaryNode<T>? = this) : Int{

        return node?.let {
            1 + max(treeHeight(node.leftChild),  treeHeight(node.rightChild))
        } ?: -1

    }


    /*
       devise a way to serialize a binary
       tree into a list, and a way to deserialize the list back into the same binary tree

        serialization Sol: you traverse the tree and store the values into an array. The
        elements of the array have type T? since you need to keep track of the null nodes
        returns a new array containing the values of the tree in pre-order

     */

    fun serializeBinaryTree(node :BinaryNode<T> = this) :MutableList<T?>{
        val serializedList = mutableListOf<T?>()
        node.traversePreOrderOrNull {
            serializedList.add(it)
        }
        return serializedList
    }

    /*
        deserialization SOL :
        take each value of the array and reassemble it back to the tree
        iterate through the array and reassemble the tree in pre-order format
     */
    private fun deserializeBinaryTree(list: MutableList<T?>): BinaryNode<T?>?{
        // base case. If removeAt returns null, there are no more elements in
        //the array, thus you’ll end recursion here

        val rootValue = list.removeAt(list.size - 1) ?: return null
        // You reassemble the tree by creating a node from the current value and
        //recursively calling deserialize to assign nodes to the left and right children.
        //Notice this is very similar to the pre-order traversal
        val root = BinaryNode<T?>(rootValue)

        root.leftChild = deserializeBinaryTree(list)
        root.rightChild = deserializeBinaryTree(list)

        return root
    }

    fun deserializeOptimized(list: MutableList<T?>): BinaryNode<T?>?{
        return deserializeBinaryTree(list.asReversed())
    }



    override fun toString() = diagram(this)

    /**
     * method recursively creates a string representing the binary tree
     *
     */
    private fun diagram(binaryNode: BinaryNode<T>?,
                        top: String = "",
                        root: String = "",
                        bottom: String = ""): String {

        return binaryNode?.let {
            if (binaryNode.leftChild == null && binaryNode.rightChild == null) {
                "$root${binaryNode.value}\n"
            } else {
                diagram(binaryNode.rightChild,"$top ", "$top┌────","$top│ ") +
                        root +
                        "${binaryNode.value}\n" +
                        diagram(binaryNode.leftChild,"$bottom│ ", "$bottom└────", "$bottom ")
            }
        } ?: "${root}null\n"
    }

    /**
     * equals recursively checks two nodes and their descendants for equality.
     * time complexity of this function is O(n).
     * The space complexity of this function is O(n).
     *
     */
    override fun equals(other: Any?): Boolean {
        return if (other != null && other is BinaryNode<*>) {
            this.value == other.value &&
                    this.leftChild == other.leftChild &&
                    this.rightChild == other.rightChild
        } else {
            false
        }
    }
}
