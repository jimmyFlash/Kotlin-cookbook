package com.datastructures.stacks

import com.datastructures.lists.KotlinLinkedListImpl

interface Stack<Element> {
    fun push(element: Element)
    fun pop(): Element?
    fun peek(): Element?
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
}


class StackImpl<T > : Stack<T>{
    /*
       ArrayList is an obvious choice since
       it offers constant time insertions and deletions at one end via add and removeAt with the last
       index as a parameter. Usage of these two operations will facilitate the LIFO nature of
       stacks
    */
    private val storage = arrayListOf<T>()

    override fun push(element: T) {
        storage.add(element)
    }

    override fun pop(): T? {
        if (isEmpty) {
            return null
        }
        return storage.removeAt(count - 1)
    }

    /**
     * look at the top element of the stack without mutating its contents
     */
    override fun peek(): T? {
        return storage.lastOrNull()
    }

    override val count: Int
        get() = storage.size

    override fun toString() = buildString {
        appendLine("----top----")
        storage.asReversed().forEach {
            appendLine("$it")
        }
        appendLine("-----------")
    }

    // take an existing list and convert it to a stack so that the access
    // order is guaranteed
    companion object {
        fun <T> create(items: Iterable<T>): Stack<T> {
            val stack= StackImpl<T>()
            for (item in items) {
                stack.push(item)
            }
            return stack
        }
    }
}

/**
 * The time complexity of pushing the nodes into the stack is O(n). The time
 * complexity of popping the stack to print the values is also O(n). Overall, the time
 * complexity of this algorithm is O(n).
 *
 * Since you’re allocating a container (the stack) inside the function, you also incur an
 * O(n) space complexity cost.
 */
fun <T> KotlinLinkedListImpl<T>.printInReverse() {
    val stack = StackImpl<T>()
    for (node in this) {
        stack.push(node)
    }
    var node = stack.pop()
    while (node != null) {
        println(node)
        node = stack.pop()
    }
}

/*
time complexity of this algorithm is O(n), where n is the number of characters in
the string. This algorithm also incurs an O(n) space complexity cost due to the usage
of the Stack data structure
 */
fun String.checkParentheses(): Boolean {
//    Create a new stack and start going through your string, character by character.
    val stack = StackImpl<Char>()
    for (character in this) {
        when (character) {
            // Push every opening parenthesis into the stack
            '(' -> stack.push(character)
            // if you’re out of items on the stack your string is already imbalanced,
            // so you can immediately return from the function
            ')' -> if (stack.isEmpty) {
                return false
                } else {
                    // Pop one item from the stack for every closing parenthesis
                    stack.pop()
                }
        }
    }
    /*
    a balanced string is one that has popped all the opening
    parentheses it’s pushed (and not a single item more). That would leave the stack
    empty because you popped all the parentheses you pushed before
     */
    return stack.isEmpty
}

fun <Element> stackOf(vararg elements: Element): Stack<Element> {
    return StackImpl.create(elements.asList())
}

fun main() {

    val stack = StackImpl<Int>().apply {
        push(1)
        push(2)
        push(3)
        push(4)
    }
    println("Stack: $stack")
    val poppedElement = stack.pop()
    if (poppedElement != null) {
        println("Popped: $poppedElement")
    }
    println("Stack popped $stack")

    val list = listOf("A", "B", "C", "D")
    val listTOStack = StackImpl.create(list)
    println("List to Stack $listTOStack")
    println("List to Stack, Popped: ${listTOStack.pop()}")

    val initializedStack = stackOf(1.0, 2.0, 3.0, 4.0)
    println("initialized-Stack $initializedStack")
    println("initialized-Stack Popped: ${initializedStack.pop()}")

    val linkedListToStackPrintReversed = KotlinLinkedListImpl<Int> ()
        .apply {

            push(3)
            push(2)
            push(1)

            printInReverse()
        }
    println("A reversed Linked List print $linkedListToStackPrintReversed")

    val str = "(jamal(jimmy))"
    println("Is this string balanced $str? :  ${str.checkParentheses()}")
}