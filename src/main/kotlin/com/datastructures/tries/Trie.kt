package com.datastructures.tries

import com.utils.example

/**
 * Trie class can store collections containing Keys
 */
class Trie<Key> {

    // the start node of any Trie
    private val root = TrieNode<Key>(key = null, parent = null)

    // A lists property that returns all the lists in the trie
    private val storedLists: MutableSet<List<Key>> = mutableSetOf()

    val lists: List<List<Key>>
        get() = storedLists.toList()

    val count: Int  // property that tells you how many lists are currently in the trie
        get() = storedLists.count()

    val isEmpty: Boolean  // property that returns true if the trie is empty, false otherwise.
        get() = storedLists.isEmpty()

    /**
     * add new node the Trie
     * @param list : list of key values to insert, this could be a string list of chars
     */
    fun insert(list: List<Key>) {
        // current keeps track of your traversal progress, we'll start from root node
        var current = root

        // A trie stores each element of a list in separate nodes. For each element of the list,
        //you first check if the node currently exists in the children map. If it doesn't, you
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
        // update the storedLists with the new list added to Trie
        storedLists.add(list)
    }

    /**
     * traverse the trie in a way similar to insert. You check every element of the
     * list to see if it’s in the tree. When you reach the last element of the list, it must be a
     * terminating element. If not, the list wasn't added to the tree and what you’ve found
     * is merely a subset of a larger list
     *
     * @param list : the list of elements to check in trie
     */
    fun contains(list: List<Key>): Boolean {
        var current = root // start from the root
        list.forEach { element ->
            // check if the element exist in the children map
            // if not return false
            val child = current.children[element] ?: return false
            current = child // update current node ref to found child
        }
        return current.isTerminating
    }

    /**
     *   check if the collection is part of the trie and to point current
     *   to the last node of the collection.
     *
     *   @param list : the list (collection) to remove
     */
    fun remove(list: List<Key>) {

        var current = root // start form the root
        // iterate over the collection
        list.forEach {
            // check if the element exist in the children map
            // if not exit
            val child = current.children[it] ?: return
            current = child // update current node ref to found child
        }
        // not a terminating node, could also be part of another collection, do nothing and exit
        if (!current.isTerminating)
            return

        // set isTerminating to false so that the current node can be removed by the
        // loop in the next step
        current.isTerminating = false
        // update the list of collections by removing this collection
        storedLists.remove(list)

        // If there are no other children in the current node,
        // it means that other collections do not depend on the current node.
        //
        while (current.parent != null && current.children.isEmpty() && !current.isTerminating) {
            current.parent!!.children.remove(current.key) // remove key value from parent chidren
            current = current.parent!! // update ref to current node to the parent of current node
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

    /**
     * helper method
     */
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

    "prefix matching" example {
        val trie = Trie<Char>().apply {
            insert("car")
            insert("card")
            insert("care")
            insert("cared")
            insert("cars")
            insert("carbs")
            insert("carapace")
            insert("cargo")
        }
        println("\nCollections starting with \"car\"")
        val prefixedWithCar = trie.collections("car")
        println(prefixedWithCar)
        println("\nCollections starting with \"care\"")
        val prefixedWithCare = trie.collections("care")
        println(prefixedWithCare)
    }
}