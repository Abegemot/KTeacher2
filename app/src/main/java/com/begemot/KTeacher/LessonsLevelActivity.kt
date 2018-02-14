package com.begemot.KTeacher

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import android.support.constraint.ConstraintSet.PARENT_ID
import android.view.Gravity
import android.widget.*
import com.begemot.klib.*
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 * Created by dad on 05/02/2018.
 */
class LessonsLevelActivity : KBaseActivity() {
    private val X = KHelp(this.javaClass.simpleName)
    private val lView=LessonsView()
    private var cLevel=0L

    private class RequestCode {
        companion object {
            val SELECT_KINDOF_EXERCISE :Int = 10
            val GOTO_LESSON :Int = 20
            val ADD_LESSON  :Int = 30
            val EDIT_LESSON :Int = 40
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cLevel=intent.getLongExtra("level",0)
        title="${resources.getText(R.string.level)} $cLevel ${resources.getText(R.string.lessons)}"
        lView.setContentView(this)
    }

    fun getDataProvider():MutableList<KLesson>{
        val l=DBHelp.getInstance(this).loadAllWhere<KLesson>("LEVEL=$cLevel")
        return l.toMutableList()
    }

    fun editLessonClick(view: View){
        if(DEBUG)X.warn ("editLessonClick")
        val kl=LessonsView().getCurrentLesson()
        startActivityForResult<EditLessonActivity>(RequestCode.EDIT_LESSON, "lesson" to kl )
    }

    fun addLessonClick(view: View){
        if(DEBUG)X.warn("")
        startActivityForResult<EditLessonActivity>(RequestCode.ADD_LESSON,"lesson" to KLesson(level = cLevel) )
    }
    fun gotoLesson(kl:KLesson){
        startActivityForResult<ExercisesLessonActivity>(RequestCode.GOTO_LESSON,"lesson" to kl)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        X.err("result code $resultCode")
        if (resultCode!= Activity.RESULT_OK) return
        when(requestCode){
            RequestCode.ADD_LESSON->{
                val kl=data?.getParcelableExtra<KLesson>("lesson")
                lView.addLesson(kl!!)
                toast("add lesson")
            }
            RequestCode.EDIT_LESSON->{toast("edit lesson")}
            RequestCode.GOTO_LESSON->{toast("go to lesson")}
        }

    }

    override fun onStart() {
        super.onStart()
        if (KApp.needupdatelessons){
            var pos=lView.mListView.getItemAtPosition(KApp.cpLessons)as KLesson
            pos.name=KApp.lesson.name
            pos.altname=KApp.lesson.altname
            pos.romanization=KApp.lesson.romanization
            //pos=KApp.lesson.copy()
            //X.err(less.toString())
            lView.mAdapter.notifyDataSetChanged()
            KApp.needupdatelessons=false
        }
    }
}


class LessonsView : AnkoComponent<LessonsLevelActivity> {
    private val X = KHelp(this.javaClass.simpleName)
    lateinit var mAdapter:MyAdapter2
    var currPos=0
    lateinit var mListView:ListView

    private object Ids{
        val myList=2
        val myButtons=3
    }

    fun getCurrentLesson():KLesson{
        return mAdapter?.getItem(currPos)as KLesson
    }

   fun addLesson(kl: KLesson){
       mAdapter.list.add(kl)
       mAdapter.notifyDataSetChanged()
       mListView.setSelection(mAdapter.list.size-1)

   }

    override fun createView(ui: AnkoContext<LessonsLevelActivity>)= with(ui) {
       mAdapter= MyAdapter2(owner)
        constraintLayout{
            //lparams(width= matchParent,height = matchParent)
            //R.style.KList
             mListView=themedListView() {
                adapter=mAdapter
                id=Ids.myList
                //background=context.getDrawable(com.begemot.klib.R.color.pink)
                setOnItemClickListener{ parent,view,position,id->run{
                        currPos=position
                        KApp.cpLessons =position
                        owner.gotoLesson(getItemAtPosition(position)as KLesson)
                    }
                }
            }.lparams(0,0){padding=dip(10)}
           //val v=addView(Button(context,null,R.style.KButtonNormal))
            linearLayout{
                id=Ids.myButtons
                val b1=themedButton(R.string.edit,R.style.KButtonEx){
                    onClick { owner.editLessonClick(view) }
                }
                val b2=themedButton(R.string.add,R.style.KButtonEx){
//                    text=context.getText)
                    onClick{owner.addLessonClick(view)}
                }
                val b3=themedButton(R.string.delete,theme=R.style.KButtonNormal){
                   // text=context.getText(R.string.delete)
                }

            }/*.applyRecursively {
                view->when(view){
                //text="dsdds",R.style.KButtonNormal
                is Button->{
                    view.textSize=32f
                }
            }
            }*/
            applyConstraintSet {
                Ids.myButtons{
                    connect(
                            BOTTOM of Ids.myButtons to BOTTOM of PARENT_ID ,
                            END of Ids.myButtons to END of PARENT_ID,
                            START of Ids.myButtons to START of PARENT_ID

                    )
                }
                Ids.myList{
                    connect(
                            BOTTOM of Ids.myList to TOP of Ids.myButtons,
                            TOP of Ids.myList to TOP of PARENT_ID,
                            START of Ids.myList to START of PARENT_ID,
                            END of Ids.myList to END of PARENT_ID
                    )
                }
            }
        }
    }
}



class MyAdapter2(val activity: LessonsLevelActivity): BaseAdapter(){
    private val X = KHelp(this.javaClass.simpleName)
    var list=activity.getDataProvider()
   // android.R.layout.simple_list_item_activated_1

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val kl=getItem(p0)
        return with(p2!!.context){
            linearLayout{
                orientation = LinearLayout.VERTICAL
                textView(kl.name){
                    //textSize=22f
                    //padding=dip(6)
                }
                textView(kl.altname){
                    //textSize=22f
                    //padding=dip(6)
                }
                textView(kl.romanization){
                    //textSize=22f
                    //padding=dip(6)
                    if (kl.romanization.isBlank())   visibility=View.GONE


                }.lparams{
                    gravity=Gravity.END
                    //textAlignment=
                }

                /*textView(klesson.toString()){
                    textSize=16f
                }.lparams{
                    alignParentBottom()
                    alignParentRight()
                }*/

            }.applyRecursively {
                view->when(view){
                 is TextView->{
                    view.textSize=20f
                    view.padding=dip(2)
                   }
                }
            }
        }
    }

    override fun getItem(p0: Int): KLesson {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).id
    }
}

