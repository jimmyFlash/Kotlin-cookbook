package com.datastructures.tries

import com.utils.example

/**
 * Trie class can store collections containing Keys
 */
class Trie<Key> {

    private val root = TrieNode<Key>(key = null, parent = null)

    fun insert(list: List<Key>) {
        // current keeps track of your traversal progress
        var current = root
        // A trie stores each element of a list in separate nodes. For each element of the list,
        //you first check if the node currently exists in the children map. If it doesn’t, you
        //create a new node. During each loop, you move current to the next node
        list.forEach { element ->
            if (current.children[element] == null) {
                current.children[element] = TrieNode(element, current)
            }
            current = current.children[element]!!
        }
        // After iterating through the for loop, current should be referencing the node
        //representing the end of the list, terminating node.
        current.isTerminating = true
    }

    /**
     * traverse the trie in a way similar to insert. You check every element of the
     * list to see if it’s in the tree. When you reach the last element of the list, it must be a
     * terminating element. If not, the list wasn’t added to the tree and what you’ve found
     * is merely a subset of a larger list
     *
     */
    fun contains(list: List<Key>): Boolean {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }

    fun remove(list: List<Key>) {
        // check if the collection is part of the trie and to point current
        // to the last node of the collection.
        var current = root
        list.forEach {
            val child = current.children[it] ?: return
            current = child
        }
        // not a terminating node, could also be part of another collection, do nothing and exit
        if (!current.isTerminating)
            return

        // set isTerminating to false so that the current node can be removed by the
        // loop in the next step
        current.isTerminating = false

        // If there are no other children
        //in the current node, it means that other collections do not depend on the current
        //node.
        //
        while (current.parent != null && current.children.isEmpty() && !current.isTerminating) {
            current.parent!!.children.remove(current.key)
            current = current.parent!!
        }
    }

    fun collections(prefix: List<Key>): List<List<Key>> {
        // verifying that the trie contains the prefix. If not, you return an empty
        // list.
        var current = root

        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }

        // After you’ve found the node that marks the end of the prefix, you call a recursive
        // helper method to find all the sequences after the current node.
        return collectionsPrefixes(prefix, current)
    }

    private fun collectionsPrefixes(prefix: List<Key>, node: TrieNode<Key>?): List<List<Key>> {

        // a MutableList to hold the results. If the current node is a terminating
        // node, you add the corresponding prefix to the results
        val results = mutableListOf<List<Key>>()

        if (node?.isTerminating == true) {
            results.add(prefix)
        }

        // check the current node’s children. For every child node, you
        // recursively call collections() to seek out other terminating nodes
        node?.children?.forEach { (key, node) ->
            results.addAll(collectionsPrefixes(prefix + key, node))
        }

        return results
    }
}

fun main() {

    "insert and contains" example {
        val trie = Trie<Char>()
        trie.insert("cute")
        if (trie.contains("cute")) {
            println("cute is in the trie")
        }
    }

    "remove" example {
        val trie = Trie<Char>()
        trie.insert("cut")
        trie.insert("cute")
        println("\n*** Before removing ***")
        assert(trie.contains("cut"))
        println("\"cut\" is in the trie")
        assert(trie.contains("cute"))
        println("\"cute\" is in the trie")
        println("\n*** After removing cut ***")
        trie.remove("cut")
        assert(!trie.contains("cut"))
        assert(trie.contains("cute"))
        println("\"cute\" is still in the trie")
    }
}