package com.begemot.klib

/**
 * Created by dad on 15/02/2018.
 */
import com.google.protobuf.ByteString
import java.util.concurrent.TimeUnit


// I learn Chinese in English  !!
// Usage 1 val sTablename = Klang.fromBytes(start.lang.lang)+"WORD"
// Usage 2 w.lang = Lang.newBuilder().setLang(Klang.toBytes(Klang.TE, Klang.ST)).build()
// Usage 3 Klang.toPrefix(Klang.TE, Klang.ST)
// els valors acabats en ST com RUST donen prefix "" per treballar amb les taules sense prefixar


object Klang {
    const val EN = 1
    const val RU = 2
    const val ZH = 3
    const val ES = 4
    const val TE = 126
    const val ST = 127

    val MLang = mapOf(RU to "RU", EN to "EN", ZH to "ZH", ES to "ES", TE to "TE", ST to "ST")
    val IMLang = mapOf("RU" to RU, "EN" to EN, "ZH" to ZH, "ES" to ES)

    fun toPrefix(f: Int, t: Int): String {
        return if (t == ST) "" else MLang[f] + MLang[t] + "_"
    }

    fun toBytes(i: Int, j: Int): ByteString {
        return ByteString.copyFrom(byteArrayOf(i.toByte(), j.toByte()))
    }

    fun fromBytes(B: ByteString?): String {
        return if (B?.size() == 2) toPrefix(B.byteAt(0).toInt(), B.byteAt(1).toInt()) else ""
    }

    fun fromBytesToInt(B: ByteString?): Int {
        return if (B?.size() == 2) B.byteAt(0).toInt() else 0
    }

    fun fromInt(i: Int): String {
        return MLang[i] + ""
    }

    fun toBytesFromString(s: String): ByteString {
        val s1=s.toUpperCase()
        val sTeacher = s1.subSequence(0, 2).toString()
        val sStudent = s1.subSequence(2, 4).toString()
        val i = IMLang[sTeacher]
        val j = IMLang[sStudent]
        if (i != null && j != null) return toBytes(i, j)
        else throw IllegalArgumentException("$s1  wrong lang  $sTeacher    $sStudent  $i   $j")
    }
}


fun execute(afun:()->Unit):String{
    val startTime=System.currentTimeMillis()
    afun()
    return  (System.currentTimeMillis() - startTime).milisToMinSecMilis()
}

fun Long.milisToMinSecMilis():String{
    val minuts  = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)-(60*minuts)
    val milis   = this-(minuts*60*1000)-(seconds*1000)
    /*val milis2   = this-1000*(minuts*60-seconds)
    if(milis!=milis2) println("milis  $milis   milis2  $milis2")*/
    return String.format("%d m, %d s, %d ms" ,minuts,seconds,milis).padEnd(25,' ')
}