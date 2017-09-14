package com.begemot.KTeacher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_select_exercise.*
import org.jetbrains.anko.toast

class SelectExerciseActivity : AppCompatActivity() {

    private val X = KHelp(this.javaClass.simpleName)

    lateinit var exercisesListAdapter: ArrayAdapter<KExercise>
    lateinit var DBH : DBHelp
    var exercisesList = ArrayList<KExercise>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_exercise)
        exercisesListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,exercisesList)


        myListExercises.adapter=exercisesListAdapter
        myListExercises.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                      X.warn("onItemClickListener position = $position    id= $id")
        } }


        val idLesson = intent.getLongExtra("lessonID",0)
        DBH=DBHelp.getInstance(this)
        loadExercises(idLesson)


    }

    fun addExerciseClick(view: View) {
        X.warn("")
        val intento1 = Intent(this, SelectKindOfEx::class.java)
        //intento1.putExtra("lessonID",iD)
        startActivityForResult(intento1,2 )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val message = data?.getLongExtra("IDKind",0)
        X.warn (" KindOf Excercise:   $message  ")
    }



    fun loadExercises(lessonID:Long){
        X.warn ("lessonID=$lessonID")
        val a:List<KExercise> = DBH.loadLessonExercises(lessonID)
        for(item in a)   exercisesList.add(item)
        exercisesListAdapter.notifyDataSetChanged()
    }


}
