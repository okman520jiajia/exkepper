package com.okman.exkepper

import com.okman.exkepper.core.api
import com.okman.exkepper.model.KData
import com.okman.exkepper.model.Ticker
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

//3分钟K线6次均值 全局参数挂进、挂出参考值
var kAvage: BigDecimal = BigDecimal(0)

var currentPrice: Float = 0f
    get() {
        return 0f
    }

var ticker: List<Ticker>? = null

class ContractInfoUpdateThread(val des: String) : Thread(des) {
    override fun run() {
        super.run()
        println("合约信息跟新线程启动...")
        while (true) {
            api.getServerTime().subscribe {
                println("服务器UTC时间: ${it.iso}")
            }
            api.getTicker().subscribe {
                ticker = it
                println("全部合约信息：")

                for (t in it) {
                    println("合约ID: ${t.instrument_id}\n" +
                            "最新成交价: ${t.last}\n" +
                            "卖一价: ${t.best_ask}\n" +
                            "买一价: ${t.best_bid}"
                    )
                }
            }
            Thread.sleep(5000)
        }
    }
}

val contractInfoUpdateThread get() = ContractInfoUpdateThread("合约信息更新线程")


val testUserThread get() = UserKeeperThread(yuanboAccount, yuanboAccount.userInfo.userName)

//用户活动线程
class UserKeeperThread(val accountInfo: Account, des: String) : Thread(des) {
    override fun run() {
        super.run()
        if (accountInfo.userInfo.userParam.isDelegateByKepper) {
            println("账户<${accountInfo.userInfo.userName}>已开启自动托管模式开始自动运行...")
            while (true) {
                ticker?.let {
                    val target = it.filter { it -> it.instrument_id == "EOS-USD-181228" }
                    if (target?.size == 1) {
                        with(target[0])
                        {
//                            println("发现 ${instrument_id}开始计算买进价格")
//                            println("一阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.firstRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.firstRank}")
//                            println("二阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.secRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.secRank}")
//                            println("三阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.thirdRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.thirdRank}")
//                            println("四阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.fourthRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.fourthRank}")
                        }
                    }
                }
                Thread.sleep(3000)
            }
        } else {
            println("账户<${accountInfo.userInfo.userName}>未开启自动托管模式")
        }
    }
}

var kDatas:MutableList<KData> = mutableListOf()


fun main(args: Array<String>) {

    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//
//    for (i in 0..5)
//        api.getServerTime().subscribe {
//            println(df.format(it.iso))
//        }

//    var now = Date()
//    println(df.format(now))
//    val date2 = now.time + 6*3*60*1000
//    println(df.format(date2))
    var stime :Date = Date()

    api.getServerTime().subscribe {
        stime = Date((it.epoch.toBigDecimal()*BigDecimal(1000)).toLong())
        println("服务器时间: ${df.format(stime)}")
    }

    val end = df.format(stime)
    val start = df.format(stime.time-6*3*60*1000)

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
        println("时间 : ${df.format(Date(kd.timestamp.toLong()))}")
        println("最低价格 : ${kd.low}")
        println("收盘价格 :${kd.close}")
        sum += kd.close.toBigDecimal()
    }
    kAvage = sum.divide(kDatas.size.toBigDecimal())

    println("K均线值 $kAvage")

}

//账户测试模拟信息
val yuanboAccount by lazy {
    Account(UserInfo(userName = "袁波"
            , apiKey = "111222333"
            , userParam = UserParam(
            isDelegateByKepper = true,
            buyRankIndex = RankIndex(
                    firstRank = 0.9980f,
                    secRank = 0.9940f,
                    thirdRank = 0.98f,
                    fourthRank = 0.94f
            ), seelRankIndex = RankIndex(
            firstRank = 0.9980f,
            secRank = 0.9940f,
            thirdRank = 0.98f,
            fourthRank = 0.94f
    )
    )), listOf(CurrencyInfo(
            balance = 1000f, hold = 0f, available = 1000f
    )))
}