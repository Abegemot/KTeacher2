package com.begemot.kteacher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main2.*
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.*


class Main2Activity : AppCompatActivity() {

    //lateinit var DB2 : myDB
    lateinit var DBH : DBHelp
    private val log = AnkoLogger("MYPOS")

    override fun onCreate(savedInstanceState: Bundle?) {
        log.warn("creatingMainAcctivity2")
        super.onCreate(savedInstanceState)
        log.warn("creatingMainAcctivity3")
        try {
            setContentView(R.layout.activity_main2)
        }catch( e:Exception){
            log.warn("creatingMainAcc4 $e.message" )
        }
      //  DB2=myDB.getInstance(this)
        log.warn("creatingMainAcctivity2 before dbhelp")
        DBH=DBHelp.getInstance(this)
        log.warn("creatingMainAcctivity2 PASSED")

    }

    fun onClickCancel(view: View){
        log.warn ("onClickCancel")
        finish()
    }

    fun onClickAddLesson(view:View){
//        parent.myList.adapter.not
//        parent.
//        toast("AddLesson myactivity2")
        log.warn("onclickaddlesson2")

        val p = DBH.addLesson(editText.text.toString())

        var message = editText.text.toString()
       // toast(message)
        val intentMessage = Intent()
        message=p.nameLeson
        // put the message to return as result in Intent
        intentMessage.putExtra("MESSAGE", message)
        // Set The Result in Intent
        var result :Int =2
        if(p.idLeson<0) result=-1
        setResult(result, intentMessage)
        finish()

    }
}
