package com.begemot.KTeacher

import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.begemot.klib.DBHelp.Companion.X
import com.begemot.klib.KLayout.Companion.kTextView

import com.begemot.klib.KT
import com.begemot.klib.myCloud

import io.grpc.myproto.Word
import io.grpc.myproto.WordSample
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 * Created by dad on 20/02/2018.
 */
class WordActivity:KBaseActivity(){
    private var wordId=0L
    private val wView=WordView()
    val LW=mutableListOf<WordSample>()
    lateinit var W:Word

    fun getDataProvider():List<WordSample> = LW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordId=intent.getLongExtra("wordId",0)
        wView.setContentView(this)

        async(kotlinx.coroutines.experimental.android.UI) {
            val data: Deferred<Word> = bg {
                loadWord(wordId)
            }
            wordToView(data.await())
        }
    }

    fun loadWord(id:Long):Word{
        X.err("id $id")
        W= myCloud.getWord(id.toInt())
        return W
        /*val v=Word.newBuilder()
        v.romanized= " romanizado "
        v.t1="t1"
        v.t2="t2"
        v.t3="t3"

        return  v.build()*/
    }

    fun wordToView(w:Word){
        wView.myText1.text=w.t1
        wView.myText2.text=w.t2
        wView.myText3.text=w.t3
        wView.myText4.text=w.romanized
        X.err("nsamples : ${w.wsList.size}")
        LW.addAll(w.wsList)
        wView.mAdapter.notifyDataSetChanged()
        X.err("size audio ${w.s1.size()}")
    }


    fun playWord(){
        KT.saveSoundToFile(ctx,W.s1.toByteArray())
        KT.playSound(ctx)
        toast("play Word click size sound :${W.s1.size()}")

    }

    fun playWordAt(i:Int){
        KT.saveSoundToFile(ctx,W.getWs(i).s1.toByteArray())
        KT.playSound(ctx)
        toast("play Word click size sound :${W.s1.size()}")

    }
}

typealias KC=ConstraintSetBuilder.Side

class WordView:AnkoComponent<WordActivity>{
    private object Ids{
        const val myText1=10
        const val myText2=20
        const val myText3=30
        const val myText4=35
        const val myList =40
    }
    lateinit var myText1:TextView
    lateinit var myText2:TextView
    lateinit var myText3:TextView
    lateinit var myText4:TextView
    lateinit var myList:ListView
    lateinit var mAdapter:WordSamplesAdapter


    override fun createView(ui: AnkoContext<WordActivity>): View=with(ui) {
        mAdapter=WordSamplesAdapter(owner)
        constraintLayout{
            onClick { owner.playWord() }

            background=context.getDrawable(R.color.pink)

            myText1= kTextView("original 1",Ids.myText1)

            myText2= test2("translated 2",Ids.myText2)

            myText3= test2("genero   3",Ids.myText3)

            myText3.textSize=12f


            myText4= test2("romanized 4",Ids.myText4)

            myList = themedListView{
                background = resources.getDrawable( R.drawable.border_edit_text, null)
                adapter=mAdapter
                id=Ids.myList
                setOnItemClickListener { parent, view, position, id -> owner.playWordAt(position)  }




            }.lparams(0,0){
                setMargins(4,4,4,4)
            }
            applyConstraintSet{
                Ids.myText1{
                    connect(
                      KC.TOP of Ids.myText1 to KC.TOP of PARENT_ID,
                      KC.START of Ids.myText1 to KC.START of PARENT_ID
                    )
                }
                Ids.myText2{
                    connect(
                            KC.TOP of Ids.myText2 to KC.BOTTOM of Ids.myText1,
                            KC.START of Ids.myText2 to KC.START of PARENT_ID
                    )
                }
                Ids.myText3{
                    connect(
                            KC.TOP of Ids.myText3 to KC.TOP of PARENT_ID,
                            KC.END of Ids.myText3 to KC.END of PARENT_ID
                    )
                }
                Ids.myText4{
                    connect(
                            KC.TOP of Ids.myText4 to KC.BOTTOM of Ids.myText2,
                            KC.START of Ids.myText4 to KC.START of PARENT_ID
                    )
                }
                Ids.myList{
                    connect(
                            KC.TOP of Ids.myList to KC.BOTTOM of Ids.myText4,
                            KC.START of Ids.myList to KC.START of PARENT_ID,
                            KC.BOTTOM of Ids.myList to KC.BOTTOM of PARENT_ID,
                            KC.END of Ids.myList to KC.END of PARENT_ID
                    )
                }




            }

        }


    }





}

class WordSamplesAdapter(val activity: WordActivity):BaseAdapter(){
    var list=activity.getDataProvider()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val wordsample=getItem(p0)
        return with(p2!!.context){
            linearLayout{
                padding=dip(6)
                orientation = LinearLayout.VERTICAL
                textView(wordsample.t1){
                    textSize=16f
                    //padding=dip(6)
                }
                textView(wordsample.t2){
                    textSize=14f
                    //padding=dip(6)
                }
                textView(wordsample.romanized){
                    textSize=14f
                    //padding=dip(6)
                    if (wordsample.romanized.isBlank())   visibility= View.GONE

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

    override fun getItem(p0: Int): WordSample {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).idWord.toLong()
    }


}



fun _ConstraintLayout.test2(s: String, i: Int): TextView {

    return textView() {
        id = i
        text = s
        setBackgroundColor(android.graphics.Color.BLUE)
        textSize = 15f
        padding = dip(5)
    }

}