package com.begemot.KTeacher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_z.*
import android.view.MotionEvent
import org.jetbrains.anko.toast


class ZActivity : AppCompatActivity() {
    lateinit var  DBH : DBHelp

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_z)


        DBH=DBHelp.getInstance(this)
        tV1.setBackgroundResource(R.drawable.border_edit_text  )
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
        tV1.setText(s)
        tV1.invalidate()
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

}


