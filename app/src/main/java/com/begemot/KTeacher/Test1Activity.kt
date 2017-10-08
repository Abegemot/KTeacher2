package com.begemot.KTeacher

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test1.*
import com.begemot.klib.KHelp
import android.widget.LinearLayout.LayoutParams
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.toast
import java.util.*


class Test1Activity : AppCompatActivity(),PlayerFragment.OnFragmentInteractionListener {
    private  val  X = KHelp(this.javaClass.simpleName)
    lateinit var pairLArroba:Pair<List<String>,List<String> >
    lateinit var lStringOk:List<String>
  //  lateinit var lStringOk2:List<String>

    val falseBkGr = R.drawable.abc_btn_borderless_material
    val trueBkGr  = R.drawable.abc_btn_default_mtrl_shape

    //    abc_btn_default_mtrl_shape



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
        fPlayer.tV1.text = intent.getStringExtra("TL1")
        buildButtons(intent.getStringExtra("TL2"))

    }

    override fun onStart() {
        super.onStart()
        //Todo asegurar que hi a file
        var f:PlayerFragment=fPlayer as PlayerFragment
        f.play(bPlay)


    }


    override fun onFragmentInteraction(uri: Uri){
        X.warn("")
    }

    fun buildButtons(s:String){
        X.warn("OriginalString:$s")
        var L=s.split(" ")


        pairLArroba=L.partition { it.startsWith("@") }

      //  pairLArroba.first.size
      //  pairLArroba.second.size
        lStringOk=pairLArroba.second
        //lStringOk2=lStringOk


        X.warn("con @ ${pairLArroba.first.size}   sin @  ${pairLArroba.second.size}")

        X.warn("${L.size}")
        var i=0

        val L2= shuffle(L as MutableList<String>)

        for(item in L2){

            if(item.length>0) {
       //         X.warn("world : $item")
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
        X.warn("")

        val oldB: Button = v as Button
        oldB.setOnClickListener { clickL2(oldB) }
        lLay1.removeView(oldB)
                //abc_btn_check_material

        if( lStringOk.size>=  lLay2.childCount+1) {


            val s_at = lStringOk[lLay2.childCount]
            //Todo Shall see if every allready placed button feets ok (they might have been moved and no longer ok)

            if (!s_at.equals(oldB.text.toString(), true)) {
                X.warn("s_at : $s_at   button : ${oldB.text}     childCount ${lLay2.childCount} ")
                //oldB.setBackgroundResource(R.drawable.abc_list_selector_background_transition_holo_dark)
                oldB.setBackgroundResource(falseBkGr)
            }
        } else  oldB.setBackgroundResource(falseBkGr)

        lLay2.addView(oldB)


 //R.drawable.abc_btn_check_material


        //lLay2.addView(oldB)
        X.warn("pasa 4")

  //      lLay2.invalidate()
  //      lLay1.invalidate()
    }

    fun clickL2(v: View) {

        v.setOnClickListener{clickL1(v)}
        lLay2.removeView(v)

        v.setBackgroundResource(trueBkGr)
        lLay1.addView(v)

        val n    = lLay2.childCount
        val nsOk = lStringOk.size
        var i=0
        for(i in 0..n-1){
            var b=lLay2.getChildAt(i) as Button
            var s=lStringOk[i]

            if(s.equals(b.text.toString(),true)){
                b.setBackgroundResource(trueBkGr )

            }else{
                b.setBackgroundResource(falseBkGr)
            }
         for(i in nsOk..n-1){
             X.warn("i=$i")
           //  var b=lLay2.getChildAt(i) as Button
           //  b.setBackgroundResource(R.drawable.abc_btn_check_material)
         }


        }




//        lLay2.invalidate()
//        lLay1.invalidate()

    }

    fun clickTestEx(v:View){
        //toast("I Will Test")
        var n_aciertos = 0
        var n_errors  = 0
        val n=lLay2.childCount
       for(i in 0..n-1){
            var b=lLay2.getChildAt(i) as Button
            var c=b.text.toString()
            var original=lStringOk[i]
           //X.warn("Original: $original  ")

            if(c.equals(original)){
               n_aciertos++
            } else n_errors++
        }

        toast("Number Of guesses: $n_aciertos  Number of mistakes: $n_errors  total words: ${lStringOk.size}")
        //showOk()

    }

    fun showOk(){
        val n=lLay2.childCount
        for(i in 0..n-1){
            var b=lLay2.getChildAt(i) as Button
            var c=b.text.toString()
            var original=lStringOk[i]

            if(c.equals(original)){
                b.setBackgroundColor(9999999)
            }
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
        //X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("${KT.getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
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