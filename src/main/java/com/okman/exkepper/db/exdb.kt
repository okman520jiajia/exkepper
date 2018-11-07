package com.okman.exkepper.db

import com.okman.exkepper.Account
import com.okman.exkepper.RankIndex
import com.okman.exkepper.UserInfo
import com.okman.exkepper.UserParam
import java.sql.DriverManager

val dburl = "jdbc:mysql://localhost:3306/excoin?serverTimezone=GMT%2B8"

val UserTable = "exkepper_users"


val sqlConn by lazy {
    Class.forName("com.mysql.cj.jdbc.Driver")
    DriverManager.getConnection(dburl, "root", "12345678")
}


fun getAllExUser() {
    val stmt = sqlConn.createStatement()
    val rs = stmt.executeQuery("select * from $UserTable")
    while (rs.next()) {
        println(rs.getString("user_name"))
    }
}

fun getUserByName(userName: String): Account? {
    val stmt = sqlConn.createStatement()
    val rs = stmt.executeQuery("select * from $UserTable where user_name = '$userName'")
    if (rs.next()) {
        val buyIndex = RankIndex(rs.getBigDecimal("buy_1_index")
                , rs.getBigDecimal("buy_2_index")
                , rs.getBigDecimal("buy_3_index")
                , rs.getBigDecimal("buy_4_index"))
        val sellIndex = RankIndex(rs.getBigDecimal("sell_1_index"),
                rs.getBigDecimal("sell_2_index"),
                rs.getBigDecimal("sell_3_index"),
                rs.getBigDecimal("sell_4_index"))
        val userParam = UserParam(rs.getBoolean("auto_delegate"),buyIndex,sellIndex)
        val userInfo = UserInfo(rs.getString("user_name"),rs.getString("pwd"),rs.getString("api_key"),userParam)
        return Account(userInfo)
    }
    return null
}