package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import java.io.*
import java.util.*

open class Exercise3 : AppCompatActivity() {

    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var  DBH : DBHelp
    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0
    lateinit var  cExercise:KExercise
    lateinit var  archivo: File
    open   val typeofex = 2
    //lateinit var  path: File


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
        eT1.setLines(5)
        eT1.setSelection(eT1.text.toString().length)

        DBH=DBHelp.getInstance(this)

        lKindOf.text=DBH.getKindEx(typeofex)

        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)

        val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(path,KT.soundNameFile())


        if(currentExerciseID==0L){
            lStatus.text=resources.getString(R.string.item_new)
            cExercise=KExercise(0,currentLessonID,typeofex)
            emptySoundFile()
            if(DEBUG)X.warn("teoricament deixa l'arxiu de so a zero")
        }
        else {
            lStatus.text=resources.getString(R.string.item_update)
            cExercise=DBH.loadExercise(currentExerciseID)
            eT1.setText(cExercise.TL1)
            //Todo ad sound to file
            if(cExercise.S1.size>50) storeSoundtoFile()
            else emptySoundFile()
        }



    }

    fun storeSoundtoFile(){
        var soundData:ByteArray=cExercise.S1
        //val path = File(Environment.getExternalStorageDirectory().getPath())
        val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
        try {
            val fos = FileOutputStream(archivo)
            fos.write(soundData)
            fos.close()

        } catch (e: IOException) {
            if(DEBUG)X.warn("Exception creating temp file: ${e.toString()} ")
        }
        if(DEBUG)X.warn("writeappend  datasize ${soundData.size.toString()}")

    }
    fun emptySoundFile(){
        FileWriter(archivo).close()
        if(DEBUG)X.warn("teoricament deixa l'arxiu a zero")

    }


    fun save(v: View){
        //toast("ISAVE")
        //val path = File(Environment.getExternalStorageDirectory().getPath())
        val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
        val archivo = File(path,KT.soundNameFile())
        val BA=ByteArray(archivo.length().toInt())
        val fos = FileInputStream(archivo)
        fos.read(BA)
        fos.close()


        cExercise.TL1= eT1.text.toString()
        //KE.TL2= tVTranslated.text.toString()
        //KE.TypeOfEx = typeofex


        cExercise.S1=BA
        if(BA.size<100){
            //toast("sound archive less than 100 Bytes!!!!")
        }




        if( currentExerciseID==0L){
            val idNewExercise=DBH.insertExerciseToLesson(cExercise)
            val intentMessage = getIntent()
            intentMessage.putExtra("IDNewExercise",idNewExercise )
            intentMessage.putExtra("Name",cExercise.TL1)
            intentMessage.putExtra("TypeOfEx",typeofex)
            setResult(Activity.RESULT_OK,intentMessage)
            if(DEBUG)X.warn("I Saved  Created ex number: $idNewExercise")
            finish()

        }else{
            DBH.updateExercise(cExercise)
            val intentMessage = getIntent()
//            intentMessage.putExtra("IDNewExcercise",idNewExercise )
            intentMessage.putExtra("Name",cExercise.TL1)
            setResult(Activity.RESULT_OK,intentMessage)
            finish()
            if(DEBUG)X.warn("I Saved Updated")

        }


    }
    fun delete(v: View){
        //toast("IDELETE")
        DBH.deleteExercise(currentExerciseID)
        val i=getIntent()
        i.putExtra("DELETE",true)
        setResult(Activity.RESULT_CANCELED,i)
        finish()

    }

    open  fun test(v: View){
        val intento1 = Intent(this, Test3Activity::class.java)
        cExercise.TL1= eT1.text.toString()
        intento1.putExtra("TL1",cExercise.TL1)
        startActivity(intento1)
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
