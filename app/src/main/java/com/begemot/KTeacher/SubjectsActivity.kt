package com.begemot.KTeacher

import android.os.Bundle
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.support.constraint.ConstraintSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.begemot.klib.KLayout.Companion.kTextView
import io.grpc.myproto.Subject
import io.grpc.myproto.WordSample
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * Created by dad on 21/02/2018.
 */
class SubjectsActivity:KBaseActivity(){
    private val sView=SubjectsView()
    private val LSubjects= mutableListOf<Subject>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sView.setContentView(this)

    }

    fun getDataProvider():List<Subject> = LSubjects
}

class SubjectsView:AnkoComponent<SubjectsActivity>{
    private object Ids{
        const val myText1=10
        const val myText2=20
        const val myText3=30
        const val myText4=35
        const val myList =40
    }
    lateinit var myText1: TextView
    lateinit var myText2: TextView
    lateinit var myText3: TextView
    lateinit var myText4: TextView
    lateinit var myList: ListView
    lateinit var mAdapter:SubjectsAdapter

    override fun createView(ui: AnkoContext<SubjectsActivity>): View = with(ui) {
       mAdapter= SubjectsAdapter(owner)
       constraintLayout{
           myText1=kTextView("patata",Ids.myText1)
           myList = themedListView{
               background = resources.getDrawable( R.drawable.border_edit_text, null)
               adapter=mAdapter
               id= Ids.myList
               setOnItemClickListener { parent, view, position, id -> toast("r.playWordAt(position) ") }
           }.lparams(0,0){
               setMargins(4,4,4,4)
           }
           applyConstraintSet {
               Ids.myList {
                   connect(
                           KC.TOP of Ids.myList to KC.TOP of PARENT_ID,
                           KC.START of Ids.myList to KC.START of PARENT_ID,
                           KC.BOTTOM of myList to KC.BOTTOM of PARENT_ID,
                           KC.END of Ids.myList to KC.END of PARENT_ID
                   )
               }
           }
       }

    }
}


class SubjectsAdapter(val activity: SubjectsActivity): BaseAdapter(){
    var list=activity.getDataProvider()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val subject=getItem(p0)
        return with(p2!!.context){
            linearLayout{
                padding=dip(6)
                orientation = LinearLayout.VERTICAL
                textView(subject.name){
                    textSize=16f
                    //padding=dip(6)
                }
                textView(subject.translated){
                    textSize=14f
                    //padding=dip(6)
                }
                textView(subject.romanized){
                    textSize=14f
                    //padding=dip(6)
                    if (subject.romanized.isBlank())   visibility= View.GONE

                }.lparams{
                    gravity= Gravity.END
                    //textAlignment=
                }

                /*textView(klesson.toString()){
                    textSize=16f
                }.lparams{
                    alignParentBottom()
                    alignParentRight()
                }*/

            }/*.applyRecursively {
                view->when(view){
                is TextView ->{
                    view.textSize=20f
                    view.padding=dip(2)
                }

            }
            }*/
        }
    }

    override fun getItem(p0: Int): Subject {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).id.toLong()
    }


}