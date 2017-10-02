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
import org.jetbrains.anko.toast

class SelectExerciseActivity : AppCompatActivity() {

    private val X = KHelp(this.javaClass.simpleName)

    lateinit var exercisesListAdapter: ArrayAdapter<KExercise>
    lateinit var DBH : DBHelp
    var exercisesList = ArrayList<KExercise>()
    var currentLessonID:Long=0
    lateinit var selectedExercise : KExercise
    var currentPosition:Int = 0

    class RequestCode {
        companion object {
            val SELECT_KINDOF_EXERCISE :Int = 10
            val EDIT_EXERCISE  :Int = 20
            val ADD_EXERCISE   :Int = 30
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_exercise)
        exercisesListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,exercisesList)


        myListExercises.adapter=exercisesListAdapter
        myListExercises.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 //selectedLesson = myList.getItemAtPosition(position) as KLesson
                      X.warn("onItemClickListener position = $position    id= $id")
                      selectedExercise = myListExercises.getItemAtPosition(position) as KExercise
                      currentPosition=position


                      var intent1:Intent?=null
                      X.warn("---------------------------selected KIND OF exercise   ${selectedExercise.TypeOfEx}")
                      if(selectedExercise.TypeOfEx==1)  intent1 =Intent(this@SelectExerciseActivity, Exercise1::class.java)
                      if(selectedExercise.TypeOfEx==3)  intent1 =Intent(this@SelectExerciseActivity, Exercise3::class.java)

                      intent1?.putExtra("lessonID",currentLessonID)
                      intent1?.putExtra("exerciseID",selectedExercise.ID)
                      startActivityForResult(intent1,RequestCode.EDIT_EXERCISE )
            }


        }


        currentLessonID = intent.getLongExtra("lessonID",0)
        idTitle.text=intent.getStringExtra("lessonName")

        DBH=DBHelp.getInstance(this)
        loadExercises(currentLessonID)


    }

    fun addExerciseClick(view: View) {
        X.warn("")
        val intento1 = Intent(this, SelectKindOfEx::class.java)
        //intento1.putExtra("lessonID",iD)
        startActivityForResult(intento1,RequestCode.SELECT_KINDOF_EXERCISE )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestCode.SELECT_KINDOF_EXERCISE) {
            X.warn("REQUESTCODE=SELECT_KINDOF_EXERCISE")

        val KoEx = data?.getLongExtra("IDKind",0)
        X.warn (" KindOf Excercise:   $KoEx  ")
        if(KoEx!=null)
        newExercise(KoEx)
       /* when(KoEx){
            1L ->{
                   X.warn("asw")
                   val intento1 = Intent(this, Exercise1::class.java)
                   intento1.putExtra("lessonID",currentLessonID)
                   intento1.putExtra("exerciseID",0L)
                   intento1.putExtra("kindOfEx",1)
                   startActivityForResult(intento1,RequestCode.ADD_EXERCISE )
            }
            else -> {
                toast("NOT IMPLEMENTED")
                X.warn("NOT IMPLEMENTED")
            }
        }*/

        }
        if(requestCode==RequestCode.EDIT_EXERCISE){
            X.warn("REQUESTCODE=EDIT_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                selectedExercise = myListExercises.getItemAtPosition(currentPosition) as KExercise
                selectedExercise.TL1=nameE
                exercisesListAdapter.notifyDataSetChanged()

            }


        }
        if(requestCode==RequestCode.ADD_EXERCISE){
            X.warn("REQUEST CODE = ADD_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                val idE = data?.getLongExtra("IDNewExercise", 0L)
                exercisesList.add(KExercise(idE!!,currentLessonID,1,nameE))
                exercisesListAdapter.notifyDataSetChanged()
                X.warn("now ID = $idE")
                //myList.setItemChecked(myListAdapter.count-1,true)
                //myList.setSelection(myListAdapter.count-1)
                //X.warn("new lesson ID ${idL.toString()}   name: $nameL")

            }

        }

    }



    fun newExercise(KindOf:Long){

        var intento1:Intent? = null
        if(KindOf==1L) intento1=Intent(this, Exercise1::class.java)
        if(KindOf==3L) intento1=Intent(this, Exercise3::class.java)
        if(KindOf==2L){toast("NOT IMPLEMENTED"); return }

        intento1?.putExtra("lessonID",currentLessonID)
        intento1?.putExtra("exerciseID",0L)
        intento1?.putExtra("kindOfEx",1)
        startActivityForResult(intento1,RequestCode.ADD_EXERCISE )


    }



    fun loadExercises(lessonID:Long){
        X.warn ("lessonID=$lessonID")
        val a:List<KExercise> = DBH.loadLessonExercises(lessonID)
        for(item in a)   exercisesList.add(item)
        exercisesListAdapter.notifyDataSetChanged()
    }


}
