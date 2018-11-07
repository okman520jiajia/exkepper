package com.okman.exkepper

import java.math.BigDecimal

data class Account(val userInfo: UserInfo)
{
    override fun toString() = "userName ${userInfo.userName} APIKey ${userInfo.apiKey} isauto ${userInfo.userParam.isDelegateByKepper}"
}

data class UserInfo(val userName:String,val apiKey:String,val pwd:String,val userParam: UserParam)

data class UserParam(var isDelegateByKepper:Boolean,val buyRankIndex:RankIndex,val seelRankIndex:RankIndex)

data class RankIndex(var firstRank:BigDecimal,var secRank:BigDecimal,var thirdRank:BigDecimal,var fourthRank:BigDecimal)
