package com.begemot.KTeacher

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.begemot.KTeacher.KApp.Companion.KPREF_FILENAME
import com.begemot.KTeacher.KApp.Companion.LANGITEACH
import com.begemot.KTeacher.KApp.Companion.LANGMYSTUDENTS
import com.begemot.KTeacher.KApp.Companion.ROMANIZED
import com.begemot.KTeacher.KApp.Companion.SHOWALLMENUS
import com.begemot.KTeacher.koin.SubjectModule
import com.begemot.KTeacher.koin.zProviderModule
import com.begemot.klib.KLesson
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin

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
        const val ROMANIZED="romanized"
        const val SHOWALLMENUS="showallmenus"

        var needupdatelessons=false
        var cpLessons =0
        var newNameLesson=""
        var lesson=KLesson()
        lateinit var ctx:Context
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)
        ctx=applicationContext
        prefs = Preferences(applicationContext)
        startKoin(this, listOf(SubjectModule, zProviderModule))
    }
    fun APS3(app: AppCompatActivity){
        val i = app.baseContext.packageManager.getLaunchIntentForPackage(app.baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        app.finish()
        app.startActivity(i)
    }

    fun getctx():Context{
        return applicationContext
    }

    fun getLang():String{
        //val pr:Preferences= KApp.prefs!!
        //val lang=llang[prefs!!.sLangIteach] //+ llang[prefs.sLangmyStudents]
        val lang=llang[prefs!!.sLangIteach]+llang[prefs!!.sLangmyStudents]
        return lang
    }

    fun getLang2(ctx:Context):String{
        val pref2=Preferences(ctx)
        //val lang=llang[pref2.sLangmyStudents]
        val lang=llang[pref2.sLangIteach]
        return lang
    }

    fun initialized(ctx: Context):Boolean{
        val prefs:SharedPreferences=ctx.getSharedPreferences(KPREF_FILENAME,Context.MODE_PRIVATE)
        if (!prefs.contains(LANGITEACH)) return false
        return true
    }

}

val Kprefs:Preferences by lazy{
     KApp.prefs!!
}

class Preferences(context:Context){
     val K_prefs:SharedPreferences=context.getSharedPreferences(KPREF_FILENAME,Context.MODE_PRIVATE)
     var sLangIteach:Int
     get() = K_prefs.getInt(LANGITEACH,4)
     set(value) = K_prefs.edit().putInt(LANGITEACH,value).apply()
     var sLangmyStudents:Int
     get() = K_prefs.getInt(LANGMYSTUDENTS,4)
     set(value) = K_prefs.edit().putInt(LANGMYSTUDENTS,value).apply()
     var bRomanized:Boolean
     get() = K_prefs.getBoolean(ROMANIZED,false)
     set(value) = K_prefs.edit().putBoolean(ROMANIZED,value).apply()
     var bAllMenus:Boolean
     get() = K_prefs.getBoolean(SHOWALLMENUS,true)
     set(value) = K_prefs.edit().putBoolean(SHOWALLMENUS,value).apply()

}

