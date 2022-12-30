package com.datastructures.trees
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

 typealias VisitorAVL<T> = VisitorNode<T>

class AVLNode<T: Comparable<T>>( value: T):TraversableBinaryNode< AVLNode<T>, T >(value) {

  // each node has a max of 2 children left and right
//  var leftChild: AVLNode<T>? = null
//  var rightChild: AVLNode<T>? = null

  // height of a node is the longest distance
  // from the current node to a leaf node
  var height = 0

  // by default if left child has no height it will default to -1
  val leftHeight: Int
    get() = leftChild?.height ?: -1

  // by default if left child has no height it will default to -1
  val rightHeight: Int
    get() = rightChild?.height ?: -1

  // balance factor is delta left height - right height per node children
  val balanceFactor: Int
    get() = leftHeight - rightHeight

  // the min value is read form the left child being the lesser one
  // if it has a min value if not then the node itself is the min
  val min: AVLNode<T>?
    get() = leftChild?.min ?: this

  override fun toString() = diagram(this)

  private fun diagram(node: AVLNode<T>?,
                      top: String = "",
                      root: String = "",
                      bottom: String = ""): String {
    return node?.let {
      if (node.leftChild == null && node.rightChild == null) {
        "$root${node.value}\n"
      } else {
        diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
          root + "${node.value}\n" + diagram(node.leftChild, "$bottom│ ", "$bottom└──", "$bottom ")
      }
    } ?: "${root}null\n"
  }

}
