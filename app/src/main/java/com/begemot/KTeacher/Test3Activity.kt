package com.begemot.KTeacher

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import com.begemot.KTeacher.KT.Companion.getAlertD
import com.begemot.KTeacher.KT.Companion.getD
import com.begemot.KTeacher.KT.Companion.showResults
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_test3.*
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*
import java.util.Collections.replaceAll




open class Test3Activity : AppCompatActivity(),PlayerFragment.OnFragmentInteractionListener {
    val  X = KHelp(this.javaClass.simpleName)
    lateinit var sOk:String
    lateinit var  DBH : DBHelp
    var ADiag: AlertDialog? = null
    open   val typeofex = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        X.warn("entra")
        super.onCreate(savedInstanceState)
        X.warn("1")
        setContentView(R.layout.activity_test3)
        X.warn("2")
        DBH=DBHelp.getInstance(this)
        //Todo Ui Ui Ui
        X.warn("3")

        lT.text=DBH.getKindEx(typeofex)
        X.warn("4")


        sOk =intent.getStringExtra("TL1")
        X.warn("5")

        eT1.imeOptions = EditorInfo.IME_ACTION_DONE
        eT1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        eT1.setHorizontallyScrolling(false)
        eT1.setLines(10)
        eT1.setSelection(eT1.text.toString().length)
        X.warn("6")

    }

    override fun onStart() {
       X.warn("")
        super.onStart()
        val fragment = getFragmentManager().findFragmentById(R.id.fPlayer)
        if(fragment!=null) {
            val f2: PlayerFragment = fragment as PlayerFragment
            f2.play(bPlay)
        }else X.warn("PUTU FRAMGMENT NULL")
        X.warn("surt")
    }




    override fun onFragmentInteraction(uri: Uri){
       X.warn("")
    }

    open fun testClick(v: View){
        var mistakes = false
        //eliminar puntuacio   eliminar white spaces p{Z}

        var sTest = eT1.text.toString().replace(Regex("[^\\p{L}\\p{Z}\\p{Nd}]"), "")
        val sOk2  = sOk.replace(Regex("[^\\p{L}\\p{Z}\\p{Nd}]"), "")

        val lsOk    :List<String> = sOk2.trim().split(" ")
        val lsTest  :List<String> = sTest.trim().split(" ")
        val szTest = lsTest.size

       X.warn("lsOk   after split ${lsOk.toString()}")
       X.warn("lsTest after split ${lsTest.toString()}")

        var I=0
        var nTrue  = 0
        var nFalse = 0
        var sResp=StringBuilder("")

        sResp.append("<I>${getString(R.string.ex1_original)}</I><br>$sOk<br><I>${getString(R.string.gived_answer)}</I><br>")

        for(item in lsOk){
            if(I>=szTest) break
            val tWord=lsTest[I]
            if(compareStrings(item.trim(),tWord.trim())){
               sResp.append(" $tWord")
               nTrue++
            }else{
               //X.warn("diferents  ????-------- OK='${item.trim()}' NEW='${tWord.trim()}'")
               sResp.append(" <u>$tWord</u>")
               mistakes=true
               nFalse++
            }
            I++
        }
        if(nFalse+nTrue<szTest) mistakes = true
        ADiag=getAlertD(Html.fromHtml(sResp.toString()),this,mistakes)
        X.warn("sResp=$sResp")

    }

    fun compareStrings(sOk:String,sTest:String):Boolean{
        if(sOk.equals(sTest,true)) return true
        else return false
   }

    override fun onPause(){
        super.onPause()
        X.warn("On PAUSE")
        if(ADiag!=null){
            val d=ADiag
            if(d is AlertDialog){
                d.dismiss()
            }
            ADiag = null
        }

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
        // X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("${KT.getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }

}
