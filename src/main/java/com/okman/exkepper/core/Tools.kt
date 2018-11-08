package com.okman.exkepper.core

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.math.BigDecimal
import java.nio.charset.Charset
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator


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

operator fun Date.minus(i: Int): String {
    return utcFormat.format(this.time - i)
}

val KEY_STR = "myKey"
val CHARSETNAME = "UTF-8"
val ALGORITHM = "DES"
val base64encoder = BASE64Encoder()

val key by lazy {
    val generator = KeyGenerator.getInstance(ALGORITHM)
    val secureRandom = SecureRandom.getInstance("SHA1PRNG")
    secureRandom.setSeed(KEY_STR.toByteArray())
    generator.init(secureRandom)
    generator.generateKey()
}

fun String.desEncrypt(): String = try {
    val bytes = this.toByteArray(Charset.forName(CHARSETNAME))
    val cipher = Cipher.getInstance(ALGORITHM)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val doFinal = cipher.doFinal(bytes)
    base64encoder.encode(doFinal)
} catch (ex: java.lang.Exception) {
    ""
}


fun String.desDecrypt(): String = try {
    val base64decoder = BASE64Decoder()
    val bytes = base64decoder.decodeBuffer(this)
    val cipher = Cipher.getInstance(ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, key)
    val doFinal = cipher.doFinal(bytes)
    String(doFinal, Charset.forName(CHARSETNAME))
} catch (e: Exception) {
    ""
}
