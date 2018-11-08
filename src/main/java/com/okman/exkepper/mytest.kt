package com.okman.exkepper

import com.okman.exkepper.db.getAllExUser
import java.net.URI
import java.net.URL
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {

//    val threadPool = ThreadPoolExecutor(100, 200, 24, TimeUnit.HOURS, ArrayBlockingQueue<Runnable>(100))
//    val accounts = getAllExUser()
//    val timer = Timer()
//
//    timer.schedule(object : TimerTask() {
//        override fun run() {
//            calcKAvager()
//            for (account in accounts) {
//
//                if (account.userInfo.userParam.isDelegateByKepper)
//                    threadPool.execute(account)
//
//            }
//        }
//    }, 0, 3 * 60 * 1000)

    val url = URI("exkeeper://Login?username=yuanbo&passwd=123")
    "exkeeper://AddAcount?"
    println(url.scheme)
    println(url.query)

}


