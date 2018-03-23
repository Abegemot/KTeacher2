package com.begemot.KTeacher

import android.content.DialogInterface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.support.v7.app.AlertDialog
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.view.*
import android.widget.*
import com.begemot.klib.*
import com.begemot.KTeacher.KLayout.Companion.kTextView
import com.begemot.KTeacher.KLayout.Companion.kTextViewRight
import com.google.protobuf.ByteString
import io.grpc.myproto.Subject
import io.grpc.myproto.Word
import io.grpc.myproto.WordSample
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.TOP
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.END
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.BOTTOM
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.START
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.RIGHT
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.LEFT
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.listeners.onClick
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.RecyclerView
import com.begemot.KTeacher.koin.SubjectViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject


/**
 * Created by dad on 21/02/2018.
 */
private val X = KHelp("subjectactiviry")

class SubjectActivity:KBaseActivity(){
    companion object kindo {
        val S=1
        val W=2
        val WS=3
    }
    val myViewModel:SubjectViewModel by viewModel()

    private val sView=SubjectView()
    private val LWSubject= mutableListOf<Word>()
    lateinit var S:Subject
    private var subjectID=0L
    var ADiag: AlertDialog? = null
   // val mmm:myProviderHelper by inject { mapOf("ctx" to applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        X.err("JJJJJJJJJJJJJJ")


     //   mmm.test()
        super.onCreate(savedInstanceState)
        subjectID=intent.getLongExtra("subjectId",0)
        myViewModel.init(subjectID.toInt())
        val a=DBP

        myViewModel.Sw.observe(this,android.arch.lifecycle.Observer { Sw->
            //X.err("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx    I Observe  $Sw")
            if(Sw!=null) {
                S=Sw
                subjectToView(Sw)
                sView.pb.visibility=View.INVISIBLE
            }
            //toast("fdafffa")

         })


        sView.setContentView(this)

      //  async(kotlinx.coroutines.experimental.android.UI) {
            // X.err(" entra ")
            //sView.pb.visibility=View.VISIBLE
          //  val data: Deferred<Subject> = bg {
            //    loadSubject(subjectID)
            //}
            //subjectToView(data.await())
            //sView.pb.visibility=View.GONE
            //X.err(" surt ")

        //}

    }

    override fun onStart() {
        super.onStart()
        X.err("Hosti tu tiu ---> ${myViewModel.sayHello()}")

        sView.pb.visibility=View.VISIBLE
        myViewModel.getSubject(subjectID.toInt())

       // subjectToView(loadSubject(subjectID))
       // X.err("Surt de create")


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.m_subject, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent subjectActivity in AndroidManifest.xml.
        if(DEBUG)X.warn("")
        return when (item.itemId) {
            R.id.ksettings->{
                dsettings()
                true
            }
            R.id.editSubject->{
                editSubject();
                toast("editSubject")
                true
            }
            R.id.deleteSubject->{
                toast("deleteSubject")

                true
            }
            R.id.addWord->{
                toast("addword")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun dsettings() {
        var CC:AlertBuilder<DialogInterface>
        lateinit var cheromanized:CheckBox
        lateinit var cheallmenus:CheckBox
        val D=alert {
            title = getText(R.string.viewromanization)
            positiveButton("Ok") {
                var changed=false
                if(cheromanized.isChecked!= Kprefs.bRomanized){
                    Kprefs.bRomanized=cheromanized.isChecked
                    changed=true
                }
                if(cheallmenus.isChecked!= Kprefs.bAllMenus){
                    Kprefs.bAllMenus=cheallmenus.isChecked
                    changed=true
                }
                if(changed) recreate()
            }
            customView {
                verticalLayout {
                    padding = dip(12)
                    cheromanized =checkBox {
                        text = getText(R.string.viewromanization)
                        isChecked= Kprefs.bRomanized
                    }
                    cheallmenus=checkBox {
                        text = getText(R.string.viewlocalmenus)
                        isChecked= Kprefs.bAllMenus
                    }
                   /* button("Ok"){
                       //gravity= Gravity.END
                       onClick {
                           toast("kaligula")
                       }
                        setOnCreateContextMenuListener(){}
                   }.lparams(wrapContent, wrapContent){gravity=Gravity.END}*/

                }
            }

        }
        //cheromanized.isChecked= prefs.bRomanized
        D.show()



    }

    fun loadSubject(id:Long):Subject{
        X.err("1---------------------------------------------->")
//        sView.pb.visibility=View.VISIBLE
//        X.err(     execute {  S=myCloud.getSubject(id.toInt()) } )
//        X.err(     execute {  S=DBP.retrieveCachedObject(id.toInt()) } )

        X.err(     execute {  S=DBP.getSubject(id.toInt()) } )

        X.err("2<---------------------------------------------")
        return S
    }

    fun subjectToView(s:Subject){

        //X.err(" subject tp view  name: ${s.name}")
        sView.t1.text=s.name
        sView.t2.text=s.translated

        sView.t3.text=s.romanized
        if (s.romanized.isEmpty() || !Kprefs.bRomanized )
            sView.t3.visibility=View.GONE

        //X.err("nwords : ${s.wordsList.size}")
        LWSubject.addAll(s.wordsList)
        sView.mAdapter.notifyDataSetChanged()
        //err("size audio ${s.s1.size()}")

    }


    fun getDataProvider():List<Word> = LWSubject


    fun playWordAt(i:Int){
        /*for(w in S.wordsList){
            if(w.wsCount>0){
                X.err("sample words --------> ${w.wsCount}")
                for(ws in w.wsList){
                    X.err("word sample   ${ws.t1} ${ws.t2} ${ws.t3}   ")
                }
            }
        }*/

        KT.saveSoundToFile(ctx,S.getWords(i).s1.toByteArray())
        KT.playSound(ctx)
        toast("play Word click size sound: ${S.getWords(i).s1.size()}  ${S.getWords(i).t1}")

    }

    fun playWordAt(ba:ByteString){
        KT.saveSoundToFile(ctx,ba.toByteArray())
        KT.playSound(ctx)
        toast("play Word click size sound: ${ba.size()}")  //${S.getWords(i).t1}")
    }


    fun editSubject(){

        ADiag=editSubject(this,S)

    }

    fun onKmenuS(mi:MenuItem,z:Subject?):Boolean{
        val ss=z as Subject
        if(z!=null) toast(ss.translated)
        return true

    }
    fun onKmenuW(mi:MenuItem,z:Word?):Boolean{
        val ss=z as Word
        if(z!=null) toast(ss.t2)
        return when (mi.itemId) {
            R.id.editWord->{
                toast("editword")
                true
            }
            R.id.deleteWord->{
                toast("deleteword")
                true
            }
            R.id.addExample->{
                toast("addExample")
                true
            }
            else -> super.onOptionsItemSelected(mi)
        }
        return true
    }
    fun onKmenuWS(mi:MenuItem,z:WordSample?):Boolean{
        val ss=z as WordSample
        if(z!=null) toast(ss.t2)
        return when (mi.itemId) {
            R.id.deleteExample->{
                toast("deleteexample")
                true
            }
            R.id.editExample->{
                toast("editExample")
                true
            }
            else -> super.onOptionsItemSelected(mi)
        }
        return true
    }




    fun setUpImg(v:View,rmenu:Int,kind:Int){
            v.onClick {
            X.err("yo clicke!!!")
            val popUp1 = PopupMenu(ctx, v)

            popUp1.inflate(rmenu)

            val menuHelper = MenuPopupHelper(ctx, popUp1.getMenu() as MenuBuilder, v)
            menuHelper.setForceShowIcon(true)

            popUp1.setOnMenuItemClickListener {
                   when(kind){
                       (kindo.S)  -> onKmenuS(it,v.tag as Subject)
                       (kindo.W)  -> onKmenuW(it,v.tag as Word)
                       (kindo.WS) -> onKmenuWS(it,v.tag as WordSample)
                       else       -> true
                   }

//                  onKmenu(it,v.tag)
//                onOptionsItemSelected(it)

            }

            menuHelper.show()

            /*popUp1.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editSubject->{owner.editSubject(); true}
                    else -> true
                }
            }
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popUp1)
                mPopup.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)
            } catch (e: Exception){
                X.err("Main Error showing menu icons. $e ")
            } finally {
                popUp1.show()
            }*/
        }
    }
}


class SubjectView:AnkoComponent<SubjectActivity>{
    private val X = KHelp(this.javaClass.simpleName)
    private object Ids{
        const val t1 = 1
        const val t2 = 2
        const val t3 = 3
        const val myList  = 4
        const val pb = 5
        const val img1  = 6
    }
    lateinit var t1: TextView
    lateinit var t2: TextView
    lateinit var t3: TextView
    lateinit var mListView: ListView
    lateinit var popUp1: PopupMenu
    lateinit var mAdapter:SubjectAdapter
    lateinit var pb: ProgressBar
    lateinit var img1:ImageView




    override fun createView(ui: AnkoContext<SubjectActivity>): View = with(ui) {
        mAdapter= SubjectAdapter(owner)

        constraintLayout{
                    lparams(matchParent, matchParent)
            background = context.getDrawable(com.begemot.klib.R.color.yellow)


/*
            img1 =imageView(R.drawable.ic_more_vert_black_24dp){
                        background = context.getDrawable(com.begemot.klib.R.color.green)
                        id=Ids.img1
                        padding=dip(7)
                    }
             img1.visibility=View.GONE
            //setUpImg(context,img1,owner)*/

            t1 = kTextView("aavv", Ids.t1).lparams(0, wrapContent)
            t2 = kTextView("translated mantilaculosoa avv  mantilaculoso aavv mantilaculoso z ", Ids.t2).lparams(0, wrapContent)
            t3 = kTextView("romanized ", Ids.t3,textsize = 12f).lparams(0, wrapContent)
            if (!Kprefs.bRomanized or t3.text.isEmpty())  t3.visibility = View.GONE



           // }.lparams(matchParent, wrapContent)


            mListView = themedListView{
                //background = resources.getDrawable( R.drawable.border_edit_text, null)
                background=context.getDrawable(com.begemot.klib.R.color.yellow)

                adapter=mAdapter
                id= Ids.myList
                setOnItemClickListener { parent, view, position, id -> owner.playWordAt(position)

                }

            }.lparams(0, 0){
                setMargins(4,4,4,4)
            }

            pb=progressBar(){
                isIndeterminate=true
                visibility=View.INVISIBLE
                id=Ids.pb
            }

            applyConstraintSet {
                t1{
                    connect(
                            TOP of t1 to TOP of PARENT_ID,
                            LEFT of t1 to LEFT of PARENT_ID,
                            RIGHT of t1 to RIGHT of PARENT_ID
                    )
                }

               t2{
                    connect(
                            TOP of t2 to BOTTOM of t1,
                            START of t2 to START of PARENT_ID,
                            RIGHT of t2 to LEFT of PARENT_ID
                    )
                }

                t3{
                    connect(
                            TOP of t3 to BOTTOM of t2,
                            START of t3 to START of PARENT_ID,
                            END of t3 to END of PARENT_ID
                    )
                }
                mListView {
                    connect(
                            TOP of mListView to BOTTOM of t3,
                            START of mListView to START of PARENT_ID,
                            BOTTOM of mListView to BOTTOM of PARENT_ID,
                            END of mListView to END of PARENT_ID
                    )
                }
                Ids.pb {
                    connect(
                            TOP of Ids.pb to TOP of PARENT_ID,
                            BOTTOM of Ids.pb to BOTTOM of PARENT_ID,
                            START of  Ids.pb to START of PARENT_ID,
                            END   of Ids.pb to END of PARENT_ID
                    )
                }
            }
        }

    }
}



class SubjectAdapter3(val subjectActivity: SubjectActivity): BaseAdapter(){
    var list = subjectActivity.getDataProvider()
    val LBinds= mutableMapOf<String,View>()
    lateinit var pp: ConstraintLayout

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return with(p2!!.context){
            verticalLayout(){
                lparams(matchParent, matchParent)
                background=context.getDrawable(R.color.beig)

                pp=PP(LBinds)
                pp.lparams(matchParent, matchParent)

                (LBinds["t1"] as TextView).text=list[p0].t1
                (LBinds["t2"] as TextView).text=list[p0].t2
                (LBinds["t3"] as TextView).text=list[p0].t3
                (LBinds["romanized"] as TextView).text=list[p0].romanized
                //(LBinds["lsamples"] as RecyclerView).adapter=WSamplesRecyclerAdapter(list[p0].wsList)
                (LBinds["lsamples"] as RecyclerView).adapter.notifyDataSetChanged()

                //pp.onClick { owner.playWord() }
                LBinds["pp"]=pp
                X.err("n ws  ${list[p0].wsList.size}")

            }


        }

    }

    override fun getItem(p0: Int): Word = list.get(p0)

    override fun getCount(): Int = list.size

    override fun getItemId(p0: Int): Long = getItem(p0).id.toLong()

}


class SubjectAdapter (val subjectActivity: SubjectActivity): BaseAdapter() {
    private object Ids {
        const val t1 = 1
        const val t2 = 2
        const val t3 = 3
        const val img1 = 5
        const val rl = 6

    }

    lateinit var t1: TextView
    lateinit var t2: TextView
    lateinit var t3: TextView
    lateinit var img1: ImageView
    lateinit var rl: LinearLayout

    var list = subjectActivity.getDataProvider()


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val word = getItem(p0)
        return with(p2!!.context) {

            constraintLayout {
                background = context.getDrawable(com.begemot.klib.R.color.gray)
                padding = dip(6)

                t1 = kTextView(word.t1, Ids.t1).lparams(0, wrapContent)

                img1 = imageView(getDrawable(R.drawable.ic_more_vert_black_24dp)) {
                    background = context.getDrawable(com.begemot.klib.R.color.green)
                    padding = dip(5)
                    id = Ids.img1
                    tag=word
                }.lparams(wrapContent, wrapContent)

                if (!Kprefs.bAllMenus) img1.visibility = View.GONE
                else subjectActivity.setUpImg(img1, R.menu.m_word,SubjectActivity.W)                               //setUpImg(context, img1, ss,R.menu.m_word)

                t2 = kTextView(word.t2 + " " + word.t3, Ids.t2)

                t3 = kTextViewRight(word.romanized, Ids.t3)

                if (!Kprefs.bRomanized || word.romanized.isEmpty() ) t3.visibility = View.GONE


                rl = verticalLayout {
                    background = context.getDrawable(R.color.pink)
                    id = Ids.rl
                    var k = 14
                    val last = word.wsList.size
                    var init = 1
                    for (ws in word.wsList)   {
                        var img2: ImageView
                        relativeLayout {

                            //setOnItemClickListener { parent, view, position, id -> owner.playWordAt(position)
                            setOnClickListener{subjectActivity.playWordAt(ws.s1 ) }

                            background = context.getDrawable(R.color.beig)
                            img2 = imageView(R.drawable.ic_more_vert_black_24dp) {
                                background = context.getDrawable(R.color.white)
                                id = k + 4
                                padding = dip(7)
                                tag = word.getWs(init - 1)


                            }.lparams() { alignParentRight();bottomOf(14) }

                            if (!Kprefs.bAllMenus) img2.visibility = View.GONE
                            else subjectActivity.setUpImg(img2, R.menu.m_wordexample,SubjectActivity.WS)              //setUpImg(context, img1, ss,R.menu.m_wordexample)


                            kTextView(ws.t1, k + 1).lparams() { startOf(k + 4);alignParentStart() }
                            kTextView(ws.t2 + " " + ws.t3, k + 2).lparams() { bottomOf(k + 1);startOf(k + 4);alignParentStart() }
                            val kk=kTextViewRight(ws.romanized, k + 3).lparams() { bottomOf(k + 2);alignParentEnd() }

                            if (!Kprefs.bRomanized || ws.romanized.isEmpty() )  kk.visibility = View.GONE


                            if (init < last) {
                                view {
                                    background = context.getDrawable(R.color.black)
                                }.lparams() { height = dip(1); bottomOf(k + 3) }
                            }



                            if (!Kprefs.bRomanized || ws.romanized.isEmpty()) t3.visibility = View.GONE


                        }.lparams() { margin = dip(4)


                        }
                        k++
                        init++
                    }


                }.lparams(0, wrapContent) { margin = dip(8);padding = dip(4) }


                applyConstraintSet {
                    img1 {
                        connect(
                                TOP to TOP of PARENT_ID,
                                RIGHT to RIGHT of PARENT_ID
                        )
                    }
                    t1 {
                        connect(
                                TOP of t1 to TOP of PARENT_ID,
                                LEFT of t1 to LEFT of PARENT_ID,
                                RIGHT of t1 to LEFT of img1
                        )
                    }

                    t2 {
                        connect(
                                TOP of t2 to BOTTOM of t1,
                                START of t2 to START of PARENT_ID,
                                RIGHT of t2 to LEFT of PARENT_ID
                        )
                    }

                    t3 {
                        connect(
                                TOP of t3 to BOTTOM of t2,
                                RIGHT of t3 to RIGHT of PARENT_ID
                                //END of t3 to END of PARENT_ID
                        )
                    }
                    rl {
                        connect(
                                TOP of rl to BOTTOM of t3,
                                LEFT of rl to LEFT of PARENT_ID,
                                RIGHT of rl to RIGHT of PARENT_ID,
                                BOTTOM of rl to BOTTOM of PARENT_ID
                        )
                    }
                }
            }
        }
    }

    override fun getItem(p0: Int): Word = list.get(p0)

    override fun getCount(): Int = list.size

    override fun getItemId(p0: Int): Long = getItem(p0).id.toLong()

}


