package com.begemot.KTeacher

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test1.*
import com.begemot.klib.KHelp
import android.widget.LinearLayout.LayoutParams
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.singleLine
import org.jetbrains.anko.toast
import java.util.*


class Test1Activity : AppCompatActivity() {
    private  val  X = KHelp(this.javaClass.simpleName)

    var ADiag: AlertDialog? = null
    lateinit var pairLArroba:Pair<List<String>,List<String> >
    lateinit var lStringOk : List<String>
    lateinit var sOrig : String
  //  lateinit var lStringOk2:List<String>

    val falseBkGr = R.drawable.abc_btn_borderless_material
    val trueBkGr  = R.drawable.abc_btn_default_mtrl_shape

    //    abc_btn_default_mtrl_shape



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
      //  fPlayer.tV1.text = intent.getStringExtra("TL1")
        buildButtons(intent.getStringExtra("TL2"))

    }

    override fun onStart() {//
        if(DEBUG)X.warn("")
        super.onStart()
        val fragment = getFragmentManager().findFragmentById(R.id.fPlayer)
        if(fragment!=null) {
            val f2: PlayerFragment = fragment as PlayerFragment
            f2.tV1.text = intent.getStringExtra("TL1")
            f2.play(bPlay)
        }else if(DEBUG)X.warn("PUTU FRAMGMENT NULL")

    }


    //override fun onFragmentInteraction(uri: Uri){
    //    if(DEBUG)X.warn("")
    //}


    fun buildButtons(s:String){
        sOrig=s
        if(DEBUG)X.warn("OriginalString:$sOrig")
        var sOrigOk=sOrig.trim()   //eliminem blank dels extrems
        sOrigOk=sOrigOk.replace(Regex("[^\\p{L} \\@ \\p{Z}\\p{Nd}]")," ") //eliminem ?!".
        sOrigOk=sOrigOk.replace(Regex("[ \\t]+"),"-@@-")   // eliminem blanks en els estrings


        var L=sOrigOk.split("-@@-")
        if(DEBUG)X.warn("L:${L.toString()}")
        pairLArroba=L.partition { it.startsWith("@") }

        lStringOk=pairLArroba.second
        if(DEBUG)X.warn("lStringOk:${lStringOk.toString()}  size=${lStringOk.size}")
        if(DEBUG)X.warn("con @ ${pairLArroba.first.size}   sin @  ${pairLArroba.second.size}")

        if(DEBUG)X.warn("${L.size}")
        var i=0

        val L2= shuffle(L as MutableList<String>)

        for(item in L2){

            if(item.length>0) {
       //         if(DEBUG)X.warn("world : $item")
                val s=item.removePrefix("@")
                val nB=createButton()
                nB.id=i++
                nB.setOnClickListener { clickL1(nB) }
                nB.setText(s)
                lLay1.addView(nB)
            }
        }
    }

    fun createButton():Button {
        val nB = Button(this)
        nB.setLayoutParams(LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        nB.minWidth=10
        nB.minimumWidth=10
        nB.minHeight=5
        nB.minimumHeight=5
        nB.setBackgroundResource(trueBkGr)
        return nB
    }


    fun clickL1(v: View) {
        if(DEBUG)X.warn("")

        val oldB: Button = v as Button
        oldB.setOnClickListener { clickL2(oldB) }
        lLay1.removeView(oldB)
                //abc_btn_check_material

        if( lStringOk.size>=  lLay2.childCount+1) {


            val s_at = lStringOk[lLay2.childCount]
            //Todo Shall see if every allready placed button feets ok (they might have been moved and no longer ok)

            if (!s_at.equals(oldB.text.toString(), true)) {
                if(DEBUG)X.warn("s_at : $s_at   button : ${oldB.text}     childCount ${lLay2.childCount} ")
                //oldB.setBackgroundResource(R.drawable.abc_list_selector_background_transition_holo_dark)
                oldB.setBackgroundResource(falseBkGr)
                //oldB.id=0
                oldB.tag=false
            }
        } else{  oldB.setBackgroundResource(falseBkGr)
            //oldB.id=1
            oldB.tag=true
        }

        lLay2.addView(oldB)

        if(!aretheyMistakes()) {
            displayAlert(false)
        }
    }

    fun clickL2(v: View) {
        //toast("PUM")
        v.setOnClickListener{clickL1(v)}
        lLay2.removeView(v)

        v.setBackgroundResource(trueBkGr)
        v.tag=true
        lLay1.addView(v)

        val n    = lLay2.childCount
        val nsOk = lStringOk.size
        var i=0
        for(i in 0..n-1) {
            var b = lLay2.getChildAt(i) as Button
            var s = lStringOk[i]

            if (s.equals(b.text.toString(), true)) {
                b.setBackgroundResource(trueBkGr)
               // b.id = 1
                b.tag=true

            } else {
                b.setBackgroundResource(falseBkGr)
                //b.id = 0
                b.tag=false
            }


        }

    }

    fun aretheyMistakes():Boolean{
        val n    = lLay2.childCount
        val nsOk = lStringOk.size
        if(n==nsOk){
            if(DEBUG)X.warn("n=$n  nsOk=$nsOk")
            if(n==0) return true
            for(i in 0..n-1){
                var b=lLay2.getChildAt(i) as Button
                // if(b.id==0){
                if(b.tag==false){
                    if(DEBUG)X.warn("troba un id false amb text ${b.text}  i numero de boto $i")
                    return true
                }
            }
            if(DEBUG)X.warn("en principi OK")
            return false
        }
        return true


    }



    fun displayAlert(mistakes:Boolean){
        if(DEBUG)X.warn("mistakes=$mistakes")
        val sOrigOk=sOrig.replace(Regex("@\\p{L}+"),"")  //elimina nomes tot el que comença per @
        if(mistakes) ADiag= KT.getAlertD(Html.fromHtml("<br> $sOrigOk"), this, true)
        else ADiag= KT.getAlertD(Html.fromHtml("<br> $sOrigOk"), this, false)
    }

    fun clickTestEx(v:View){
        if(DEBUG)X.warn("")
        displayAlert(aretheyMistakes())
    }


    override fun onPause(){
        super.onPause()
        if(DEBUG)X.warn("On PAUSE")
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



/*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
final View rowView = inflater.inflate(R.layout.field, null);
// Add the new row before the add field button.
parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);*/

fun <T:Comparable<T>>shuffle(items:MutableList<T>):List<T>{
    val rg : Random = Random()
    for (i in 0..items.size - 1) {
        val randomPosition = rg.nextInt(items.size)
        val tmp : T = items[i]
        items[i] = items[randomPosition]
        items[randomPosition] = tmp
    }
    return items
}





/*fun main(args: Array<String>) {
    println("\nOrdered list:")
    val ordered = listOf<String>("Adam", "Clark", "John", "Tim", "Zack")
    println(ordered)
    println("\nshuffled list:")
    val shuffled = shuffle(ordered as MutableList<String>)
    print(shuffled)
}
 */