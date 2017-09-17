package com.begemot.KTeacher

import android.content.ContentValues
import android.renderscript.Short2
import java.security.PKCS12Attribute

/**
 * Created by Dad on 28-Aug-17.
 */
//data class KExercise(var ID:Long=0, var IDLesson:Long=0, var TypeOfEx:Int=0, var TL1:String="", var TL2:String="", var S1: ByteArray= byteArrayOf(100) ){


data class KExercise(var ID:Long=0, var IDLesson:Long=0, var TypeOfEx:Int=0, var TL1:String="", var TL2:String="", var S1: ByteArray=byteArrayOf(100)  ){
     override fun toString(): String { return TL1   }

     companion object {
         val tName   : String = "KEXERCISE"

         val DBCreate= "CREATE TABLE if not exists $tName(ID  integer PRIMARY KEY ,IDL integer,TOE integer,T1  text,T2  text,S1 blob)"

         val tSelect: Array<String> = arrayOf("ID","IDL","TOE","T1","T2","S1")

         fun values(IDLesson: Long,TypeOfEx: Int,T1: String,T2: String,S1:ByteArray)  : ContentValues {
             val A=ContentValues()
             A.put("IDL",IDLesson)
             A.put("TOE",TypeOfEx)
             A.put("T1",T1)
             A.put("T2",T2)
             A.put("S1",S1)
             return A
         }

         fun values2(KE:KExercise)  : ContentValues {
             val A=ContentValues()
             A.put("IDL",KE.IDLesson)
             A.put("TOE",KE.TypeOfEx)
             A.put("T1",KE.TL1)
             A.put("T2",KE.TL2)
             A.put("S1",KE.S1)
             return A
         }


     }

    fun pos(){
        try {
            val BA:ByteArray=ByteArray(0)
            val VA:ByteArray= byteArrayOf(1)
            val a:KExercise = KExercise(1,2,1,"jj","jj",BA )
            val b:KExercise= KExercise(S1 = BA)
            val c:KExercise= KExercise()
            val L:List<KExercise> = listOf(KExercise())

        } catch (e: Exception) {


        }
    }


}