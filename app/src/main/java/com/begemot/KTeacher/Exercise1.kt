package com.begemot.KTeacher

import android.app.Activity
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


class Exercise1 : AppCompatActivity() , MediaPlayer.OnCompletionListener,MediaRecorder.OnErrorListener {

    private  val  X = KHelp(this.javaClass.simpleName)
    var  recorder: MediaRecorder? = null
    var  player :  MediaPlayer?   = null
    lateinit var  archivo:  File

    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0

    lateinit var  DBH : DBHelp
    var  BA:ByteArray = byteArrayOf(0)
    lateinit var  cExercise:KExercise
    lateinit var path:File
    val recTime:Int  =  10000                        //MAX TIME REC in ms



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)
        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)
        DBH=DBHelp.getInstance(this)


        path = File(Environment.getExternalStorageDirectory().getPath())
        archivo = File(path,"MYSOUND1.3gp")



        if(currentExerciseID==0L){
            idTxW.text="CREATE"
            bRec.isEnabled=true
            bPlay.isEnabled=false
            bStop.isEnabled=false
        }
        else{
            bRec.isEnabled=true
            bPlay.isEnabled=false
            bStop.isEnabled=false

            idTxW.text="UPDATE"
            cExercise=DBH.loadExercise(currentExerciseID)
            idTexEx1.setText(cExercise.TL1)

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
        tv1.setText("Grabando")
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
        tv1.setText("Listo para reproducir")
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
        tv1.setText("Reproduciendo")
    }

    override fun onCompletion(mp: MediaPlayer) {
        mp.reset()
        mp.release()

        bRec.isEnabled = true
        bStop.isEnabled = false
        bPlay.isEnabled = true
        tv1.setText("Listo")
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
        KE.TL1=idTexEx1.text.toString()
        KE.TL2="ja veurem"
        KE.TypeOfEx = 1
        KE.S1=BA



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
    }

    override fun onDestroy() {
        super.onDestroy()
        recorder?.release()
        player?.release()
    }


}
