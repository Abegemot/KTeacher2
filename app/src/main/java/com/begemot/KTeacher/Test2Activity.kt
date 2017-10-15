package com.begemot.KTeacher

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.View
import com.begemot.KTeacher.KT.Companion.gimeADialog
import kotlinx.android.synthetic.main.activity_test3.*
import org.jetbrains.anko.AlertBuilder
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

/**
 * Created by dad on 08/10/2017.
 */


class Test2Activity:Test3Activity(){
    override val typeofex=1

     //var ADiag: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lT.text=DBH.getKindEx(typeofex)
    }

    override fun testClick(v: View){
        var mistakes = false
        //eliminar puntuacio   eliminar white spaces p{Z}


        //user text
        var sTest = eT1.text.toString().replace(Regex("[^\\p{L}\\p{Z}\\p{Nd}]"), "")
       // val sOk2  = sOk.replace(Regex("[^\\p{L}\\p{Z}\\p{Nd}]"), "")

        //llista frases OK delimitades per @

        X.warn("sOK   '$sOk'")
        val lsOk    :List<String> = sOk.split("@")
        X.warn("lsOK   $lsOk")

        for(item in lsOk){
            mistakes=compareSentences(item,sTest)
            if(!mistakes) break
        }

        var sResp=StringBuilder("")
        sResp.append("<I>${getString(R.string.posible_answers)}</I><br>")

        for(item in lsOk){
            sResp.append("-"+item+"<br>")
        }

        sResp.append("<br><I>${getString(R.string.gived_answer)}</I><br>$sTest")


        ADiag = KT.getAlertD(Html.fromHtml(sResp.toString()), this, mistakes)

    }



fun compareSentences(sOk: String,sUser:String):Boolean{
    var I=0
    var nTrue  = 0
    var nFalse = 0
    var mistakes = false
    val lsOk    :List<String> = sOk.split(" ")
    val lsTest  :List<String> = sUser.trim().split(" ")
    val szTest = lsTest.size
    X.warn("compare sentences: sOk    :$sOk")
    X.warn("compare sentences: sUser  :$sUser")

    //var sResp=StringBuilder("")
    for(item in lsOk){
        if(I>=szTest) break
        val tWord=lsTest[I]
        if(compareWords(item.trim(),tWord.trim())){
            //sResp.append(" $tWord")
            nTrue++
        }else{
            //X.warn("diferents  ????-------- OK='${item.trim()}' NEW='${tWord.trim()}'")
      //      sResp.append(" <u>$tWord<u>")
            mistakes=true
            nFalse++
        }
        I++
    }
    if(nFalse+nTrue<szTest) mistakes = true
    return mistakes

}



fun compareWords(sOk:String,sTest:String):Boolean{
    if(sOk.equals(sTest,true)) return true
    else return false
}


}