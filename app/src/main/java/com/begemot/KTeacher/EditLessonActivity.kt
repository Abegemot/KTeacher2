package com.begemot.KTeacher
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.Window
import android.view.WindowManager
import com.begemot.klib.*
import kotlinx.android.synthetic.main.editlessonactivity.*
import org.jetbrains.anko.toast

class EditLessonActivity : KBaseActivity() {
    lateinit var DBH : DBHelp
    private val X = KHelp(this.javaClass.simpleName)
    lateinit var kl:KLesson
    lateinit var initialkl:KLesson

    override fun onCreate(savedInstanceState: Bundle?) {
        if(DEBUG)X.warn("")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editlessonactivity)
        kl=intent.getParcelableExtra("lesson")
        initialkl=KLesson()
        initialkl=kl.copy()
        DBH=DBHelp.getInstance(this)


        var status=""
        if(kl.id==0L) status=resources.getString(R.string.item_new)
        else status =resources.getString(R.string.item_update)

        val s="${resources.getString(R.string.lesson)}&emsp;&emsp;&emsp;&emsp;&emsp;<i>$status</i>"
        setTitle(Html.fromHtml(s))

        eT1.setText(kl.name)
        eT2.setText(kl.altname)
        eT3.setText(kl.romanization)
    }

    fun onClickAddLesson(view:View){
        if(DEBUG)X.warn("")

        kl.name         = eT1.text.toString()
        kl.altname      = eT2.text.toString()
        kl.romanization = eT3.text.toString()

        if (kl.name.trim().length==0 ){
            toast("${resources.getString(R.string.empty_lesson)}")
            return
        }
        if(kl.equals(initialkl)){
            //toast("No changes")
            finish()
        }
        val intent = Intent()
        if(kl.id==0L){ //insert
            val nkl=DBH.addLesson(kl)
            kl=nkl.copy()
            intent.putExtra("lesson",kl)
           if(kl.id>-1)  setResult(Activity.RESULT_OK, intent)
           else setResult(Activity.RESULT_CANCELED, intent)
        }else{ //update
                DBH.updateLesson(kl)
                intent.putExtra("lesson", kl)
                setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

}
