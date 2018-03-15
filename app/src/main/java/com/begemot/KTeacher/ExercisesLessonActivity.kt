package com.begemot.KTeacher

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.begemot.KTeacher.R.attr.*
import com.begemot.klib.*
import kotlinx.android.synthetic.main.activity_exercises_lesson.*
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.sdk25.listeners.onItemClick
import java.util.*

class ExercisesLessonActivity : KBaseActivity() {

    private val X = KHelp(this.javaClass.simpleName)

    lateinit var exercisesListAdapter: ArrayAdapter<KExercise>
    lateinit var DBH : DBHelp
    var exercisesList = ArrayList<KExercise>()
    var currentLessonID:Long=0
    lateinit var selectedExercise : KExercise
    var currentPosition:Int = 0
    lateinit var kl:KLesson


    class RequestCode {
        companion object {
            val SELECT_KINDOF_EXERCISE :Int = 10
            val EDIT_EXERCISE          :Int = 20
            val ADD_EXERCISE           :Int = 40
            val EDIT_LESSON            :Int = 80
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises_lesson)
        exercisesListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,exercisesList)


        myListExercises.adapter=exercisesListAdapter
        myListExercises.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 //selectedLesson = myList.getItemAtPosition(position) as KLesson
                      if(DEBUG)X.warn("onItemClickListener position = $position    id= $id")
                      selectedExercise = myListExercises.getItemAtPosition(position) as KExercise
                      currentPosition=position

                      if(DEBUG)X.warn("Selected ex = ${selectedExercise.toXString()}")

                      var intent1:Intent?=null
                      if(selectedExercise.TypeOfEx==0)  intent1 =Intent(this@ExercisesLessonActivity, Exercise1::class.java)
                      if(selectedExercise.TypeOfEx==1)  intent1 =Intent(this@ExercisesLessonActivity, Exercise2::class.java)
                      if(selectedExercise.TypeOfEx==2)  intent1 =Intent(this@ExercisesLessonActivity, Exercise3::class.java)

                      intent1?.putExtra("lessonID",currentLessonID)
                      intent1?.putExtra("exerciseID",selectedExercise.ID)
                      //intent1?.putExtra("typeOfEx",selectedExercise.TypeOfEx)
                      if(DEBUG)X.warn("before start subjectActivity for result")

            try {
                if(intent1==null) if(DEBUG)X.warn("!!!!!!!!!!!!!  XXXXXXXXXXXXXXXXXXXXXXXXXXXXX     CAGADA     INTENT NULL    XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                else              if(DEBUG)X.warn("!!!!!!!!!!!!!  XXXXXXXXXXXXXXXXXXXXXXXXXXXXX     INTENT     NO NULL    XXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                startActivityForResult(intent1,RequestCode.EDIT_EXERCISE )

            } catch (ignored: Exception) {
                if(DEBUG)X.warn("EXCEPTION")
            }


            }


        }

        kl=intent.getParcelableExtra("lesson")
        currentLessonID = kl.id
        idTitle.text=kl.name

        //kl= KLesson(intent.getLongExtra("lessonID",0),name=intent.getStringExtra("lessonName"))
        //currentLessonID = intent.getLongExtra("lessonID",0)
        //idTitle.text=intent.getStringExtra("lessonName")

        DBH=DBHelp.getInstance(this)
        loadExercises(currentLessonID)


    }

     fun addExerciseClick(view: View) {
        var sList=DBH.getKOE().toList()
        val lAdapter =  ArrayAdapter(ctx,android.R.layout.simple_list_item_activated_1,sList)
        lateinit var AL:DialogInterface
        AL= alert {
            customView {
                title=getString(R.string.sel_kind_ofex)
                verticalLayout {
                    view{
                        background=resources.getDrawable(R.color.indigo)
                        //        padding=300
                    }.lparams(width= matchParent,height = 3){
                                padding=5
                    }
                     val myListView=listView {
                        //divider=resources.getDrawable(R.drawable.abc_list_divider_mtrl_alpha)
                        adapter = lAdapter
                        choiceMode = 1

                        //id = Ids.myList
                        //padding = dip(50)
                         //background=resources.getDrawable(R.color.indigo)
                    }//.lparams(0,0)
                    myListView.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                        (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        val KOF = myListView.getItemAtPosition(position) as String
                        newExercise((1+position).toLong())
                        AL.cancel()
                        }
                    }
                }
            }
        }.show()
    }


    fun onclicknameLesson(view:View) {
        startActivityForResult<EditLessonActivity>(RequestCode.EDIT_LESSON, "lesson" to kl )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if(requestCode==RequestCode.SELECT_KINDOF_EXERCISE) {
            if(DEBUG)X.warn("REQUESTCODE=SELECT_KINDOF_EXERCISE")

        val KoEx = data?.getLongExtra("IDKind",0)
        if(DEBUG)X.warn (" KindOf Excercise:   $KoEx  ")
        if(KoEx!=null)
        newExercise(KoEx)
       /* when(KoEx){
            1L ->{
                   if(DEBUG)X.warn("asw")
                   val intento1 = Intent(this, Exercise1::class.java)
                   intento1.putExtra("lessonID",currentLessonID)
                   intento1.putExtra("exerciseID",0L)
                   intento1.putExtra("kindOfEx",1)
                   startActivityForResult(intento1,RequestCode.ADD_EXERCISE )
            }
            else -> {
                toast("NOT IMPLEMENTED")
                if(DEBUG)X.warn("NOT IMPLEMENTED")
            }
        }*/

        }*/
        if(requestCode==RequestCode.EDIT_EXERCISE){
            if(DEBUG)X.warn("REQUESTCODE=EDIT_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                selectedExercise = myListExercises.getItemAtPosition(currentPosition) as KExercise
                selectedExercise.TL1=nameE
                exercisesListAdapter.notifyDataSetChanged()

            }
            if(resultCode==Activity.RESULT_CANCELED){
                if(data==null) return  //back button
                val delete=data?.getBooleanExtra("DELETE",false)
                if(delete!!){
                    //toast("XXXXXXXXXXXXXXXXXXXXXDELETEE")
                    exercisesList.removeAt(currentPosition)
                    exercisesListAdapter.notifyDataSetChanged()
                }

            }
            //Activity.RE


        }
        if(requestCode==RequestCode.ADD_EXERCISE){
            if(DEBUG)X.warn("REQUEST CODE = ADD_EXERCISE")
            if(resultCode== Activity.RESULT_OK){
                val nameE =""+ data?.getStringExtra("Name")
                val idE = data?.getLongExtra("IDNewExercise", 0L)
                val type= data?.getIntExtra("TypeOfEx",0)
                //Todo mistake on typeof ex
                val t=0+type!!
                exercisesList.add(KExercise(idE!!, currentLessonID, t, nameE))
                exercisesListAdapter.notifyDataSetChanged()
                if(DEBUG)X.warn("now ID = $idE")
                //myList.setItemChecked(myListAdapter.count-1,true)
                //myList.setSelection(myListAdapter.count-1)
                //if(DEBUG)X.warn("new lesson ID ${idL.toString()}   name: $nameL")

            }

        }
        if (resultCode!= Activity.RESULT_OK) return
        when(requestCode){
             RequestCode.EDIT_LESSON->{

                 //kl.name = data?.getStringExtra("NAME")+""
                 //kl.id = 0+data!!.getLongExtra("ID", 0L)
                 if (data!=null){
                     kl=data.getParcelableExtra("lesson")
                 }
                 idTitle.text=kl.name
                 KApp.needupdatelessons=true
                 //KApp.newNameLesson=kl.name
                 KApp.lesson=kl.copy()
                 toast("I ara que fem?")
            }
        }



    }



    fun newExercise(KindOf:Long){

        var intento1:Intent? = null
        if(KindOf==1L) intento1=Intent(this, Exercise1::class.java)
        if(KindOf==2L) intento1=Intent(this, Exercise2::class.java)
        if(KindOf==3L) intento1=Intent(this, Exercise3::class.java)
        //if(KindOf==2L){toast("NOT IMPLEMENTED"); return }

        intento1?.putExtra("lessonID",currentLessonID)
        intento1?.putExtra("exerciseID",0L)
        intento1?.putExtra("kindOfEx",1)
        startActivityForResult(intento1,RequestCode.ADD_EXERCISE )


    }



    fun loadExercises(lessonID:Long){
        if(DEBUG)X.warn ("lessonID=$lessonID")
        val a:List<KExercise> = DBH.loadLessonExercises(lessonID)
        for(item in a)   exercisesList.add(item)
        exercisesListAdapter.notifyDataSetChanged()
    }





}
