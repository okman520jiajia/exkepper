package com.okman.exkepper.core

import java.math.BigDecimal
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

val SCHEME = "exkepper"

fun List<BigDecimal>.sum() = this.fold(BigDecimal(0)) { acc, bigDecimal ->
    var b = acc + bigDecimal
    b
}

fun List<BigDecimal>.avg() = this.sum().divide(this.size.toBigDecimal(), 5, BigDecimal.ROUND_HALF_UP)

fun Date.utcStr(): String {
    return utcFormat.format(this)
}

val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

operator fun Date.minus(i: Int): String {
    return utcFormat.format(this.time - i)
}

val publicKey = "okman520jiajia"

fun String.desEncrypt(key:String= publicKey):String
{
    return DESUtil.ENCRYPTMethod(this, key)
}

fun String.desDecrypt(key:String= publicKey):String
{
    return DESUtil.decrypt(this,key)
}

fun String.parseUri():Map<String,String>
{
    val uri = URI(this)
    val data = URLUtil.URLRequest(this)
    data["host"] = uri.host
    data["scheme"] = uri.scheme
    return data
}


object Action{
    const val LOGIN = "Login"
}