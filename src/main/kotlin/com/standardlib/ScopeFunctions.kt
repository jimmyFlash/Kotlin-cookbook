package com.standardlib


data class Car (var doors : Int = 2)

fun main() {

    val car = Car(2)

    printCarWithLet(car)

    printCarWithRun(car)

    printCarWithAlso(car)

    printCarWithApply(car)


}

/*
    These two functions [let, run] are “transformational” functions. They’re called
    "transformational" because the object they return can be different from the object
    you call the function on.
 */
fun printCarWithLet(car: Car?) {
    // let can return anything
    val isCoupe = car?.let {
        /*
            'let' uses the target object as it.
         */
        it.doors <= 2
    }

    if (isCoupe == true) {
        println("Coupes are awesome")
    }else{
        println("Not Coupe")
    }
}

fun printCarWithRun(car: Car?) {
    /*  more focused on the target object, the one you’re using to call the function.
     Inside the block, 'run'  passes the target object as this and
     isolates the block from the outer scope
     return value can still be anything
     */
    val isCoupe = car?.run {
         doors <= 2
    }
    if (isCoupe == true) {
        println("Coupes are awesome")
    }else{
        println("Not Coupe")
    }
}

fun printCarWithAlso(car: Car?) {
    /*
    Unlike with 'let' or 'run'
    which return a transformation, the 'also' function returns the original object
    also uses it to refer to the object inside the block and
    has access to the outer scope using this

    Since 'also' returns the same car object, you can use it to mutate the object and then
    chain other calls to it. In this com.utils.example, the check to see if the car is a coupe is within
    a let block, but since it was modified to have 4 doors within also, it won’t print
    "Coupes are awesome"
     */
    car?.also {
        it.doors = 4
    }.let {
        if (it?.doors != null && it.doors <= 2) {
            println("Coupes are awesome")
        }else{
            println("Not Coupe")
        }
    }
}

fun printCarWithApply(car: Car?) {
    /*
    'apply' is an 'also' that is isolated
    like a 'run'. It returns the same object as the target, and it uses this inside the block
     */
    car?.apply {
        doors = 4
    }.let {
        if (it?.doors != null && it.doors <= 2) {
            println("Coupes are awesome")
        }
    }
}