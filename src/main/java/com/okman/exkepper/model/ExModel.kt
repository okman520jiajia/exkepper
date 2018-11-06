package com.okman.exkepper.model

data class Ticker(
        val best_ask: String,//卖一价
        val best_bid: String,//买一价
        val high_24h: String,//24 小时最高价
        val instrument_id: String, // 合约 ID
        val last: String, //最新成交价
        val low_24h: String,//24 小时最低价
        val timestamp: String,
        val volume_24h: String//24 小时成交量，按张数统计
)


data class KData(val timestamp: String
                 , val low: String //最低价
                 , val high: String //最高价格
                 , val open: String //开盘价格
                 , val close: String //收盘价格
                 , val volume: String //交易量(张)
                 , val currencyVolume: String //按币种折算的交易量
                )