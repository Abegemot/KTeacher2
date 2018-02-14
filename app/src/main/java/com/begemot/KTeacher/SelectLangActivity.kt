package com.begemot.KTeacher

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.begemot.klib.KHelp
import org.jetbrains.anko.*

class SelectLangActivity : KBaseActivity() {
    private val X = KHelp(this.javaClass.simpleName)

    fun aps(langITeach:String,langMyStudents:String){
        val intent=Intent()
        intent.putExtra("ITEACH",langITeach)
        intent.putExtra("MYSTUDENTS",langMyStudents)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title=resources.getText(R.string.choselang)
        SelectLangView().setContentView(this)
    }

    override fun onBackPressed() {
        if (KApp().initialized(this)){
            super.onBackPressed()
            return
        }
        alert(R.string.firsttimelang){ yesButton {  } }.show()
    }
}
