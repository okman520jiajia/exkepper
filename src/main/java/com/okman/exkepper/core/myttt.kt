package com.okman.exkepper.core

import Constans
import com.okman.exkepper.model.Ticker
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("/api/general/v3/time")
    fun getServerTime(): Observable<ServerTime>

    @GET("/api/futures/v3/instruments/{instrument_id}/candles")
    fun getCandles(@Path("instrument_id") instrument_id: String
                   , @Query("start") start: String
                   , @Query("end") end: String
                   , @Query("granularity") granularity: Int=180):Observable<List<List<String>>>

    @GET("/api/futures/v3/instruments/ticker")
    fun getTicker(): Observable<List<Ticker>>
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

//val gson: Gson by lazy {
//    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            .serializeNulls()
//            .create()
//}

val jsonRetrofitBuild: Retrofit by lazy {
    retrofitBuild.baseUrl(Constans.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

val api: API by lazy {
    jsonRetrofitBuild.create(API::class.java)
}

data class ServerTime(val iso: String, val epoch: String)



