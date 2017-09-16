package com.begemot.KTeacher

import android.content.ContentValues

/**
 * Created by Dad on 28-Aug-17.
 */
data class KExercise(var ID:Long=0, var IDLesson:Long=0, var TypeOfEx:Int=0, var TL1:String="", var TL2:String=""){

     override fun toString(): String { return TL1.toString()   }

     companion object {
         val tName   : String = "KEXERCISE"

         val DBCreate= "CREATE TABLE if not exists $tName(ID  integer PRIMARY KEY ,IDL integer,TOE integer,T1  text,T2  text)"

         val tSelect: Array<String> = arrayOf("ID","IDL","TOE","T1","T2")

         fun values(IDLesson: Long,TypeOfEx: Int,T1: String,T2: String)  : ContentValues {
             val A=ContentValues()
             A.put("IDL",IDLesson)
             A.put("TOE",TypeOfEx)
             A.put("T1",T1)
             A.put("T2",T2)
             return A
         }

         fun values2(KE:KExercise)  : ContentValues {
             val A=ContentValues()
             A.put("IDL",KE.IDLesson)
             A.put("TOE",KE.TypeOfEx)
             A.put("T1",KE.TL1)
             A.put("T2",KE.TL2)
             return A
         }


     }

}