package com.okman.exkepper

import com.okman.exkepper.core.api
import com.okman.exkepper.core.minus
import com.okman.exkepper.core.utcStr
import com.okman.exkepper.db.getUserByName
import com.okman.exkepper.model.KData
import com.okman.exkepper.model.Ticker
import java.math.BigDecimal
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
            calcKAvager()

        }
    }
}

val contractInfoUpdateThread get() = ContractInfoUpdateThread("合约信息更新线程")


val testUserThread get() = UserKeeperThread(yuanboAccount!!, yuanboAccount!!.userInfo.userName)

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
                            println("发现 ${instrument_id}开始计算买进价格")
                            println("一阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.firstRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.firstRank}")
                            println("二阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.secRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.secRank}")
                            println("三阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.thirdRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.thirdRank}")
                            println("四阶买进挂单价 ${kAvage} * ${accountInfo.userInfo.userParam.buyRankIndex.fourthRank} = ${kAvage * accountInfo.userInfo.userParam.buyRankIndex.fourthRank}")
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

}

//账户测试模拟信息
val yuanboAccount by lazy {
    getUserByName("yuanbo")
}


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
