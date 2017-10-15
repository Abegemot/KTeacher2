package com.begemot.KTeacher

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
//import android.support.v4.app.Fragment
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.ctx
import java.io.File
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlayerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment(),MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener {
    private  val  X = KHelp(this.javaClass.simpleName)
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mListener: OnFragmentInteractionListener? = null

    var  player :  MediaPlayer?   = null
    lateinit var  archivo: File
    lateinit var  dir: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        X.warn("")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        X.warn("")
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_player, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    /*override fun onAttach(context: Context?) {
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

    override  fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         X.warn("entra")

        //path = File(Environment.getExternalStorageDirectory().getPath())
        //archivo = File(path,"MYSOUND1.3gp")

        dir = ctx.getDir("KDir",Context.MODE_PRIVATE)
        archivo = File(dir, (KT.soundNameFile()))


        if(archivo.length()<2L) bPlay.isEnabled=false
        else bPlay.isEnabled=true
        bPlay.setOnClickListener({play(bPlay)})
        X.warn("surt")

    }


    fun play(v:View){  X.warn("REPRODUCIR")

        if(archivo.length()==0L){ X.warn("-----------EMMPPPTY SOUND FILE"); return }
        try {
            bPlay.isEnabled=false
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


    }

    override fun onPause() {
        super.onPause()
        X.warn("")
        player=null
       // onCompletion(player!!)
    }

    override fun onCompletion(mp: MediaPlayer) {
        X.warn("")
        mp.reset()
        mp.release()
        bPlay?.isEnabled=true
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        X.warn("Error Media Player  $p1 ,  $p2")
        return true

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
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

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
