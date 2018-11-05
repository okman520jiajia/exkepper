package com.okman.exkepper

data class Account(val userInfo: UserInfo, val currencyInfos:List<CurrencyInfo>)

data class CurrencyInfo(var balance:Float,var hold:Float,var available:Float)

data class UserInfo(val userName:String,val apiKey:String,val userParam: UserParam)

data class UserParam(var isDelegateByKepper:Boolean,val buyRankIndex:RankIndex,val seelRankIndex:RankIndex)

data class RankIndex(var firstRank:Float,var secRank:Float,var thirdRank:Float,var fourthRank:Float)