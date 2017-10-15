package com.begemot.KTeacher

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {




    @Test
    fun addition_isCorrect() {



        //var RE=Regex("[^\\p{L} \\@ \\p{Z}\\p{Nd}]")
        var RE=Regex("@\\p{L}+")
        testRegExp(RE)
        //test2()

    }

    fun test2(){
        val sT2="      me duele el corazon y.   .me da vuelta7s ?  121  ca@ca la cabeza @perros    @gatos   "
        var L=sT2.split(" ")
        //var L=s.split(Regex("\\P{L}+"))
        //  \\s    "(?= )"
        //elimina els strings nuls
        var L3=L.filter { it.length>0 }

        println("L:${L.toString()}")

        println("L3:${L3.toString()}")



    }



    //setEnable(REC&PLAY)
    fun testRegExp(rx: Regex){
        var sT2="      me duele el corazon y.   .me da vuelta7s ?  121  ca@ca la cabeza @perros    @gatos   "

        var RE=Regex("[^\\p{L} \\@ \\p{Z}\\p{Nd}]")

        println(sT2)
        var sOk  = sT2.replace(rx, " ")
        println(sOk)



    }




}
