package com.begemot.KTeacher

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_exercise3.*
import kotlinx.android.synthetic.main.activity_test3.*
import org.jetbrains.anko.toast

/**
 * Created by dad on 08/10/2017.
 */


class Test2Activity:Test3Activity(){
    override val typeofex=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lT.text=DBH.getKindEx(typeofex)
    }

    override fun testClick(v: View){
        toast("I WILL TEST SOON")
    }

}