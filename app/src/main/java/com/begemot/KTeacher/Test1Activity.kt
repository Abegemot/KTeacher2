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
        for(item in L){
            X.warn(item)
            val nB=Button(this)


            nB.setBackgroundColor(4)
            nB.setLayoutParams(LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT))
            nB.setOnClickListener{click(nB)}


            nB.setText(item)
            lLay.addView(nB)
        }
    }

    fun click(v: View){
        val nB:Button=v as Button
        toast(nB.text.toString())
    }

}
/*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
final View rowView = inflater.inflate(R.layout.field, null);
// Add the new row before the add field button.
parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);*/