package com.begemot.KTeacher

import android.content.ContentValues

/**
 * Created by Dad on 28-Aug-17.
 */
data class KKindOfExercice( var ID:Long=0,var T1:String=" ",var T2:String=" " ){
     override fun toString(): String { return T1   }

     companion object{
          val tName   : String = "KINDOFEX"

          val DBCreate= "CREATE TABLE if not exists $tName(ID  integer PRIMARY KEY ,T1 text,T2 text)"

          val tSelect: Array<String> = arrayOf("ID","T1","T2")

          fun values(ID: Long,T1: String,T2: String)  : ContentValues {
               val A= ContentValues()
               A.put("ID",ID)
               A.put("T1",T1)
               A.put("T2",T2)
               return A
          }
     }


}

/*
 DB2.use { createTable("KINDOFEX", true, "ID" to INTEGER + PRIMARY_KEY, "T1" to TEXT, "T2" to TEXT) }
               var ds: SQLiteDatabase = DB2!!.writableDatabase
               ds.insert("KINDOFEX", "T1" to "Select from a set of russian words to form a frase", "T2" to "")
               ds.insert("KINDOFEX", "T1" to "Form par of words to find antonims", "T2" to "")
               ds.insert("KINDOFEX", "T1" to "who knows what", "T2" to "")
 */