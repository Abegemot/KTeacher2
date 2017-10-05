package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_z.*
import android.view.MotionEvent
import com.begemot.KTeacher.DBHelp.Companion.X
import org.jetbrains.anko.toast
import java.util.*


class ZActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener {
    lateinit var  DBH : DBHelp

    override fun onFragmentInteraction(uri: Uri){
             X.warn("")
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_z)


        DBH=DBHelp.getInstance(this)
       // eT1.setBackgroundResource(R.drawable.border_edit_text  )
        eT1.imeOptions = EditorInfo.IME_ACTION_DONE
        eT1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        eT1.setHorizontallyScrolling(false)
        eT1.setLines(4)
       // eT1.setSelection(eT1.text.toString().length)


    }


    fun pos(v: View){
        finish()

    }

    fun pos1(v:View){
        toast("whose making pos?")
        val s="afadfa fafafa fdaf adfgsdfh sdhh dfd jkyt ry ioy otioty tywr wtyyrwej yjyruk tiy ey  jrywe jety jru ikry ey uje ujeukj ryuk ket ketuk etuk ry k" +
                "tyerye ty ery ery e u ruiuri  rti rui rt uiri rtu ytu e ueuy eruy wruywru w we uwer wywr"

    }


    fun clickButton1(v:View){
       // ibMic.t
        if(ibMic.isEnabled) { ibMic.isEnabled=false }
        else ibMic.isEnabled=true

        if(ibSpeaker.isEnabled) ibSpeaker.isEnabled=false
        else ibSpeaker.isEnabled=true

        if(button2.isEnabled) button2.isEnabled=false
        else button2.isEnabled=true


    }

    fun clickMicro(v:View){
        toast("CLICK MICRO")
        DBH.createEX1Examples(this)

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


