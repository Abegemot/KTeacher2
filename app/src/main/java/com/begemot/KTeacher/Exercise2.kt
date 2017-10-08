package com.begemot.KTeacher

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_exercise3.*

/**
 * Created by dad on 08/10/2017.
 */


class Exercise2: Exercise3(){
    override val typeofex=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lKindOf.text=DBH.getKindEx(typeofex)
        tVoriginal.text=getText(R.string.ex1_translated)
    }

    override fun test(v: View){
        val intento1 = Intent(this, Test2Activity::class.java)
        cExercise.TL1= eT1.text.toString()
        intento1.putExtra("TL1",cExercise.TL1)
        startActivity(intento1)
    }


}