package com.begemot.KTeacher

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
//import android.support.v4.app.Fragment
import android.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.begemot.klib.DEBUG
import com.begemot.klib.KHelp
import com.begemot.klib.KT
import org.jetbrains.anko.*

import kotlinx.android.synthetic.main.fragment_blank.*
import java.io.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment(),MediaPlayer.OnCompletionListener,MediaRecorder.OnErrorListener {
    private  val  X = KHelp(this.javaClass.simpleName)
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mListener: OnFragmentInteractionListener? = null

    var  recorder: MediaRecorder? = null
    var  player :  MediaPlayer?   = null


    lateinit var  archivo : File
    lateinit var  dir : File

   // lateinit var path: File
    val recTime:Int  =  10000                        //MAX TIME REC in ms



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }



    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_blank, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }



   /* override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the subjectActivity and potentially other fragments contained in that
     * subjectActivity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        private val DISABLE_ALL  : Int = 0
        private val REC  : Int= 1
        private val PLAY : Int= 2
        private val STOP : Int= 4


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): BlankFragment {
            val fragment = BlankFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    //setEnable(REC and PLAY and STOP)
    fun setEnable(i:Int){
        if(i and 1 != 0) bRec?.isEnabled=true else bRec?.isEnabled=false
        if(i and 2 != 0) bPlay?.isEnabled=true else bPlay?.isEnabled=false
        if(i and 4 != 0) bStop?.isEnabled=true else bStop?.isEnabled=false
        if(i and 8 != 0) {}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(DEBUG)X.warn("1")
        bRec.setOnClickListener  { grabar(bRec) }
        bPlay.setOnClickListener { reproducir(bPlay) }
        bStop.setOnClickListener { detener(bStop) }
        if(DEBUG)X.warn("2")
       // path = File(Environment.getExternalStorageDirectory().getPath())
       // archivo = File(path,"MYSOUND1.3gp")
        dir = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(dir,  KT.soundNameFile())
        if(archivo.length()==0L) setEnable(REC)
        else setEnable(REC or PLAY)
        lSound.setText("${resources.getString(R.string.ready)}")
        if(DEBUG)X.warn("3")


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(DEBUG)X.warn("")
    }


    fun grabar(v:View){
        if(DEBUG)X.warn("GRABAR")
        try {
            recorder = MediaRecorder()
            recorder?.setOnErrorListener(this)
            recorder?.setOnInfoListener(object : MediaRecorder.OnInfoListener {
            override fun onInfo(mr: MediaRecorder, what: Int, extra: Int) {
                    if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        detener(bStop)
                        toast("${resources.getString(R.string.max_time)}  ${recTime/1000} (s)")
                        if(DEBUG)X.warn("REC  TIME  EXCEDED   ${recTime/1000} (s)")
                        //recorder?.stop()

                    }
                }
            })
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            //recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            //recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)

            recorder?.setMaxDuration(recTime)


           // if(DEBUG)X.warn("archivo creado en: $path")
        } catch (e: IOException) {
            if(DEBUG)X.warn(" Exception no se ha podido crear archivo temp: ${e.message}")
            if(DEBUG)X.warn("${e.printStackTrace()}")
        }
        recorder?.setOutputFile(archivo.getAbsolutePath())


        try {
            recorder?.prepare()
        } catch (e: IOException) {
            if(DEBUG)X.warn(" Exception recorder prepare: ${e.message}")
            if(DEBUG)X.warn("${e.printStackTrace()}")
        }

        recorder?.start()
        lSound.setText("${resources.getString(R.string.rec)}")
        setEnable(STOP)


    }

    fun reproducir(v:View){
        if(DEBUG)X.warn("REPRODUCIR")
        try {
            player = MediaPlayer()
            player?.setOnCompletionListener(this)

            player?.setDataSource(archivo.getAbsolutePath())
            if(DEBUG)X.warn("length file in Bytes: ${archivo.length().toString()}")

        } catch (e: IOException) {
            if(DEBUG)X.warn(" no se ha podido detener: ${e.message}")
            if(DEBUG)X.warn("${e.printStackTrace()}")
        }

        try {
            player?.prepare()
        } catch (e: IOException) {
            if(DEBUG)X.warn(" exception en player prepare al reproducir: ${e.message}")
            if(DEBUG)X.warn("${e.printStackTrace()}")
        }
        player?.start()
        setEnable(DISABLE_ALL)

        lSound.setText("${resources.getString(R.string.playing)}")

   }


    fun detener(v:View){
        recorder?.stop()
        recorder?.release()
        recorder=null
        //Salvem la nova grabacio
//        BA=ByteArray(archivo.length().toInt())
//        val fos = FileInputStream(archivo)
//        fos.read(BA)
//        fos.close()
        setEnable(REC or PLAY)
        lSound.setText("${resources.getString(R.string.ready)}")
    }

    override fun onCompletion(mp: MediaPlayer) {
        mp.reset()
        mp.release()
        setEnable(REC or PLAY)
        lSound?.setText("${resources.getString(R.string.ready)}")
    }

    override fun onError(md:MediaRecorder,a:Int,b:Int){
        if(DEBUG)X.warn("On error Recorder a  :$a    b  :$b")
    }


}// Required empty public constructor
