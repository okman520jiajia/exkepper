package com.okman.exkepper.core

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

fun List<BigDecimal>.sum() = this.fold(BigDecimal(0)) { acc, bigDecimal ->
    var b = acc + bigDecimal
    b
}

fun List<BigDecimal>.avg() = this.sum().divide(this.size.toBigDecimal(), 5, BigDecimal.ROUND_HALF_UP)

fun Date.utcStr():String
{
    return utcFormat.format(this)
}

val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

operator fun Date.minus(i: Int):String {
    return utcFormat.format(this.time -i)
}
