package com.okman.exkepper.core

import Constans
import io.reactivex.Observable
import jdk.nashorn.internal.objects.annotations.Getter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface API{

    @GET("/api/general/v3/time")
    fun getServerTime():Observable<ServerTime>

//    @GET("/api/account/v3/currencies")
//    fun getCurrencies():Observable<String>
//
//    @GET("/api/futures/v3/rate")
//    fun getRate(): Observable<String>

    @GET("/api/futures/v3/instruments/ticker")
    fun getTicker():Observable<List<Ticker>>
}

val retrofitBuild by lazy {
    Retrofit.Builder()
}

val strRetrofitBuild by lazy {
    retrofitBuild.baseUrl(Constans.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
}

val jsonRetrofitBuild by lazy {
    retrofitBuild.baseUrl(Constans.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

val api by lazy {
    jsonRetrofitBuild.create(API::class.java)
}

data class ServerTime(val iso:String , val epoch:String)


data class Ticker(
    val best_ask: String,//卖一价
    val best_bid: String,//买一价
    val high_24h: String,//24 小时最高价
    val instrument_id: String, // 合约 ID
    val last: String, //最新成交价
    val low_24h: String,//24 小时最低价
    val timestamp: String,//24 小时成交量，按张数统计
    val volume_24h: String
)