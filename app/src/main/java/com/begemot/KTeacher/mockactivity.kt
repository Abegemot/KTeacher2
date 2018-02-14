package com.begemot.KTeacher

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.begemot.klib.KHelp
import com.begemot.klib.KLesson
import org.jetbrains.anko.*

/**
 * Created by dad on 06/02/2018.
 */
class MockActivity : KBaseActivity() {
    private val X = KHelp(this.javaClass.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title=resources.getText(R.string.lesson)
        MockView().setContentView(this)
    }

    fun getDataProvider():List<String>{
        return listOf("pepe","juan","ramon" )
        //return DBHelp.getInstance(this).loadAll<KLesson>()
    }

}




