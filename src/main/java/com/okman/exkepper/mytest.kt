package com.okman.exkepper


fun main(args: Array<String>) {

    Thread{
        while (true)
        {
            produce()
            Thread.sleep(3000)
        }
    }.start()

    Thread{
        while (true)
        {
            consume()
        }
    }.start()

}

var reslut : Int? = null

private val lock = java.lang.Object()

fun produce() = synchronized(lock) {
    println("calc result")
    reslut = 100
    lock.notifyAll()
}

fun consume() = synchronized(lock) {
    if(reslut==null)
        lock.wait()
    println("start work")
    reslut = null
}


