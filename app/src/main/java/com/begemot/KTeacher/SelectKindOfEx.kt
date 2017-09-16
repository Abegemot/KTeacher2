package com.begemot.KTeacher

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_select_exercise.*
import kotlinx.android.synthetic.main.activity_select_kind_of_ex.*
import kotlin.reflect.KClass

class SelectKindOfEx : AppCompatActivity() {
    private val X = KHelp(this.javaClass.simpleName)

    lateinit var KOfexercisesListAdapter: ArrayAdapter<KKindOfExercice>
    lateinit var DBH : DBHelp
    var KOfexercisesList = ArrayList<KKindOfExercice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_kind_of_ex)
        KOfexercisesListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,KOfexercisesList)


        myListKindOfExercises.adapter=KOfexercisesListAdapter

        myListKindOfExercises.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
            X.warn("onItemClickListener position = $position    id= $id")

            val intentMessage = getIntent()
            val KOF:KKindOfExercice = myListKindOfExercises.getItemAtPosition(position) as KKindOfExercice
            intentMessage.putExtra("IDKind",KOF.ID )
            setResult(Activity.RESULT_OK,intentMessage)
            finish()



        } }


        DBH=DBHelp.getInstance(this)
        loadKindOfExercises()



    }
    fun loadKindOfExercises(){
        X.warn ("")
        val a:List<KKindOfExercice> = DBH.loadKindOfExercises()
        for(item in a)   KOfexercisesList.add(item)
        KOfexercisesListAdapter.notifyDataSetChanged()
    }




}
