package com.okman.exkepper

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket


fun main(args: Array<String>) {

    KepperServer.startRun()
    val reader = InputStreamReader(System.`in`)
    while (true)
    {
        reader.forEachLine {
            if(it.equals("show"))
            {
                println("客户数量:${KepperServer.clients.size}")
            }
        }
    }
}

object KepperServer {

    var clients = mutableListOf<ExClient>()

    var serverSocket = ServerSocket(8888)

    fun startRun() {
        Thread {
            println("服务器开启监听....")
            while (true) {
                val socket = serverSocket.accept()
                val client = ExClient(socket)
                clients.add(client)
                client.startTalk()
            }
        }.start()
    }
}


class ExClient(val socket: Socket) {

    fun startTalk() {
        println("${socket.remoteSocketAddress} 开启连接会话")
        try {
            process(socket.getInputStream())
            println("客户结束会话")
        } catch (ex: IOException) {
            println("客服端异常断开")
        } finally {
            KepperServer.clients.remove(this)
        }
    }

    val maxDataLen = 2048
    val buffer = ByteArray(2)
    val data = mutableListOf<Byte>()

    fun process(inputStream: InputStream)
    {
        while (true)
        {
            val startSuffex0 = inputStream.read()
            if(startSuffex0==-1)
                return
            val startSuffex1 = inputStream.read()
            if(startSuffex1 == -1)
                return
            if(startSuffex0 == '\n'.toInt() && startSuffex1 == '\n'.toInt())
            {
                data.clear()
                var datalen = 0
                while (true)
                {
                    inputStream.read(buffer,0,2)
                    datalen+=2
                    if(buffer[0]!= '\n'.toByte() && buffer[1]=='\n'.toByte())
                    {
                        data.add(buffer[0])
                        inputStream.read(buffer,0,1)
                        datalen+=1
                    }
                    if((buffer[0] == '\n'.toByte() && buffer[1] == '\n'.toByte()) || datalen==maxDataLen) {
                        break
                    }
                    data.addAll(buffer.asList())
                }
                println("userdata : ${String(data.toByteArray())}")
            }
        }
    }
}
