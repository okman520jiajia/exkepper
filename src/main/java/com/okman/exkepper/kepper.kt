package com.okman.exkepper

import com.okman.exkepper.core.api
import com.okman.exkepper.core.minus
import com.okman.exkepper.core.utcStr
import com.okman.exkepper.model.KData
import com.okman.exkepper.model.Ticker
import java.io.IOException
import java.io.InputStreamReader
import java.math.BigDecimal
import java.net.ServerSocket
import java.net.Socket
import java.util.*

//3分钟K线6次均值 全局参数挂进、挂出参考值
var kAvage: BigDecimal = BigDecimal(0)

var currentPrice: Float = 0f
    get() {
        return 0f
    }

var ticker: List<Ticker>? = null


var kDatas:MutableList<KData> = mutableListOf()

fun calcKAvager()
{
    var servertime = Date()
    api.getServerTime().subscribe {
        println("epoch ${it.epoch}")
        servertime = Date((it.epoch.toBigDecimal()*BigDecimal(1000)).toLong())
        println("服务器时间: ${servertime.utcStr()}")
    }

    val end = servertime.utcStr()
    val start = servertime-6*3*60*1000

    println("Start Time $start")
    println("End Time $end")

    kDatas.clear()
    api.getCandles("EOS-USD-181116",start = start,end = end)
            .subscribe {
                for(kd in it)
                {
                    kDatas.add(KData(timestamp = kd[0],low = kd[1],high = kd[2],open = kd[3],close = kd[4],volume = kd[5],currencyVolume = kd[6]))
                }
            }
    println("采样点个数: ${kDatas.size}")

    var sum = BigDecimal(0)

    for(kd in kDatas)
    {
        println("TS: ${kd.timestamp}")
        println("时间 : ${Date(kd.timestamp.toLong()).utcStr()}")
        println("最低价格 : ${kd.low}")
        println("收盘价格 :${kd.close}")
        sum += kd.close.toBigDecimal()
    }
    kAvage = sum.divide(kDatas.size.toBigDecimal(),5,BigDecimal.ROUND_HALF_UP)
    println("K均线值 $kAvage")

}

object ExKepperServer{

    val serverSocket = ServerSocket(1135)
    var clients = mutableListOf<KepperClient>()
    fun start()
    {
        while (true)
        {
            val socket = serverSocket.accept()
            println("客户连接 ${socket.remoteSocketAddress} 连接服务器")
            var client = KepperClient(socket)
            clients.add(client)
            client.startTalk()
        }
    }
}


class KepperClient(val socket: Socket){

    val reader = InputStreamReader(socket.getInputStream())

    fun startTalk()
    {
        try {
            reader.forEachLine {
                if(it.startsWith("login"))
                {

                }
            }
        }catch (ex :IOException){
            ExKepperServer.clients.remove(this)
        }
    }
}