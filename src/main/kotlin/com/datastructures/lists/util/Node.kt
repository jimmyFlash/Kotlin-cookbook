package com.datastructures.lists.util

// main class for creating nodes of the linked list
// since a linked list will contain a set of nodes
// each node has a value and a reference to the next node in the list
// use of generics to hold any data type as value for the node
data class Node<T>(var value: T,
                   var next: Node<T>? = null,
                   var previous: Node<T>? = null) {

    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}