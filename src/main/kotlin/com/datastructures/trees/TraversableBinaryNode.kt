package com.datastructures.trees

abstract class TraversableBinaryNode
    <
        Self : TraversableBinaryNode<Self, T>, // reference to the class (Node) extending this class
        T // value assigned to the extension class (Node)
    >(var value: T) {

    // a binary tree node should have left and right children
    var leftChild: Self? = null
    var rightChild: Self? = null

    /**
     *  binary tree traversal methods
     *  in-order : left child - parent - right child
     */
    fun traverseInOrder(visit: VisitorNode<T>) {
        leftChild?.traverseInOrder(visit)
        visit(value)
        rightChild?.traverseInOrder(visit)
    }

    /**
     *  binary tree traversal methods
     *  pre-order :  parent - left child - right child
     */
    fun traversePreOrder(visit: VisitorNode<T>) {
        visit(value)
        leftChild?.traversePreOrder(visit)
        rightChild?.traversePreOrder(visit)
    }

    /**
     *  binary tree traversal methods
     *  post-order : left child - right child - parent
     */
    fun traversePostOrder(visit: VisitorNode<T>) {
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }
}