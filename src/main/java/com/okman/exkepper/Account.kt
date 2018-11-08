package com.okman.exkepper

import java.math.BigDecimal

class Account(val userInfo: UserInfo) : Runnable {

    override fun run() {
        println("账户<${userInfo.userName}>已开启自动托管模式开始自动运行...")
        cancleOrder()
        while (true) {
            ticker?.let {
                val target = it.filter { it -> it.instrument_id == "EOS-USD-181228" }
                if (target?.size == 1) {
                    with(target[0])
                    {
                        println("发现 ${instrument_id}开始计算买进价格")
                        println("一阶买进挂单价 ${kAvage} * ${userInfo.userParam.buyRankIndex.firstRank} = ${kAvage * userInfo.userParam.buyRankIndex.firstRank}")
                        println("二阶买进挂单价 ${kAvage} * ${userInfo.userParam.buyRankIndex.secRank} = ${kAvage * userInfo.userParam.buyRankIndex.secRank}")
                        println("三阶买进挂单价 ${kAvage} * ${userInfo.userParam.buyRankIndex.thirdRank} = ${kAvage * userInfo.userParam.buyRankIndex.thirdRank}")
                        println("四阶买进挂单价 ${kAvage} * ${userInfo.userParam.buyRankIndex.fourthRank} = ${kAvage * userInfo.userParam.buyRankIndex.fourthRank}")
                    }
                }
            }
            println("${userInfo.userName} 处理完毕")
        }
    }

    private fun cancleOrder() {
        println("取消上一未成交订单")
    }

    fun updateAccountInfo()= synchronized(accountLock){

    }

    override fun toString() = "userName ${userInfo.userName} APIKey ${userInfo.apiKey} isauto ${userInfo.userParam.isDelegateByKepper}"

    val accountLock = java.lang.Object()

}

data class UserInfo(val userName: String, val apiKey: String, val pwd: String, val userParam: UserParam)

data class UserParam(var isDelegateByKepper: Boolean, val buyRankIndex: RankIndex, val seelRankIndex: RankIndex)

data class RankIndex(var firstRank: BigDecimal, var secRank: BigDecimal, var thirdRank: BigDecimal, var fourthRank: BigDecimal)
