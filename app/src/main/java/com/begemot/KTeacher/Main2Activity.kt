package com.begemot.KTeacher

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.*


class Main2Activity : AppCompatActivity() {

    lateinit var DBH : DBHelp
    private val X = KHelp(this.javaClass.simpleName)

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
}
