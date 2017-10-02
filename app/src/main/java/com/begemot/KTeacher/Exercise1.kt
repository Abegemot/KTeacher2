package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import kotlinx.android.synthetic.main.activity_exercise1.*
import android.view.View
import android.os.Environment
import com.begemot.klib.KHelp
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class Exercise1 : AppCompatActivity() , MediaPlayer.OnCompletionListener,MediaRecorder.OnErrorListener {

    private  val  X = KHelp(this.javaClass.simpleName)
    var  recorder: MediaRecorder? = null
    var  player :  MediaPlayer?   = null
    lateinit var  archivo:  File

    lateinit var path:File
    val recTime:Int  =  10000                        //MAX TIME REC in ms



    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0

    lateinit var  DBH : DBHelp
    var  BA:ByteArray = byteArrayOf(0)
    lateinit var  cExercise:KExercise

    class RequestCode {
        companion object {
            val EDIT_ORIGINAL    :Int = 10
            val EDIT_TRANSLATED  :Int = 20
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        X.warn("1")
        setContentView(R.layout.activity_exercise1)

        //BEGIN POS

       // idTexEx1.imeOptions = EditorInfo.IME_ACTION_DONE
       // idTexEx1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
       // idTexEx1.setLines(6)
       // idTexEx1.setHorizontallyScrolling(false)
//        idTexEx1.setPadding(0,0,0,0)

       // idTexEx2.imeOptions = EditorInfo.IME_ACTION_DONE
       // idTexEx2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
       // idTexEx2.setLines(6)
       // idTexEx2.setHorizontallyScrolling(false)


        //idTexEx1.setSingleLine()

        //END POS






        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)
        DBH=DBHelp.getInstance(this)
        X.warn("2")
        lKindOf.text=DBH.getKindEx(0)

        path = File(Environment.getExternalStorageDirectory().getPath())
        archivo = File(path,"MYSOUND1.3gp")

        X.warn("3")

        bRec.setOnClickListener  { grabar(bRec) }
        bPlay.setOnClickListener { reproducir(bPlay) }
        bStop.setOnClickListener { detener(bStop) }

        if(currentExerciseID==0L){
            lStatus.text=resources.getString(R.string.item_new)
            bRec.isEnabled=true
            bPlay.isEnabled=false
            bStop.isEnabled=false
        }
        else{
            bRec.isEnabled=true
            bPlay.isEnabled=false
            bStop.isEnabled=false

            lStatus.text=resources.getString(R.string.item_update)
            cExercise=DBH.loadExercise(currentExerciseID)
            tVOriginal.setText(cExercise.TL1)
            tVTranslated.setText(cExercise.TL2)

            X.warn("LONGITUD SONIDO: ${cExercise.S1.size.toString()}")
            if(cExercise.S1.size>1) bPlay.isEnabled=true

            var soundData:ByteArray=cExercise.S1
            val path = File(Environment.getExternalStorageDirectory().getPath())

            try {
                val fos = FileOutputStream(archivo)
                fos.write(soundData)
                fos.close()

            } catch (e: IOException) {
                X.warn("Exception creating temp file: ${e.toString()} ")
            }

            X.warn("writeappend  datasize ${soundData.size.toString()}")

        }
    }


    fun grabar(view:View) {
        try {
            //archivo = File(path,"MYSOUND1.3gp")
            //archivo.createNewFile()
            recorder = MediaRecorder()
            recorder?.setOnErrorListener(this)
            //recorder?.setOnInfoListener()


            recorder?.setOnInfoListener(object : MediaRecorder.OnInfoListener {
                override fun onInfo(mr: MediaRecorder, what: Int, extra: Int) {
                    if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        detener(bStop)
                        toast("Max Recording time Exceeded  ${recTime/1000} (s)")
                        X.warn("REC  TIME  EXCEDED   ${recTime/1000} (s)")
                        //recorder?.stop()

                    }
                }
            })
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder?.setMaxDuration(recTime)


            X.warn("archivo creado en: $path")
        } catch (e: IOException) {
            X.warn(" Exception no se ha podido crear archivo temp: ${e.message}")
            X.warn("${e.printStackTrace()}")
        }
        recorder?.setOutputFile(archivo.getAbsolutePath())
        try {
            recorder?.prepare()
        } catch (e: IOException) {
            X.warn(" Exception recorder prepare: ${e.message}")
            X.warn("${e.printStackTrace()}")
        }

        recorder?.start()
        lSound.setText("Grabando")
        bRec.isEnabled  = false
        bPlay.isEnabled = false
        bStop.isEnabled = true
    }

    fun detener(v: View) {

        recorder?.stop()
        recorder?.release()
        recorder=null
        //Salvem la nova grabacio
        BA=ByteArray(archivo.length().toInt())

        val fos = FileInputStream(archivo)
        fos.read(BA)
        fos.close()

        bRec.isEnabled = true
        bStop.isEnabled = false
        bPlay.isEnabled = true
        lSound.setText("Listo para reproducir")
    }

    fun reproducir(v: View) {
        try {
            player = MediaPlayer()
            player?.setOnCompletionListener(this)

            player?.setDataSource(archivo.getAbsolutePath())
            X.warn("length file in Bytes: ${archivo.length().toString()}")

        } catch (e: IOException) {
            X.warn(" no se ha podido detener: ${e.message}")
            X.warn("${e.printStackTrace()}")
        }

        try {
            player?.prepare()
        } catch (e: IOException) {
            X.warn(" exception en player prepare al reproducir: ${e.message}")
            X.warn("${e.printStackTrace()}")
        }
        player?.start()
        bRec.isEnabled = false
        bStop.isEnabled = false
        bPlay.isEnabled = false
        lSound.setText("Reproduciendo")
    }

    override fun onCompletion(mp: MediaPlayer) {
        mp.reset()
        mp.release()

        bRec.isEnabled = true
        bStop.isEnabled = false
        bPlay.isEnabled = true
        lSound.setText("Listo")
    }

     override fun onError(md:MediaRecorder,a:Int,b:Int){
         X.warn("On error Recorder a  :$a    b  :$b")
     }

     fun MediaRecorder.OnInfoListener(md:MediaRecorder,what:Int,extra:Int){
          X.warn("EXCEDED TIME REC")
     }

    fun save(v:View){
       X.warn("I Will Save")

        val KE=KExercise()
        KE.ID=currentExerciseID
        KE.IDLesson=currentLessonID
        KE.TL1= tVOriginal.text.toString()
        KE.TL2= tVTranslated.text.toString()
        KE.TypeOfEx = 1
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

    fun delete(v:View){
        X.warn("I Will Delete")
    }

    fun test(v:View){
        X.warn("I Will Test")
        val intento1 = Intent(this, Test1Activity::class.java)
        cExercise.TL1= tVOriginal.text.toString()
        cExercise.TL2= tVTranslated.text.toString()
        intento1.putExtra("TL1",cExercise.TL1)
        intento1.putExtra("TL2",cExercise.TL2)
        startActivity(intento1)

    }

    override fun onDestroy() {
        super.onDestroy()
        recorder?.release()
        player?.release()
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
            X.warn("REQUESTCODE=SELECT_KINDOF_EXERCISE")

            val KoEx = data?.getLongExtra("IDKind",0)
            X.warn (" KindOf Excercise:   $KoEx  ")
            if(KoEx!=null)
                when(KoEx){
                    1L ->{
                        X.warn("asw")
                        val intento1 = Intent(this, Exercise1::class.java)
                        intento1.putExtra("lessonID",currentLessonID)
                        intento1.putExtra("exerciseID",0L)
                        intento1.putExtra("kindOfEx",1)
                        startActivityForResult(intento1,RequestCode.ADD_EXERCISE )
                    }
                    else -> {
                        toast("NOT IMPLEMENTED")
                        X.warn("NOT IMPLEMENTED")
                    }
                }

        }
        if(requestCode==RequestCode.EDIT_EXERCISE){
            X.warn("REQUESTCODE=EDIT_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                selectedExercise = myListExercises.getItemAtPosition(currentPosition) as KExercise
                selectedExercise.TL1=nameE
                exercisesListAdapter.notifyDataSetChanged()

            }


        }
        if(requestCode==RequestCode.ADD_EXERCISE){
            X.warn("REQUEST CODE = ADD_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                val idE = data?.getLongExtra("IDNewExercise", 0L)
                exercisesList.add(KExercise(idE!!,currentLessonID,1,nameE))
                exercisesListAdapter.notifyDataSetChanged()
                X.warn("now ID = $idE")
                //myList.setItemChecked(myListAdapter.count-1,true)
                //myList.setSelection(myListAdapter.count-1)
                //X.warn("new lesson ID ${idL.toString()}   name: $nameL")

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
