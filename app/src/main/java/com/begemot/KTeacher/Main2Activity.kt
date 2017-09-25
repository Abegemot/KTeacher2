package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    lateinit var DBH : DBHelp
    private val X = KHelp(this.javaClass.simpleName)
  //  var curLang=""

    override fun onCreate(savedInstanceState: Bundle?) {
        X.warn("")
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main2)
        }catch( e:Exception){
            X.warn("creatingMainAcc4 $e.message" )
        }
        DBH=DBHelp.getInstance(this)
        X.warn("End")

    }

    fun onClickAddLesson(view:View){
        X.warn("")
        val nL=DBH.addLesson(editText.text.toString())
        val i=nL.id.toInt()
        X.warn("new ID = $i")
        val intent = Intent()
        intent.putExtra("NAME", nL.name)
        intent.putExtra("ID", nL.id)
        if(i>-1)  setResult(Activity.RESULT_OK, intent)
        else setResult(Activity.RESULT_CANCELED, intent)
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
           X.warn("ZXXXXXXXXXXXXXXXX   lang  $curLang")*/



        //val lang:String= newBase.getString(R.string.app_lang)
        //X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("${getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }
}
