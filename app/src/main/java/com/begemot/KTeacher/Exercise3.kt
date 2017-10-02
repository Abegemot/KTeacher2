package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import com.begemot.KTeacher.R.id.tVOriginal
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_exercise3.*
import kotlinx.android.synthetic.main.savedeletetest.view.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.util.*

class Exercise3 : AppCompatActivity() {

    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var  DBH : DBHelp
    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_exercise3)

        savedeletetest.bTest.setOnClickListener   { test(savedeletetest.bTest) }
        savedeletetest.bDelete.setOnClickListener { delete(savedeletetest.bDelete) }
        savedeletetest.bSave.setOnClickListener   { save(savedeletetest.bSave) }

        eT1.setBackgroundResource(R.drawable.border_edit_text  )
        eT1.imeOptions = EditorInfo.IME_ACTION_DONE
        eT1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        eT1.setHorizontallyScrolling(false)
        eT1.setLines(4)
        eT1.setSelection(eT1.text.toString().length)

        DBH=DBHelp.getInstance(this)

        lKindOf.text=DBH.getKindEx(2)

        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)

        if(currentExerciseID==0L)  lStatus.text=resources.getString(R.string.item_new)
        else {
            lStatus.text=resources.getString(R.string.item_update)
            val cExercise=DBH.loadExercise(currentExerciseID)
            eT1.setText(cExercise.TL1)
            //Todo ad sound to file
        }



    }

    fun save(v: View){
        toast("ISAVE")
        val path = File(Environment.getExternalStorageDirectory().getPath())
        val archivo = File(path,"MYSOUND1.3gp")
        val BA=ByteArray(archivo.length().toInt())
        val fos = FileInputStream(archivo)
        fos.read(BA)
        fos.close()


        val KE=KExercise()
        KE.ID=currentExerciseID
        KE.IDLesson=currentLessonID
        KE.TL1= eT1.text.toString()
        //KE.TL2= tVTranslated.text.toString()
        KE.TypeOfEx = 3


        KE.S1=BA
        if(BA.size<100){
            toast("sound archive less than 100 Bytes!!!!")
        }




        if( currentExerciseID==0L){
            val idNewExercise=DBH.insertExerciseToLesson(KE)
            val intentMessage = getIntent()
            intentMessage.putExtra("IDNewExercise",idNewExercise )
            intentMessage.putExtra("Name",KE.TL1)
            setResult(Activity.RESULT_OK,intentMessage)
            X.warn("I Saved  Created ex number: $idNewExercise")
            finish()

        }else{
            DBH.updateExercise(KE)
            val intentMessage = getIntent()
//            intentMessage.putExtra("IDNewExcercise",idNewExercise )
            intentMessage.putExtra("Name",KE.TL1)
            setResult(Activity.RESULT_OK,intentMessage)
            finish()
            X.warn("I Saved Updated")

        }


    }
    fun delete(v: View){
        toast("IDELETE")

    }
    fun test(v: View){
        toast("ITEST")
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
