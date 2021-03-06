package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise1.*
import android.view.View
import com.begemot.klib.*
import kotlinx.android.synthetic.main.savedeletetest.view.*
import org.jetbrains.anko.ctx
import java.io.*
import java.util.*


class Exercise1 : AppCompatActivity()   {

    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var  archivo: File
//    lateinit var  path: File
    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0
    val typeofex = 0

    lateinit var  DBH : DBHelp
  //  var  BA:ByteArray = byteArrayOf(0)
    lateinit var  cExercise: KExercise

    class RequestCode {
        companion object {
            val EDIT_ORIGINAL    :Int = 10
            val EDIT_TRANSLATED  :Int = 20
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(DEBUG)X.warn("1")
        setContentView(R.layout.activity_exercise1)
        savedeletetest.bTest.setOnClickListener   { test(savedeletetest.bTest) }
        savedeletetest.bDelete.setOnClickListener { delete(savedeletetest.bDelete) }
        savedeletetest.bSave.setOnClickListener   { save(savedeletetest.bSave) }

        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)

      //peta no init  if(DEBUG)X.warn("--1  ---- ${DBH.getDBNAme()}")

        DBH=DBHelp.getInstance(this)

        if(DEBUG)X.warn("--2  ---- ${DBH.getDBNAme()}")

        lKindOf.text=DBH.getKindEx(0)

        if(DEBUG)X.warn("--3  ---- ${DBH.getDBNAme()}")

        //val path = File(Environment.getExternalStorageDirectory().getPath())
        //archivo = File(path,"MYSOUND1.3gp")


        val dir = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(dir,  KT.soundNameFile())


        if(currentExerciseID==0L){

            savedeletetest.bDelete.isEnabled=false
            lStatus.text=resources.getString(R.string.item_new)
            cExercise= KExercise(0, currentLessonID, typeofex)
            FileWriter(archivo).close()
            if(DEBUG)X.warn("teoricament deixa l'arxiu de so a zero")

        }
        else{

            lStatus.text=resources.getString(R.string.item_update)
            cExercise=DBH.loadExercise(currentExerciseID)
            tVOriginal.setText(cExercise.TL1)
            tVTranslated.setText(cExercise.TL2)

            if(DEBUG)X.warn("LONGITUD SONIDO: ${cExercise.S1.size.toString()}")

           if(cExercise.S1.size>50) storeSoundtoFile()
           else emptySoundFile()
        }
    }


    fun storeSoundtoFile(){
        var soundData:ByteArray=cExercise.S1
//        val path = File(Environment.getExternalStorageDirectory().getPath())

        val dir = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(dir,  KT.soundNameFile())



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

    fun save(v:View){
       if(DEBUG)X.warn("I Will Save")

        //Salvem la nova grabacio
//        BA=ByteArray(archivo.length().toInt())
//       val fos = FileInputStream(archivo)
//        fos.read(BA)
//        fos.close()

        //val path = File(Environment.getExternalStorageDirectory().getPath())
        //val archivo = File(path,"MYSOUND1.3gp")

        val dir = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(dir,  KT.soundNameFile())
        val BA=ByteArray(archivo.length().toInt())
        val fos = FileInputStream(archivo)
        fos.read(BA)
        fos.close()
        cExercise.S1=BA





       /* val KE=KExercise()
        KE.ID=currentExerciseID
        KE.IDLesson=currentLessonID
        KE.TL1= tVOriginal.text.toString()
        KE.TL2= tVTranslated.text.toString()
        KE.TypeOfEx = 0
        KE.S1=BA
        if(BA.size<100){
            //toast("sound archive less than 100 Bytes!!!!")
        }*/
        cExercise.TL1=tVOriginal.text.toString()
        cExercise.TL2= tVTranslated.text.toString()


        if( currentExerciseID==0L){
            val idNewExercise=DBH.insertExerciseToLesson(cExercise)
            val intentMessage = getIntent()

            intentMessage.putExtra("IDNewExercise",idNewExercise )
            intentMessage.putExtra("Name",cExercise.TL1)
            intentMessage.putExtra("TypeOfEx",typeofex)
            setResult(Activity.RESULT_OK,intentMessage)
            if(DEBUG)X.warn("I Created ex : ${cExercise.toXString()}")
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

    fun delete(v:View){
        if(DEBUG)X.warn("I Will Delete")
        DBH.deleteExercise(currentExerciseID)
        val i=getIntent()
        i.putExtra("DELETE",true)
        setResult(Activity.RESULT_CANCELED,i)
        finish()
        //i.req
    }

    fun test(v:View){
        if(DEBUG)X.warn("I Will Test")
        val intento1 = Intent(this, Test1Activity::class.java)
        cExercise.TL1= tVOriginal.text.toString()
        cExercise.TL2= tVTranslated.text.toString()
        intento1.putExtra("TL1",cExercise.TL1)
        intento1.putExtra("TL2",cExercise.TL2)
        startActivity(intento1)

    }


    fun editTranslated(v:View){
        val intento1 = Intent(this, EdiTextActivity::class.java)
        //cExercise.TL1=idTexEx1.text.toString()
        //cExercise.TL2=idTexEx2.text.toString()
        intento1.putExtra("TL1", tVTranslated.text.toString())
        intento1.putExtra("TL2",resources.getString(R.string.ex1_translated))
        startActivityForResult(intento1,RequestCode.EDIT_TRANSLATED )

    }

    fun editOriginal(v:View){
        val intento1 = Intent(this, EdiTextActivity::class.java)
        //cExercise.TL1=idTexEx1.text.toString()
        //cExercise.TL2=idTexEx2.text.toString()
        intento1.putExtra("TL1", tVOriginal.text.toString())
        intento1.putExtra("TL2",resources.getString(R.string.ex1_original))
        startActivityForResult(intento1,RequestCode.EDIT_ORIGINAL )

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestCode.EDIT_ORIGINAL){
            if(resultCode== Activity.RESULT_OK){
                val nameE =data?.getStringExtra("TL1")
                tVOriginal.setText(nameE)
            }

        }
        if(requestCode==RequestCode.EDIT_TRANSLATED){
            if(resultCode== Activity.RESULT_OK){
                val nameE =data?.getStringExtra("TL1")
                tVTranslated.setText(nameE)
            }

        }
      /*  if(requestCode==RequestCode.SELECT_KINDOF_EXERCISE) {
            if(DEBUG)X.warn("REQUESTCODE=SELECT_KINDOF_EXERCISE")

            val KoEx = data?.getLongExtra("IDKind",0)
            if(DEBUG)X.warn (" KindOf Excercise:   $KoEx  ")
            if(KoEx!=null)
                when(KoEx){
                    1L ->{
                        if(DEBUG)X.warn("asw")
                        val intento1 = Intent(this, Exercise1::class.java)
                        intento1.putExtra("lessonID",currentLessonID)
                        intento1.putExtra("exerciseID",0L)
                        intento1.putExtra("kindOfEx",1)
                        startActivityForResult(intento1,RequestCode.ADD_EXERCISE )
                    }
                    else -> {
                        toast("NOT IMPLEMENTED")
                        if(DEBUG)X.warn("NOT IMPLEMENTED")
                    }
                }

        }
        if(requestCode==RequestCode.EDIT_EXERCISE){
            if(DEBUG)X.warn("REQUESTCODE=EDIT_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                selectedExercise = myListExercises.getItemAtPosition(currentPosition) as KExercise
                selectedExercise.TL1=nameE
                exercisesListAdapter.notifyDataSetChanged()

            }


        }
        if(requestCode==RequestCode.ADD_EXERCISE){
            if(DEBUG)X.warn("REQUEST CODE = ADD_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                val idE = data?.getLongExtra("IDNewExercise", 0L)
                exercisesList.add(KExercise(idE!!,currentLessonID,1,nameE))
                exercisesListAdapter.notifyDataSetChanged()
                if(DEBUG)X.warn("now ID = $idE")
                //myList.setItemChecked(myListAdapter.count-1,true)
                //myList.setSelection(myListAdapter.count-1)
                //if(DEBUG)X.warn("new lesson ID ${idL.toString()}   name: $nameL")

            }

        }
*/
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
