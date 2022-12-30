package com.datastructures.trees

import com.utils.example
import java.lang.Integer.max
import kotlin.math.pow

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

class AVLTree<T : Comparable<T>> {

  var root: AVLNode<T>? = null

  fun insert(value: T, balance :Boolean = true) {
    root = insert(root, value, balance)
  }

  private fun insert(node: AVLNode<T>?, value: T, balance :Boolean): AVLNode<T> {

    node ?: return AVLNode(value)  // creates a new node if no node is supplied with given value

    // check the value against node value
    // if less recursive call on left child of current node
    // if larger recursive call on right child of current node
    if (value < node.value) {
      node.leftChild = insert(node.leftChild, value, balance)
    } else {
      node.rightChild = insert(node.rightChild, value, balance)
    }

    if (!balance)
      return node

    // call helper method to check if node is balanced
    // which returns a node
    val balancedNode = balanced(node)
    // update the node height by choosing max value of either it's left of right children
    balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1

    return balancedNode
  }

  fun remove(value: T, balance :Boolean = true) {
    root = remove(root, value, balance)
  }

  private fun remove(node: AVLNode<T>?, value: T, balance :Boolean ): AVLNode<T>? {
    node ?: return null

    when {
      value == node.value -> {
        // if not left or right children are found return null
        if (node.leftChild == null && node.rightChild == null) {
          return null
        }
        // no left child return the right one
        if (node.leftChild == null) {
          return node.rightChild
        }
        // no right child return the left one
        if (node.rightChild == null) {
          return node.leftChild
        }
        // if the min value of the right child is found
        // assign it to the parent node value
        node.rightChild?.min?.value?.let {
          node.value = it
        }
        // make recursive call on the right child
        node.rightChild = remove(node.rightChild, node.value, balance)
      }
      value < node.value ->
        node.leftChild = remove(node.leftChild, value, balance)
      else ->
        node.rightChild = remove(node.rightChild, value, balance)
    }

    if(!balance)
      return node

    val balancedNode = balanced(node)
    balancedNode.height = max( balancedNode.leftHeight, balancedNode.rightHeight  ) + 1

    return balancedNode
  }

  override fun toString() = root?.toString() ?: "empty tree"

  fun contains(value: T): Boolean {
    // 1
    var current = root

    // 2
    while (current != null) {
      // 3
      if (current.value == value) {
        return true
      }

      // 4
      current = if (value < current.value) {
        current.leftChild
      } else {
        current.rightChild
      }
    }
    return false
  }

  fun getNode(value: T): AVLNode<T>? {
    var current = root

    while (current != null) {

      if (current.value == value) {
        return current
      }
      current = if (value < current.value) {
        current.leftChild
      } else {
        current.rightChild
      }
    }
    return null
  }

  private fun leftRotate(node: AVLNode<T>): AVLNode<T> {
    // The right child is chosen as the pivot. This node replaces the rotated node as the
    // root of the subtree (it moves up a level)
    val pivot = node.rightChild!!
    // The node to be rotated becomes the left child of the pivot (it moves down a
    // level). This means that the current left child of the pivot must be moved
    // elsewhere.
    node.rightChild = pivot.leftChild
    // The pivot’s leftChild can now be set to the rotated node.
    pivot.leftChild = node
    // update the heights of the rotated node and the pivot
    node.height = max(node.leftHeight, node.rightHeight) + 1
    pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
    // return the pivot so that it can replace the rotated node in the tree.
    return pivot
  }

  private fun rightRotate(node: AVLNode<T>): AVLNode<T> {
    val pivot = node.leftChild!!
    node.leftChild = pivot.rightChild
    pivot.rightChild = node
    node.height = max(node.leftHeight, node.rightHeight) + 1
    pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
    return pivot
  }

  private fun rightLeftRotate(node: AVLNode<T>): AVLNode<T> {
    val rightChild = node.rightChild ?: return node
    node.rightChild = rightRotate(rightChild)
    return leftRotate(node)
  }

  private fun leftRightRotate(node: AVLNode<T>): AVLNode<T> {
    val leftChild = node.leftChild ?: return node
    node.leftChild = rightRotate(leftChild)
    return rightRotate(node)
  }

  /**
   * method that uses balanceFactor to decide whether a
   * node requires balancing or not
   * balanced() inspects the balanceFactor to determine the proper course of action.
   * All that’s left is to call balanced() at the proper spot
   */
  private fun balanced(node: AVLNode<T>): AVLNode<T> {
    return when (node.balanceFactor) {
      /*
        A balanceFactor of 2 suggests that the left child is heavier
        (that is, contains more nodes) than the right child.
        This means that you want to use either right or left-right rotations.
       */
      2 -> {
        if (node.leftChild?.balanceFactor == -1) {
          leftRightRotate(node)
        } else {
          rightRotate(node)
        }
      }

      /*
      A balanceFactor of -2 suggests that the right child is heavier
      than the left child. This means that you want to use either left or right-left rotations.
       */
      -2 -> {
        if (node.rightChild?.balanceFactor == 1) {
          rightLeftRotate(node)
        } else {
          leftRotate(node)
        }
      }

      /*
      The default case suggests that the particular node is balanced.
      There’s nothing to do here except to return the node.
       */
      else -> node
    }
  }

  /**
   * counting leaf nodes of AVL tree
   * perfectly balanced tree is a tree where all the leaves are at the same level,
   * and that level is completely filled
   * Since each node has two children, the number of leaf nodes doubles as the height
   * increases
   */
  fun leafNodes(height: Int): Int {
    return 2.0.pow(height).toInt()
  }

  /**
   * calculates the total number
   * of nodes in a balanced tree
   * total number of nodes is one less than the number of leaf nodes of the next level.
   * faster version of this in O(1)
   */
  fun nodes(height: Int): Int {
    return 2.0.pow(height + 1).toInt() - 1
  }

}

fun main(){
  "AVL tree ( unbalanced )" example{
    val avlTree = AVLTree<Int>().apply {
      insert(value = 50, balance = false)
      insert(value = 25, balance = false)
      insert(value = 75, balance = false)
      insert(value = 37, balance = false)
    }
    avlTree.insert(value = 40, balance = false)  // causes unbalanced tree
    println(avlTree)

    val checkNode = avlTree.getNode(40)
    println("value: ${checkNode?.value}, height: ${checkNode?.height}, balanceFactor: ${checkNode?.balanceFactor}")
  }


  "repeated insertions in sequence (balanced)" example {
    val tree = AVLTree<Int>()
    (0..14).forEach {
      tree.insert(it)
    }
    print(tree)
  }

  "Example of removing a value (balanced)" example {
    val tree = AVLTree<Int>()
    tree.insert(15)
    tree.insert(10)
    tree.insert(16)
    tree.insert(18)
    println("Before remove")
    print(tree)
    tree.remove(10)
    println("After remove")
    print(tree)

  }

  "using TraversableBinaryNode" example {
    val tree = AVLTree<Int>()
    (0..14).forEach {
      tree.insert(it)
    }
    println(tree)
    tree.root?.traverseInOrder { println(it) }
  }


}

