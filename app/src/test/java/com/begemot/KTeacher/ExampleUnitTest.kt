package com.begemot.KTeacher

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val REC:Int=1
    private val PLAY:Int=2
    private val STOP:Int=4




    @Test
    fun addition_isCorrect() {

        println("REC PLAY STOP")

        setEnable(REC or PLAY or STOP)

        println("REC")

        setEnable(REC)


        println("STOP")

        setEnable(STOP)

        println("STOP REC")

        setEnable(STOP or REC)


        assertEquals(4, 2 + 2)
    }


    //setEnable(REC&PLAY)
    fun setEnable(i:Int){
        println(i)

        println(i and (1 shl 0))
        println(i and (1 shl 1))
        println(i and (1 shl 2))
        println(i and (1 shl 3))

        if(i and (1 shl 0)!=0) {println("REC ENABLED")}
        if(i and (1 shl 1)!=0) {println("PLAY ENABLED")}
        if(i and (1 shl 2)!=0) {println("STOP ENABLED")}
        if(i and (1 shl 3)!=0) {}

    }




}
