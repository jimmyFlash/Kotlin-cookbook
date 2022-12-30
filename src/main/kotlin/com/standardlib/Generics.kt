package com.standardlib

interface TestInterface<T>{
    val jimmy:T
}

interface InheritInterface1<T : Int> :TestInterface<T>{
    override val jimmy: T
        get() = 2 as T
}
/*
Generics are an excellent way to keep the code abstract and
let the objects specialize once instantiated
 */
class Box<T> {
    var content: T? = null
    fun put(content: T?) {
        this.content = content
    }

    fun retrieve(): T? {
        return content
    }

    fun isEmpty(): Boolean {
        return content == null
    }
}

fun main() {
    // Your box can handle any data type you want,
    // and you’ll be sure that whatever you
    //put in it, has the same data type when you remove it from the box.
    val box = Box<Int>()
    box.put(4)

    val boolBox = Box<Boolean>()
    boolBox.put(true)
    println("Is the box empty? ${boolBox.isEmpty()}")
}
