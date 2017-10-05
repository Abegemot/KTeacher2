package com.begemot.KTeacher

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_test3.*
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.toast
import java.util.*


class Test3Activity : AppCompatActivity(),PlayerFragment.OnFragmentInteractionListener {
    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var sAnswer:String
    lateinit var  DBH : DBHelp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test3)
        X.warn("entra")
        DBH=DBHelp.getInstance(this)
        //Todo Ui Ui Ui
        //lT.text=DBHelp.getKindEx(2)
        DBH.getKindEx(2)
        X.warn("surt")


        sAnswer=intent.getStringExtra("TL1")

        eT1.imeOptions = EditorInfo.IME_ACTION_DONE
        eT1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        eT1.setHorizontallyScrolling(false)
        //eT1.setLines(4)
        eT1.setSelection(eT1.text.toString().length)
    }

    override fun onStart() {
        super.onStart()
        //Todo asegurar que hi a file
        var f:PlayerFragment=fPlayer as PlayerFragment
        f.play(bPlay)

    }



    override fun onFragmentInteraction(uri: Uri){
        X.warn("")
    }

    fun testClick(v: View){
        val s=eT1.text
        val s2="proposed:  $s   real: $sAnswer"
        toast(s2)
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
        val newLocale= Locale("${KT.getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }

}
