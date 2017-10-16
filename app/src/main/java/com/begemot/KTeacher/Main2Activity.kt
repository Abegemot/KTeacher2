package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.toast
import java.util.*


class Main2Activity : AppCompatActivity() {

    lateinit var DBH : DBHelp
    private val X = KHelp(this.javaClass.simpleName)
    private var currentLessonID=0L


    override fun onCreate(savedInstanceState: Bundle?) {
        if(DEBUG)X.warn("")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setTitle(resources.getString(R.string.lesson))
        DBH=DBHelp.getInstance(this)

        currentLessonID=intent.getLongExtra("lessonID",0)
        if(currentLessonID==0L) lStatus.text=resources.getString(R.string.item_new)
        else lStatus.text=resources.getString(R.string.item_update)
        eT1.setText(intent.getStringExtra("lessonName"))




    }

    fun onClickAddLesson(view:View){
        if(DEBUG)X.warn("")
        val intent = Intent()
        var stitleLesson=eT1.text.toString()
        stitleLesson.trim()
        if (stitleLesson.length==0 )
        {
            toast("${resources.getString(R.string.empty_lesson)}")
            return
        }



        if(currentLessonID==0L){ //insert




           val nL=DBH.addLesson(eT1.text.toString())
           val i=nL.id.toInt()
           if(DEBUG)X.warn("new ID = $i")
           intent.putExtra("NAME", nL.name)
           intent.putExtra("ID", nL.id)
           if(i>-1)  setResult(Activity.RESULT_OK, intent)
           else setResult(Activity.RESULT_CANCELED, intent)
        }else{ //update
            val L=KLesson(currentLessonID,eT1.text.toString())
            DBH.updateLesson(L)
            intent.putExtra("NAME", L.name)
            intent.putExtra("ID", L.id)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()


    }

    override fun attachBaseContext(newBase: Context) {
        //curLang=getCurrentLang(newBase)

        /*   val sharedpreferences = newBase.getSharedPreferences("KPref",Context.MODE_PRIVATE)
           if (sharedpreferences.contains("lang")) {
               curLang=sharedpreferences.getString("lang", "none")
           } else curLang="en"

           val editor = sharedpreferences.edit()
           editor.putString("lang", curLang)
           editor.commit()
           if(DEBUG)X.warn("ZXXXXXXXXXXXXXXXX   lang  $curLang")*/



        //val lang:String= newBase.getString(R.string.app_lang)
        //if(DEBUG)X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("${KT.getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //if(DEBUG)X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }
}
