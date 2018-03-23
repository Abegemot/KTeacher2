package com.begemot.KTeacher

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.begemot.klib.DBHelp.Companion.X
import com.begemot.KTeacher.KLayout.Companion.kTextView
import com.begemot.KTeacher.KLayout.Companion.kTextViewRight
import com.begemot.klib.KHelp

import com.begemot.klib.KT
import com.begemot.klib.myCloud
import com.google.protobuf.ByteString

import io.grpc.myproto.Word
import io.grpc.myproto.WordSample
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.intentFor


/**
 * Created by dad on 20/02/2018.
 */

data class WordSample3(val t1:String="", val t2:String="Hola pop t2", val t3:String="Hola pop t3", val romanized:String="Hola pop romano", val s1:ByteString= ByteString.EMPTY)
data class WordSample2(val t1:String="wst1", val t2:String="Hola pop t2", val t3:String="Hola pop t3", val romanized:String="Hola pop romano", val s1:ByteString= ByteString.EMPTY)

data class WordX(val t1:String="xt1", val t2:String="xHola pop t2", val t3:String="Hola pop t3", val romanized:String="Hola pop romano", val s1:ByteString= ByteString.EMPTY,val LWS:List<WordSample2> = emptyList() )




val LWS2= arrayListOf<WordSample2>(WordSample2(), WordSample2(t1 = "pop2"),WordSample2(t1 = "pop3"),WordSample2(t1 = "pop4"),WordSample2(t1 = "pop5"),WordSample2(),WordSample2() ,WordSample2() ,WordSample2()  )
//val LWS2= arrayListOf<WordSample2>(WordSample2(), WordSample2(t1 = "pop2"),WordSample2(t1 = "pop3"))
val LWS3= arrayListOf<WordSample2>(WordSample2(t1="Hosti tu tiu funciona"), WordSample2(t1 = "pop2"),WordSample2(t1 = "pop3"))


val VX = WordX(LWS= LWS2)

val LX = arrayListOf<WordX>(WordX(LWS=LWS2))


private val X = KHelp("wordactiviry")



fun playWord2(bA:ByteString){
    val ctx=KApp.ctx
    KT.saveSoundToFile(ctx,bA.toByteArray())
    KT.playSound(ctx)
}


class WordActivity:KBaseActivity(){
    private var wordId=0L
    private val wView=WordView()
    lateinit var W:Word

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordId=intent.getLongExtra("wordId",0)
        //wView.setContentView(this)
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
    }

    fun wordToView(w:Word){
        wView.setContentView(this)



        (wView.LBinds["t1"] as TextView).text=w.t1
        (wView.LBinds["t2"] as TextView).text=w.t2
        (wView.LBinds["t3"] as TextView).text=w.t3
        (wView.LBinds["romanized"] as TextView).text=w.romanized
        (wView.LBinds["lsamples"] as RecyclerView).adapter=WSamplesRecyclerAdapter(LWS3)
        //(wView.LBinds["lsamples"] as RecyclerView).adapter=WSamplesRecyclerAdapter(w.wsList)
        (wView.LBinds["lsamples"] as RecyclerView).adapter.notifyDataSetChanged()

    }

    fun playWord(){
        KT.saveSoundToFile(ctx,W.s1.toByteArray())
        KT.playSound(ctx)
    }

    fun playWordAt(i:Int){
        KT.saveSoundToFile(ctx,W.getWs(i).s1.toByteArray())
        KT.playSound(ctx)

    }

    fun editcreate(){
        startActivity<SWWSEditor>("id" to 5)
    }



}

typealias KC=ConstraintSetBuilder.Side


fun editcreate2(ctx:Context){
    with(ctx){
        startActivity<SWWSEditor>("id" to 5)
    }
}





class WordView:AnkoComponent<WordActivity>{
    lateinit var pp:ConstraintLayout

    val LBinds= mutableMapOf<String,View>()

    override fun createView(ui: AnkoContext<WordActivity>): View=with(ui) {


        verticalLayout(){
            lparams(matchParent, matchParent)
            background=context.getDrawable(R.color.beig)

            pp=PP(LBinds)
            pp.lparams(matchParent, matchParent)
            pp.onClick { owner.playWord() }
            LBinds["pp"]=pp

        }
    }
}
private val smviewPool = RecyclerView.RecycledViewPool()


fun ViewManager.PP(lmapbinds: MutableMap<String,View>) = constraintLayout {
        val Ids = object {
            val myText1 = 10
            val myText2 = 20
            val myText3 = 30
            val myText4 = 35
            val myList = 40
            val img1 = 45
        }
        lmapbinds["t1"] = kTextView("original 1 dafur dfsdf dfasdfads cvcv yyfdfsdfsdfs dffd", Ids.myText1)

        lmapbinds["t2"] = kTextView("translated 21", Ids.myText2)

        lmapbinds["t3"] = kTextView("genero   3", Ids.myText3)

        (lmapbinds["t3"] as TextView).textSize = 12f

        lmapbinds["romanized"] = kTextView("romanized 4", Ids.myText4)

        lmapbinds["menu"] = imageView(context.getDrawable(R.drawable.ic_more_vert_black_24dp)) {
            background = context.getDrawable(com.begemot.klib.R.color.green)
            padding = dip(5)
            id = Ids.img1
           //tag=word
        }.lparams(wrapContent, wrapContent)



    lmapbinds["lsamples"] = recyclerView() {

            background = resources.getDrawable(com.begemot.KTeacher.R.drawable.border_edit_text, null)
            val orientation = android.support.v7.widget.LinearLayoutManager.VERTICAL
            val mlayoutManager = android.support.v7.widget.LinearLayoutManager(context, orientation, true)
            mlayoutManager.stackFromEnd = false
            mlayoutManager.reverseLayout = false
            layoutManager = mlayoutManager
            id = Ids.myList
            //adapter= com.begemot.KTeacher.WSamplesRecyclerAdapter(kotlin.collections.emptyList())
            adapter = com.begemot.KTeacher.WSamplesRecyclerAdapter(LWS3)
            addItemDecoration( DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            recycledViewPool=smviewPool
        }.lparams(matchParent, 0) {
            setMargins(4, 4, 4, 4)
        }
        applyConstraintSet {
            Ids.myText1 {
                connect(
                        KC.TOP of Ids.myText1 to KC.TOP of android.support.constraint.ConstraintSet.PARENT_ID,
                        KC.START of Ids.myText1 to KC.START of android.support.constraint.ConstraintSet.PARENT_ID
                )
            }
            Ids.img1{
                connect(
                        KC.RIGHT of Ids.img1 to KC.RIGHT of PARENT_ID
                )
            }
            Ids.myText2 {
                connect(
                        KC.TOP of Ids.myText2 to KC.BOTTOM of Ids.myText1,
                        KC.START of Ids.myText2 to KC.START of android.support.constraint.ConstraintSet.PARENT_ID
                )
            }
            Ids.myText3 {
                connect(
                        KC.TOP of Ids.myText3 to KC.TOP of android.support.constraint.ConstraintSet.PARENT_ID,
                        KC.END of Ids.myText3 to KC.START of Ids.img1
                )
            }
            Ids.myText4 {
                connect(
                        KC.TOP of Ids.myText4 to KC.BOTTOM of Ids.myText2,
                        KC.RIGHT of Ids.myText4 to KC.RIGHT of android.support.constraint.ConstraintSet.PARENT_ID
                )
            }
            Ids.myList {
                connect(
                        KC.TOP of Ids.myList to KC.BOTTOM of Ids.myText4,
                        KC.START of Ids.myList to KC.START of android.support.constraint.ConstraintSet.PARENT_ID,
                        KC.BOTTOM of Ids.myList to KC.BOTTOM of android.support.constraint.ConstraintSet.PARENT_ID,
                        KC.END of Ids.myList to KC.END of android.support.constraint.ConstraintSet.PARENT_ID
                )
            }

        }

}



object kindo{
    val S=1
    val W=2
    val WS=3
}

fun onKmenuW(mi: MenuItem, z:Word?):Boolean{
    val ss=z as Word
    if(z!=null) X.err(ss.t2)
    return when (mi.itemId) {
        R.id.editWord->{
            X.err("editword")
            true
        }
        R.id.deleteWord->{
            X.err("deleteword")
            true
        }
        R.id.addExample->{
            X.err("addExample")
            true
        }
        else -> true
    }
    return true
}
fun onKmenuWS(mi: MenuItem, z:WordSample2?,ctx: Context):Boolean{
    val ss=z as WordSample2
    if(z!=null) X.err(ss.t2)
    return when (mi.itemId) {
        R.id.deleteExample->{
            X.err("deleteexample")
            true
        }
        R.id.editExample->{
            X.err("editExample")
            editcreate2(ctx)
            true
        }
        else -> true
    }
    return true
}





fun setUpImg(v:View,rmenu:Int,kind:Int){
    v.onClick {


        X.err("yo clicke 0 !!!")
        val popUp1 = PopupMenu(v.context, v)
        X.err("yo clicke 1 !!!")
        popUp1.inflate(rmenu)
        X.err("yo clicke 2 !!!")
        val menuHelper = MenuPopupHelper(v.context, popUp1.getMenu() as MenuBuilder, v)
        X.err("yo clicke 3 !!!")

        menuHelper.setForceShowIcon(true)
        X.err("yo clicke 4 !!!")

        popUp1.setOnMenuItemClickListener {
            when(kind){
               // (kindo.S)  -> onKmenuS(it,v.tag as Subject)
                (kindo.W)  -> onKmenuW(it,v.tag as Word)
                (kindo.WS) -> onKmenuWS(it,v.tag as WordSample2,v.context)
                else       -> true
            }
        }
        menuHelper.show()

    }
}


//if (!Kprefs.bAllMenus) img2.visibility = View.GONE
//else subjectActivity.setUpImg(img2, R.menu.m_wordexample,SubjectActivity.WS)              //setUpImg(context, img1, ss,R.menu.m_wordexample)

class WSampleHolder(v:View):RecyclerView.ViewHolder(v),View.OnClickListener{
    var aview:View=v
    lateinit var  bA:ByteString


    fun bindItems(ws:WordSample2){
        val lbind=aview.tag as MutableMap<String,View>
        X.err("ws t2 holder ${ws.t2}")
        if (ws.t1.isEmpty()) (lbind["t1"] as TextView).visibility=View.GONE
        if (ws.t2.isEmpty()) (lbind["t2"] as TextView).visibility=View.GONE
        if (ws.t3.isEmpty()) (lbind["t3"] as TextView).visibility=View.GONE
        if (ws.romanized.isEmpty()) (lbind["romanized"] as TextView).visibility=View.GONE

        with(lbind["menu"] as ImageView){
            tag=ws
            setUpImg(this,R.menu.m_wordexample,kindo.WS)
        }


        (lbind["t1"] as TextView).text=ws.t1
        (lbind["t2"] as TextView).text=ws.t2
        (lbind["t3"] as TextView).text=ws.t3
        (lbind["romanized"] as TextView).text=ws.romanized
         bA=ws.s1
         aview.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        //X.err("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ----->${t1.text}")
        playWord2(bA)
        //setOnItemClickListener { parent, view, position, id -> owner.playWordAt(position)  }
    }


}
fun ViewManager.WSLayout(lmapbinds: MutableMap<String,View>) = constraintLayout{
    val LBinds= mutableMapOf<String,View>()
    val ids= object { val img=5}

        lparams(matchParent, wrapContent)
        //lparams(300, 300)
        background=context.getDrawable(R.color.pink)
        padding=dip(6)
        //orientation = LinearLayout.VERTICAL
        LBinds["t1"] = kTextView("", i=1,textsize = 16f).lparams(wrapContent, wrapContent)
        LBinds["t2"] = kTextView("t2",i=2,textsize=14f)
        LBinds["t3"] = kTextView("t3",i=3,textsize=14f)
        LBinds["romanized"] = kTextViewRight("roma",i=4,textsize=14f)
        LBinds["menu"] = imageView(context.getDrawable(R.drawable.ic_more_vert_black_24dp)) {
            background = context.getDrawable(com.begemot.klib.R.color.green)
            padding = dip(5)
            id = ids.img
            //tag=word
        }.lparams(wrapContent, wrapContent)
        tag=LBinds
        applyConstraintSet {
            (LBinds["t1"]!!){
                connect(
                        KC.TOP to KC.TOP of PARENT_ID
                )
            }
            (LBinds["t2"]!!){
                connect(
                        KC.TOP to KC.BOTTOM of LBinds["t1"]!!
                )
            }
            (LBinds["t3"]!!){
                connect(
                        KC.TOP to KC.BOTTOM of LBinds["t2"]!!
                )
            }
            (LBinds["romanized"]!!){
                connect(
                        KC.TOP to KC.BOTTOM of LBinds["t3"]!!,
                        KC.END to KC.END of PARENT_ID
                )
            }
            (LBinds["menu"]!!){
                connect(
                        KC.TOP to KC.TOP of PARENT_ID,
                        KC.END to KC.END of PARENT_ID
                )
            }
        }
    }





class RowWSampleLayout():AnkoComponent<ViewGroup>{
    val LBinds= mutableMapOf<String,View>()
    object ids{const val img=5}
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
         return WSLayout(LBinds)
        /*return constraintLayout(){
            lparams(matchParent, wrapContent)
            //lparams(300, 300)
            background=context.getDrawable(R.color.pink)
            padding=dip(6)
            //orientation = LinearLayout.VERTICAL
            LBinds["t1"] = kTextView("", i=1,textsize = 16f).lparams(wrapContent, wrapContent)
            LBinds["t2"] = kTextView("t2",i=2,textsize=14f)
            LBinds["t3"] = kTextView("t3",i=3,textsize=14f)
            LBinds["romanized"] = kTextViewRight("roma",i=4,textsize=14f)
            LBinds["menu"] = imageView(context.getDrawable(R.drawable.ic_more_vert_black_24dp)) {
                background = context.getDrawable(com.begemot.klib.R.color.green)
                padding = dip(5)
                id = ids.img
                //tag=word
            }.lparams(wrapContent, wrapContent)
            tag=LBinds
            applyConstraintSet {
                (LBinds["t1"]!!){
                    connect(
                       KC.TOP to KC.TOP of PARENT_ID
                    )
                }
                (LBinds["t2"]!!){
                    connect(
                            KC.TOP to KC.BOTTOM of LBinds["t1"]!!
                    )
                }
                (LBinds["t3"]!!){
                    connect(
                            KC.TOP to KC.BOTTOM of LBinds["t2"]!!
                    )
                }
                (LBinds["romanized"]!!){
                    connect(
                            KC.TOP to KC.BOTTOM of LBinds["t3"]!!,
                            KC.END to KC.END of PARENT_ID
                    )
                }
                (LBinds["menu"]!!){
                    connect(
                            KC.TOP to KC.TOP of PARENT_ID,
                            KC.END to KC.END of PARENT_ID
                    )
                }
            }
        }*/

    }
}

class WSamplesRecyclerAdapter(private val WS:List<WordSample2>):RecyclerView.Adapter<WSampleHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):WSampleHolder {
         return WSampleHolder(RowWSampleLayout().createView(AnkoContext.Companion.create(parent!!.context,parent)))
    }

    override fun getItemCount(): Int {
        X.err("WS Item count  ${WS.size}")
        return WS.size
    }

    override fun onBindViewHolder(holder:WSampleHolder , position: Int) {
        X.err("onBind")
        holder.bindItems(WS[position])
    }

}





