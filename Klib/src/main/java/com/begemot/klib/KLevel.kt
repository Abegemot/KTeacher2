package com.begemot.klib

import android.content.ContentValues
import com.begemot.klib.KLevel.sa.tName
import org.jetbrains.anko.db.classParser

/**
 * Created by dad on 03/02/2018.
 */

interface   MI{
    abstract val tName:String
    abstract val tSelect:Array<String>
    abstract val tDBCreate:String
}

data class KLevel( var id: Long =0,var nLocal:Int=0,var nServer:Int=0, var desc: String = ""):MI{

    override fun toString(): String { return "PATATA"+desc   }

    override val tSelect:Array<String> get() =  sa.tSelect

    override val tName:String    get() = sa.tName

    override val tDBCreate: String    get() = sa.DBCreate


     companion object sa {

        const val tName   : String = "KLEVEL"

        const val DBCreate= "CREATE TABLE if not exists ${tName}(ID  integer PRIMARY KEY ,NLOCAL int,NSERVER int,DESC text)"

        val tSelect: Array<String> = arrayOf("ID","NLOCAL","NSERVER","DESC")

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
            patata()
            return A
        }




    }
}

fun patata(){

      val L: List<KLevel> = getAll<KLevel>()
    L.forEach{
        println(it)
    }

}


inline fun <reified T:MI>  getAll():List<T>{

    val L= mutableListOf<T>()
    val rowParser = classParser<T>()
    //var s=T

    for (i in 1..10){
        val s=T::class.java.newInstance()

        s.tName
        s.tSelect
        //s.id=i.toLong()
        L.add(s)
    }
    return L

}

