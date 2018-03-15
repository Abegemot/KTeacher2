package com.begemot.klib

import android.content.ContentValues
import com.begemot.klib.DBHelp.Companion.X
import org.jetbrains.anko.db.classParser

/**
 * Created by dad on 03/02/2018.
 */






interface    MI{
    val tName:String
    val tSelect:Array<String>
    val tDBCreate:String
}

data class KLevel( var id: Long =0,var nLocal:Int=0,var nServer:Int=0, var desc: String = ""):MI{

    override fun toString(): String { return "PATATA"+desc   }

    override val tSelect:Array<String> get() =  sa.tSelect

    override val tName:String    get() = sa.tName

    override val tDBCreate: String    get() = sa.DBCreate


     companion object sa {

         const val tName   : String = "KLEVEL"

         val tSelect: Array<String> = arrayOf("ID","NLOCAL","NSERVER","DESC")

          val DBCreate= "CREATE TABLE if not exists ${tName}(${tSelect[0]}  integer PRIMARY KEY ,${tSelect[1]} int,${tSelect[2]} int,${tSelect[3]} text)"



        //fun qtSelect(): String { return tSelect }
        //override fun qtSelect(): String {
        //    return "wwwww"
        //}

        fun values(nameLesson: String)  : ContentValues {
            val A= ContentValues()
            //A.put("IDL",idLesson)
            A.put("NAME",nameLesson)
            return A
        }

        fun values(KL: KLevel)  : ContentValues {
            val A= ContentValues()
            A.put("ID",KL.id)
            A.put("NLOCAL",KL.nLocal)
            A.put("NSERVER",KL.nServer)
            A.put("DESC",KL.desc)
            return A
        }




    }
}


