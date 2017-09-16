package com.begemot.KTeacher

import android.content.ContentValues

/**
 * Created by Dad on 23-Aug-17.
 */
data class KLesson( var id: Long =0,var name: String = ""){

    override fun toString(): String { return name.toString()   }

    companion object{
        val tName   : String = "KLESSONS"

        val DBCreate= "CREATE TABLE if not exists $tName(ID  integer PRIMARY KEY ,NAME text)"

        val tSelect: Array<String> = arrayOf("ID","NAME")

        fun values(nameLesson: String)  : ContentValues {
            val A= ContentValues()
            //A.put("IDL",idLesson)
            A.put("NAME",nameLesson)
            return A
        }
    }
}
