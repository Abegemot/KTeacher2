package com.begemot.KTeacher

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.begemot.KTeacher.KApp.Companion.KPREF_FILENAME
import com.begemot.KTeacher.KApp.Companion.LANGITEACH
import com.begemot.KTeacher.KApp.Companion.LANGMYSTUDENTS
import com.begemot.klib.KLesson

/**
 * Created by dad on 30/01/2018.
 */

class KApp:Application(){
    companion object {
        var prefs:Preferences?=null
        val llang= listOf("en","es","ru","zh","")
        const val KPREF_FILENAME="com.begemot.KTeacher.Kpreferences"
        const val LANGITEACH="langiteach"
        const val LANGMYSTUDENTS="langmystudents"

        var needupdatelessons=false
        var cpLessons =0
        var newNameLesson=""
        var lesson=KLesson()
    }

    override fun onCreate() {
        prefs= Preferences(applicationContext)
        super.onCreate()
    }
    fun APS3(app: AppCompatActivity){
        val i = app.baseContext.packageManager.getLaunchIntentForPackage(app.baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        app.finish()
        app.startActivity(i)
    }
    fun getLang():String{
        //val pr:Preferences= KApp.prefs!!
        //val lang=llang[prefs!!.sLangIteach] //+ llang[prefs.sLangmyStudents]
        val lang=llang[prefs!!.sLangIteach]+llang[prefs!!.sLangmyStudents]
        return lang
    }

    fun getLang2(ctx:Context):String{
        val pref2=Preferences(ctx)
        val lang=llang[pref2.sLangmyStudents]
        return lang
    }

    fun initialized(ctx: Context):Boolean{
        val prefs:SharedPreferences=ctx.getSharedPreferences(KPREF_FILENAME,Context.MODE_PRIVATE)
        if (!prefs.contains(LANGITEACH)) return false
        return true
    }

}

val prefs:Preferences by lazy{
     KApp.prefs!!
}

class Preferences(context:Context){


     val prefs:SharedPreferences=context.getSharedPreferences(KPREF_FILENAME,Context.MODE_PRIVATE)
     var sLangIteach:Int
     get() = prefs.getInt(LANGITEACH,4)
     set(value) = prefs.edit().putInt(LANGITEACH,value).apply()
     var sLangmyStudents:Int
     get() = prefs.getInt(LANGMYSTUDENTS,4)
     set(value) = prefs.edit().putInt(LANGMYSTUDENTS,value).apply()
}

