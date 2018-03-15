package com.begemot.KTeacher

import android.os.Bundle
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.support.v4.content.res.ResourcesCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AbsListView.OnScrollListener.*
import android.support.v7.widget.SearchView

import android.support.v7.widget.Toolbar
import com.begemot.klib.*
import com.begemot.klib.DBHelp.Companion.X
import io.grpc.myproto.Word
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.sdk25.listeners.onScrollListener

/**
 * Created by dad on 14/02/2018.
 */


class DictionaryActivity:KBaseActivity(){
    private val myView=DictionaryView()
    val LW=mutableListOf<Word>()
    lateinit var myToolBar:Toolbar
    lateinit var sw:SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="${resources.getText(R.string.dictionary)} "
        myView.setContentView(this)
//        val toolbar:Toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dictionary, menu)

        val mi=menu.findItem(R.id.action_search)
        sw=mi.actionView as SearchView

        if(sw!=null) {
            sw.queryHint = resources.getText(R.string.dictionarysearch)
            sw.setIconifiedByDefault(false)
            sw.afterTextChanged2 { posdelabona(it.toString(), myView.pb, myView.mListView) }
        }
        return true
    }

    fun getDataProvider():List<Word> = LW

    //fun getNewWords(s:String,endevant: Boolean=true):List<Word> = myCloud.getWords(s,endevant,KApp().getLang())
    fun getNewWords(s:String,endevant: Boolean=true):List<Word> = myCloud.getWords(s,forward = endevant)

    fun showNewWords(lw:List<Word>,adapter: BaseAdapter) {
        LW.clear()
        LW.addAll(lw)
        X.err("size new words : ${LW.size}")
        adapter.notifyDataSetChanged()
    }

   suspend  fun  posdelabona(s:String,pb:ProgressBar,mListView:ListView, endevant:Boolean=true){

            X.err("pos------>$s     ${s.length}    query:${sw.query}")
            sw.query


  //      async(UI) {
            X.err("  entra endevant=$endevant  ----------> getNewWords  $s  ")
            pb.visibility = View.VISIBLE
            val newWords: Deferred<List<Word>> = bg {
                getNewWords(s,endevant)
            }
            showNewWords(newWords.await(), mListView.adapter as BaseAdapter)
            X.err("------------------------------------------------------->>    $s    LOAD ")
            mListView.setSelection(0)
            pb.visibility = View.INVISIBLE
           // X.err(" surt endevant=$endevant  ----------> getNewWords  $s  ")
       //        }
       delay(700)



    }





}




fun SearchView.afterTextChanged2(action: suspend (String) -> Unit) {
    var hasTW=false
    lateinit var TW:SearchView.OnQueryTextListener
    lateinit var TW2:SearchView.OnQueryTextListener

    TW= object:SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {   return true   }

        override fun onQueryTextChange(newText: String?): Boolean
        {



            X.err("1    ${this.toString()}")
            X.err(("La bola entro"))
            //launch(UI) {
            //    afterTextChanged2(editable.toString())
            //}
            if (!hasTW) { X.err("crea actor")
                val eventActor = actor<String>(UI,capacity = Channel.CONFLATED) {
                    for (event in channel) {
                        action(event)
                    }

                }


                TW2=object:SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        X.err("2    ${this.toString()}")
                        X.err("vio usted a mi abuela  ${newText.toString()}   query:$query")
                        eventActor.offer(newText.toString())
                        return true
                    }


                }
                // eventActor.offer(text.toString())
                setOnQueryTextListener(TW2)
                X.err("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ")
                eventActor.offer(newText.toString())
            }
            //addTextChangedListener(TW)
            hasTW=true
            return true
        }
    }

    //if (!hasTW){
    //   hasTW=true
    setOnQueryTextListener(TW)
    //this.addTextChangedListener(TW)

    X.err("add listener")
    //}




}



fun EditText.afterTextChanged2(action: suspend (String) -> Unit) {
    var hasTW=false
    lateinit var TW:TextWatcher
    lateinit var TW2:TextWatcher

     TW= object:TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {     }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {   X.err("hola handler 1")  }

        override fun afterTextChanged(editable: Editable?) {
            X.err("1    ${this.toString()}")
            X.err(("La bola entro"))
            //launch(UI) {
            //    afterTextChanged2(editable.toString())
            //}
            if (!hasTW) { X.err("crea actor")
            val eventActor = actor<String>(UI,capacity = Channel.CONFLATED) {
                for (event in channel) {
                    action(event)
                }
            }

                X.err("hasTW   $hasTW")
                removeTextChangedListener(TW)

                TW2=object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {
                        X.err("2    ${this.toString()}")
                        X.err("vio usted a mi abuela  ${p0.toString()}")
                        eventActor.offer(text.toString())

                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {X.err("hola handler 2") }
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


                }
               // eventActor.offer(text.toString())

                addTextChangedListener(TW2)
            }
            //addTextChangedListener(TW)
            hasTW=true
        }
    }

    //if (!hasTW){
     //   hasTW=true
        this.addTextChangedListener(TW)

        X.err("add listener")
    //}




}

class DictionaryView:AnkoComponent<DictionaryActivity>{
    private object Ids{
        const val myText=10
        const val myButton=20
        const val myList=30
        const val myPb=40
        const val mSW=50
    }
    lateinit var mListView:ListView
    lateinit var mAdapter:MyAdapter5
    lateinit var pb: ProgressBar

    override fun createView(ui: AnkoContext<DictionaryActivity>)= with(ui) {
        mAdapter= MyAdapter5(owner)

        constraintLayout{
/*            val myText=themedEditText(R.style.KEdit){
                id=Ids.myText
                background = ResourcesCompat.getDrawable(resources, R.drawable.border_edit_text, null)
                setText("")
                gravity=Gravity.TOP or Gravity.LEFT
                ems=100
                imeOptions= EditorInfo.IME_ACTION_SEARCH
                inputType=InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE or InputType.TYPE_CLASS_TEXT
                lines=1

                afterTextChanged2 {
                    owner.posdelabona(it.toString(),pb,mListView)
                    //X.err("a su abuela yo la vi k ------->  ${it.toString()}")
                    //delay(5700)
                }


            }.lparams(dip(275),dip(50)){
                topMargin=dip(2)
                leftMargin=dip(2)

            }*/



           /* imageButton(R.drawable.ic_search_black_24dp){
                id=Ids.myButton
                //setBackgroundColor(Color.TRANSPARENT)
                onClick {
                        async(UI){owner.posdelabona(myText.text.toString(),pb,mListView)}
                }
            }.lparams(dip(75),dip(50)){
                rightMargin=dip(8)
            }*/


            pb=progressBar(){
                isIndeterminate=true
                visibility=View.INVISIBLE
                id=Ids.myPb
            }


            mListView=themedListView {
                adapter=mAdapter
                //background=context.getDrawable(com.begemot.klib.R.color.pink)
                background = ResourcesCompat.getDrawable(resources, R.drawable.border_edit_text, null)
                id=Ids.myList
                onScrollListener {

                    setOnItemClickListener { parent, view, position, id -> startActivity<WordActivity>("wordId" to id) }

                    onScroll { view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int ->
                                if(!::mListView.isInitialized) return@onScroll
                                if(mListView.getAdapter().getCount()<9) return@onScroll
                                if(mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 && mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                                    val r=mListView.adapter.getItem(mListView.adapter.count-1) as Word
                                    async(UI){owner.posdelabona(r.t2,pb,mListView)}
                                }
                          }

                    onScrollStateChanged{absListView, i ->
                        if (mListView.firstVisiblePosition==0)
                          when(i){
                              SCROLL_STATE_FLING -> X.err(" state fling ")
                              SCROLL_STATE_IDLE->{
                                   X.err(" state IDLE ")
                                   val r=mListView.adapter.getItem(0) as Word
                                  async(UI){  owner.posdelabona(r.t2,pb,mListView,endevant = false)}
                              }
                              SCROLL_STATE_TOUCH_SCROLL->X.err("state TOUCH SCROLL")
                          }

                    }
                }
            }.lparams(0,0){
                setMargins(4,4,4,4)
            }


            applyConstraintSet{
               /* Ids.myText{
                    connect(
                            START of Ids.myText to START of PARENT_ID,
                            TOP of Ids.myText to TOP of PARENT_ID
                    )
                }
                Ids.myButton{
                    connect(
                            END of Ids.myButton to END of PARENT_ID
                    )
                }*/
                Ids.myList{
                    connect(
                            TOP of Ids.myList to TOP of PARENT_ID,
                            BOTTOM of Ids.myList to BOTTOM of PARENT_ID,
                            START of Ids.myList to START of PARENT_ID,
                            END of Ids.myList to END of PARENT_ID

                    )
                }
                Ids.myPb{
                    connect(
                            TOP of Ids.myPb to TOP of PARENT_ID,
                            BOTTOM of Ids.myPb to BOTTOM of PARENT_ID,
                            START of  Ids.myPb to START of PARENT_ID,
                            END   of Ids.myPb to END of PARENT_ID
                    )
                }

            }
        }


    }

}

class MyAdapter5(val activity: DictionaryActivity): BaseAdapter(){
    private val X = KHelp(this.javaClass.simpleName)
    var list=activity.getDataProvider()
    // android.R.layout.simple_list_item_activated_1

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val word=getItem(p0)
        return with(p2!!.context){
            linearLayout{
                padding=dip(6)
                orientation = LinearLayout.VERTICAL
                textView(word.t1){
                    textSize=16f
                    //padding=dip(6)
                }
                textView(word.t2){
                    textSize=14f
                    //padding=dip(6)
                }
                textView(word.romanized){
                    textSize=14f
                    //padding=dip(6)
                    if (word.romanized.isBlank())   visibility= View.GONE


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

    override fun getItem(p0: Int): Word {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).id.toLong()
    }
}


