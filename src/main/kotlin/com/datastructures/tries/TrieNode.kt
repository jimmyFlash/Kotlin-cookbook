package com.datastructures.tries

/**
 * implementation of a trie tree node
 *  key : holds the data for the node. This is optional because the root node of the trie has no key
 *  parent : holds a reference to its parent. This reference simplifies remove() later on
 */
class TrieNode<Key>(var key: Key?, var parent: TrieNode<Key>?) {

    // In a trie, a node needs to hold multiple different elements.
    // a children map to help with that.
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isTerminating = false // is the node a terminal node
}