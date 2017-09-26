package com.begemot.KTeacher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_test1.*
import com.begemot.klib.KHelp
import android.widget.LinearLayout.LayoutParams
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.toast


class Test1Activity : AppCompatActivity() {
    private  val  X = KHelp(this.javaClass.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
        tV1.text = intent.getStringExtra("TL1")
        buildButtons(intent.getStringExtra("TL2"))

    }

    fun buildButtons(s:String){
        var L=s.split(" ")
        X.warn("${L.size}")
        var i=0
        for(item in L){

            if(item.length>0) {
                X.warn("world : $item")
                //val nB = Button(this, null, R.style.KButton)
                val nB = Button(this)
                nB.id=i++
                nB.setBackgroundColor(8440772)
                nB.setLayoutParams(LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
                nB.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
                nB.setOnClickListener { clickL1(nB) }
                nB.setText(item)
                lLay1.addView(nB)
            }
        }
    }

    fun clickL1(v: View) {
        val oldB: Button = v as Button
        oldB.visibility=View.INVISIBLE
        val nB=Button(this)
        nB.id=oldB.id
        nB.setLayoutParams(LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        nB.setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
        nB.setOnClickListener { clickL2(nB) }
        nB.setText(oldB.text.toString())
        lLay2.addView(nB)
    }

    fun clickL2(v: View) {
        val n=lLay1.childCount
        X.warn("nItems in lLay1 $n")

        val btoRemove=v as Button
        for(i in 0..n-1){

            var b=lLay1.getChildAt(i) as Button
            if(btoRemove.id==b.id){
                X.warn("poner a visible ${b.id}")
                b.visibility=View.VISIBLE
                break
            }

        }
        lLay2.removeView(v)
    }


}
/*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
final View rowView = inflater.inflate(R.layout.field, null);
// Add the new row before the add field button.
parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);*/
/*
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





fun main(args: Array<String>) {
    println("\nOrdered list:")
    val ordered = listOf<String>("Adam", "Clark", "John", "Tim", "Zack")
    println(ordered)
    println("\nshuffled list:")
    val shuffled = shuffle(ordered as MutableList<String>)
    print(shuffled)
}
 */