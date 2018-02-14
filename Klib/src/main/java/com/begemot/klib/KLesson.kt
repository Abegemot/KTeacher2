package com.begemot.klib

import android.content.ContentValues
import android.os.Parcel

/**
 * Created by Dad on 23-Aug-17.
 */
data class KLesson( var id: Long =0,var userid:Long=0,var level:Long=0,var name: String = "",var altname:String="",var romanization:String="",var status:Long=0):MI,KParcelable{

    //override fun toString(): String { return name   }
    private val X = KHelp(this.javaClass.simpleName)
    override val tSelect:Array<String>    get() = sa.tSelect
    override val tName:String             get() = sa.tName
    override val tDBCreate: String        get() = sa.DBCreate


    companion object sa{
        const val tName   : String = "KLESSONS"
        const val DBCreate= "CREATE TABLE if not exists ${tName}(ID  integer PRIMARY KEY,USERID integer, LEVEL integer, NAME text,ALTNAME text,ROMANIZATION text,STATUS integer)"
        val tSelect: Array<String> = arrayOf("ID","USERID","LEVEL","NAME","ALTNAME","ROMANIZATION","STATUS")


        fun values(nameLesson: String)  : ContentValues {
            val A= ContentValues()
            //A.put("IDL",idLesson)
            A.put("NAME",nameLesson)
            return A
         }

        fun values(KL: KLesson)  : ContentValues {
            val A=ContentValues()
            //for(i in KLesson.tSelect.indices){
            //      A.put(tSelect[i], KL.getVal(i))
            //}
            A.put(tSelect[0],KL.id)
            A.put(tSelect[1],KL.userid)
            A.put(tSelect[2],KL.level)
            A.put(tSelect[3],KL.name)
            A.put(tSelect[4],KL.altname)
            A.put(tSelect[5],KL.romanization)
            A.put(tSelect[6],KL.status)
            return A
        }

        @JvmField val CREATOR = parcelableCreator(::KLesson)


    }

    private constructor(p: Parcel) : this(
            id   = p.readLong(),
            userid=p.readLong(),
            level=p.readLong(),
            name = p.readString(),
            altname = p.readString(),
            romanization = p.readString(),
            status = p.readLong()
            )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeLong(userid)
        dest.writeLong(level)
        dest.writeString(name)
        dest.writeString(altname)
        dest.writeString(romanization)
        dest.writeLong(status)
    }

    fun getVal(i:Int):Any{
       return when(i){
            0->id
            1->userid
            2->level
            3->name
            4->altname
            5->romanization
            6->status
            else->""
        }

    }

    fun getAA():Array< Pair<String,Any>>{
        val K= ArrayList<Pair<String,Any>>()
        for(i in KLesson.tSelect.indices){
            X.err("$i    val ${this.getVal(i)}")
            if (i>0) {
                K.add(Pair(tSelect[i], this.getVal(i)))
            }
        }
        val J= arrayOfNulls<Pair<String,Any>>(K.size)
        return  K.toArray(J)
        //return arrayOf( Pair(tSelect[0],id),Pair(tSelect[1],userid),Pair(tSelect[2],level),Pair(tSelect[3],name),Pair(tSelect[4],altname),Pair(tSelect[5],romanization))
        //return arrayOf(Pair(tSelect[1],userid),Pair(tSelect[2],level),Pair(tSelect[3],name),Pair(tSelect[4],altname),Pair(tSelect[5],romanization))
    }



}
