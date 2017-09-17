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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Exercise1 : AppCompatActivity() , MediaPlayer.OnCompletionListener {

    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var  recorder: MediaRecorder
    lateinit var  player :  MediaPlayer
    lateinit var  archivo:  File
    private  var  currentLessonID : Long = 0
    private  var  currentExerciseID : Long = 0
    lateinit var  DBH : DBHelp
    lateinit var  BA:ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)
        currentLessonID = intent.getLongExtra("lessonID",0)
        currentExerciseID = intent.getLongExtra("exerciseID",0)
        DBH=DBHelp.getInstance(this)

    }


    fun grabar(view:View) {
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val path = File(Environment.getExternalStorageDirectory().getPath())
        try {
            archivo = File.createTempFile("temporal", ".3gp", path)
            X.warn("archivo creado en: $path")
        } catch (e: IOException) {
           X.warn(" no se ha podido grabar: ${e.message}")
        }

        recorder.setOutputFile(archivo.getAbsolutePath())
        try {
            recorder.prepare()
        } catch (e: IOException) {
        }

        recorder.start()
        tv1.setText("Grabando")
        b1.isEnabled = false
        b2.isEnabled = true
    }

    fun detener(v: View) {

        recorder.stop()
        recorder.release()

        player = MediaPlayer()
        player.setOnCompletionListener(this)
        try {
            player.setDataSource(archivo.getAbsolutePath())
            X.warn("length file in Bytes: ${archivo.length().toString()}")
            BA=ByteArray(archivo.length().toInt())
            val fos = FileOutputStream(archivo)
            fos.write(BA)
            fos.close()

        } catch (e: IOException) {
            X.warn(" no se ha podido detener: ${e.message}")
        }

        try {
            player.prepare()
        } catch (e: IOException) {
        }

        b1.isEnabled = true
        b2.isEnabled = false  
        b3.isEnabled = true
        tv1.setText("Listo para reproducir")
    }

    fun reproducir(v: View) {
        player.start()
        b1.isEnabled = false
        b2.isEnabled = false
        b3.isEnabled = false
        tv1.setText("Reproduciendo")
    }

    override fun onCompletion(mp: MediaPlayer) {
        b1.isEnabled = true
        b2.isEnabled = true
        b3.isEnabled = true
        tv1.setText("Listo")
    }

    fun save(v:View){
       X.warn("I Will Save")
       if( currentExerciseID==0L){
            val KE=KExercise()
            KE.IDLesson=currentLessonID
            KE.TL1=idTexEx1.text.toString()
            KE.TL2="ja veurem"
            KE.TypeOfEx = 1
            KE.S1=BA

            val idNewExercise=DBH.insertExerciseToLesson(KE)


            val intentMessage = getIntent()
            intentMessage.putExtra("IDNewExcercise",idNewExercise )
            setResult(Activity.RESULT_OK,intentMessage)
            finish()
           X.warn("I Saved")



       }

    }

    fun delete(v:View){
        X.warn("I Will Delete")
    }

    fun test(v:View){
        X.warn("I Will Test")
    }




}
