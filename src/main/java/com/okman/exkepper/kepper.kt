package com.okman.exkepper

import com.okman.exkepper.core.api
import com.okman.exkepper.core.jsonRetrofitBuild

//3分钟K线6次均值 全局参数挂进、挂出参考值
var kAvage:Float = 0.0f
    get() = 5.0F

var currentPrice: Float = 0f
    get() {
    return 0f
}

class ContractInfoUpdateThread(val des:String):Thread(des)
{
    override fun run() {
        super.run()
        println("合约信息跟新线程启动...")
        while (true)
        {
            api.getServerTime().subscribe {
                println("服务器UTC时间: ${it.iso}")
            }
            api.getTicker().subscribe {
                println("全部合约信息：")
                for(t in it)
                {
                    println("合约ID: ${t.instrument_id}\n" +
                            "最新成交价: ${t.last}\n" +
                            "卖一价: ${t.best_ask}\n"+
                            "买一价: ${t.best_bid}"
                    )
                }
            }
            Thread.sleep(5000)
        }
    }
}

val contractInfoUpdateThread get() = ContractInfoUpdateThread("合约信息更新线程")

//用户活动线程
class UserKeeperThread(val accountInfo: Account,des:String):Thread(des)
{
    override fun run() {
        super.run()
        if(accountInfo.userInfo.userParam.isDelegateByKepper)
        {
            println("账户<${accountInfo.userInfo.userName}>已开启自动托管模式开始自动运行...")
            while (true)
            {

            }
        }else{
            println("账户<${accountInfo.userInfo.userName}>未开启自动托管模式")
        }
    }
}

fun main(args:Array<String>)
{
    contractInfoUpdateThread.start()

}

//账户测试模拟信息
val yuanboAccount by lazy {
    Account(UserInfo(userName="袁波"
            ,apiKey = "111222333"
            ,userParam = UserParam(
            isDelegateByKepper = true,
            buyRankIndex = RankIndex(
                    firstRank = 0.9980f,
                    secRank = 0.9940f,
                    thirdRank = 0.98f,
                    fourthRank = 0.94f
            ),seelRankIndex = RankIndex(
            firstRank = 0.9980f,
            secRank = 0.9940f,
            thirdRank = 0.98f,
            fourthRank = 0.94f
        )
    )), listOf(CurrencyInfo(
            balance = 1000f,hold = 0f,available = 1000f
    )))
}