package com.begemot.KTeacher

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.begemot.KTeacher.KLayout.Companion.kTextView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

class SWWSEditor:KBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}




class SWSEView:AnkoComponent<SWWSEditor>{

    override fun createView(ui: AnkoContext<SWWSEditor>): View = with(ui) {

        verticalLayout{
            kTextView("Hola papanatas")

        }

    }


}