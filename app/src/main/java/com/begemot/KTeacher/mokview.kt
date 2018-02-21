package com.begemot.KTeacher

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.begemot.klib.KHelp
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.design.R.attr.contentScrim
import android.support.design.R.attr.title
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.*
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.support.v4.content.res.ResourcesCompat
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.begemot.klib.KKindOfExercice
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.constraint.layout.themedConstraintLayout
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.toast
import java.util.ArrayList


/**
 * Created by dad on 06/02/2018.
 */
class MockView : AnkoComponent<MockActivity> {
    //private val X = KHelp(this.javaClass.simpleName)
    var mAdapter: MyAdapter3? = null

    private object Ids {
        val bAddLesson = 1
        val bDeleteLesson = 2
        val editLeson = 3
        val linearLayout = 4
        val myList = 5
        val myButton=6
        val myText=7
    }

    lateinit var KStringsLAdapter: ArrayAdapter<String>
//    var KOfexercisesList = ArrayList<String>()
    var stringList = arrayOf("adada","faff","adfafa","afaffa","z","z","z","z","z","z","z","z","z","z","z","z","z","z","z","z","z","zorglub")

    override fun createView(ui: AnkoContext<MockActivity>)=with(ui) {
        mAdapter = MyAdapter3(null)


        constraintLayout {

            val myText=themedEditText(theme=R.style.KEdit){
                id=Ids.myText
                setText("aleluya")
                //background=R.drawable.border_edit_text
                background = ResourcesCompat.getDrawable(resources, R.drawable.border_edit_text, null)
            }.lparams(dip(275),dip(50)){

                topMargin=dip(2)
                leftMargin=dip(2)
                //padding=dip(10)
            }
            imageButton(R.drawable.ic_search_black_24dp){
                id=Ids.myButton
                //background=
                //setBackgroundColor(Color.TRANSPARENT)


            }.lparams(dip(75),dip(50)){rightMargin=dip(8)

            }
            val pb=progressBar(){
                isIndeterminate=true
                visibility=View.VISIBLE



            }

           // themedButton("My Button1                        zzz", theme = R.attr.layout_insetEdge)
           // themedButton("My Button2", theme = R.style.cat_style)

            listView {
                adapter = mAdapter
                id=Ids.myList
                setOnItemClickListener { parent, view, position, id -> startActivity<LessonsLevelActivity>("level" to id) }
            }
            applyConstraintSet{
                Ids.myText{
                    connect(
                            START of Ids.myText to START of PARENT_ID,
                            TOP of Ids.myText to TOP of PARENT_ID
                            //,
                            //END of Ids.myText to START of Ids.myButton
                    )
                }
                Ids.myButton{
                    connect(
                            TOP of Ids.myButton to TOP of PARENT_ID,
                            END of Ids.myButton to END of PARENT_ID
                    )
                }
                Ids.myList{
                    connect(
                            TOP of Ids.myList to BOTTOM of Ids.myText,
                            BOTTOM of Ids.myList to BOTTOM of PARENT_ID,
                            START of Ids.myList to START of PARENT_ID,
                            END of Ids.myList to END of PARENT_ID

                    )
                }

            }
        }
    }


     fun createView3(ui: AnkoContext<MockActivity>)=with(ui) {
        //mAdapter = MyAdapter3(null)
        KStringsLAdapter =  ArrayAdapter(ctx,android.R.layout.simple_list_item_activated_1,stringList)
        //mAdapter=KOfexercisesListAdapter


        themedLinearLayout(R.style.AppTheme){
           orientation = LinearLayout.VERTICAL
           lparams(width= wrapContent,height = wrapContent)

            //background=ctx.getDrawable(R.drawable.button_test)
            //title=ctx.getString(R.string.sel_kind_ofex)
            textView(){
                text=ctx.getString(R.string.sel_kind_ofex)
            }
            textView(){
                text=ctx.getString(R.string.sel_kind_ofex)
            }

            editText("hola")

            listView(){
                adapter=KStringsLAdapter
                choiceMode = 1
                id = Ids.myList
                //padding = dip(50)
                // background=resources.getDrawable(R.drawable.button_test)
            }//.lparams(0,0)

            textView(){
                text=ctx.getString(R.string.sel_kind_ofex)
            }
            textView(){
                text=ctx.getString(R.string.sel_kind_ofex)
            }


        }
    }


     fun createView2(ui: AnkoContext<MockActivity>) = with(ui) {
        mAdapter = MyAdapter3(null)
        themedConstraintLayout(R.style.AppTheme){
        //constraintLayout(){
            //themedListView(R.style.KList) {
            listView(){
                adapter=mAdapter
                choiceMode = 1
                id = Ids.myList
                //padding = dip(50)
               // background=resources.getDrawable(R.drawable.button_test)
            }.lparams(0,0)

            linearLayout {
                id = Ids.linearLayout
                orientation = LinearLayout.HORIZONTAL

                themedButton(R.string.edit,theme=R.style.KButtonTest) {
                    id = Ids.editLeson

                }.lparams(width = wrapContent, height = wrapContent).applyRecursively {

                }

                //@android:style/Widget.Button.Inset
                //theme=android.R.style.Widget_Button_Inset
                button(R.string.add) {
                    id = Ids.bAddLesson
                        android.R.drawable.button_onoff_indicator_on
                        background=resources.getDrawable(R.drawable.button_test)

                }.lparams(width = wrapContent, height = wrapContent){

                }


                themedButton(R.string.delete,theme=R.style.KIButton) {
                    id = Ids.bDeleteLesson
                }.lparams(width = wrapContent, height = wrapContent) {
                    weight = 1F
                }

            }.lparams(width = wrapContent, height = wrapContent)
            applyConstraintSet {
                Ids.linearLayout{
                    connect(
                            BOTTOM of Ids.linearLayout to BOTTOM of PARENT_ID ,
                            END of Ids.linearLayout to END of PARENT_ID,
                            START of Ids.linearLayout to START of PARENT_ID

                    )
                }
                Ids.myList{
                    connect(
                            BOTTOM of Ids.myList to TOP of Ids.linearLayout,
                            TOP of Ids.myList to TOP of PARENT_ID,
                            START of Ids.myList to START of PARENT_ID,
                            END of Ids.myList to END of PARENT_ID
                    )

                }
            }



        }




        }





}
class MyAdapter3(val activity: MockActivity?) : BaseAdapter() {
    var list = arrayListOf<String>("pepes", "juan", "ramon", "maria \n luisa", "louisa")
    //var list=activity.getDataProvider()
    //val list= ArrayList<String>()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val level = getItem(p0)
        return with(p2!!.context) {

            linearLayout{
                orientation = LinearLayout.VERTICAL
                textView(level) {
                    textSize = 18f
                    padding = dip(6)
                }

                textView("mamon") {
                    textSize = 12f
                }

                textView("pesaomamon") {
                    textSize = 12f
                }


            }


        }
    }

    override fun getItem(p0: Int): String {
        return list.get(p0)
    }

    override fun getCount(): Int {

        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

}