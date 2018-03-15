 package com.begemot.KTeacher

import android.os.Bundle
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.begemot.klib.KHelp
import com.begemot.klib.myCloud
import io.grpc.myproto.Subject
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.listeners.onScrollListener

 /**
 * Created by dad on 21/02/2018.
 */
class SubjectsActivity:KBaseActivity(){
    private val X = KHelp(this.javaClass.simpleName)
    private val sView=SubjectsView()
    private val LSubjects= mutableListOf<Subject>()
    var endReached=false
    val firstID = 1
    val lastID  = 221

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sView.setContentView(this)
        runBlocking {
            posdelabona(endevant =  true)
        }

    }

    fun getDataProvider():List<Subject> = LSubjects

    fun getNewSubjects(s:String,endevant: Boolean=true):List<Subject> = myCloud.getSubjects(s,forward = endevant)

    fun showNewSubjects(ls:List<Subject>) {
        LSubjects.clear()
        LSubjects.addAll(ls)
        //if (LSubjects.size<10) endReached=true
        X.err("size new words : ${LSubjects.size}  ${LSubjects[0].id} ${LSubjects[LSubjects.size-1].id}")
        sView.mAdapter.notifyDataSetChanged()

    }



suspend  fun  posdelabona(endevant:Boolean=true){
       //  DBHelp.X.err("  entra endevant=$endevant  ----------> getNewSubjects s=$s")
         var s="0"
         //if (endevant && endReached){ toast("End Reached"); return }

         if(!endevant){
             val firstInList=(sView.mListView.adapter.getItem(0) as Subject).id
             if(firstInList==firstID) { toast("first element reached"); return }
             s=firstInList.toString()

         }
         if (endevant){
             val lastInList= try {
                 (sView.mListView.adapter.getItem(sView.mListView.adapter.count-1) as Subject).id
             } catch (e: Exception) {
                 0
             }
             if (lastInList==lastID){ toast("last element reached");  return }
             s=lastInList.toString()
         }

         sView.pb.visibility = View.VISIBLE

         X.err("     1")
         val newSubjets: Deferred<List<Subject>> = bg {
             X.err("------------------------------------------------------->>    $s    LOAD endevant=$endevant")
             getNewSubjects(s,endevant)
         }
    X.err("     2")
         showNewSubjects(newSubjets.await())
         sView.mListView.setSelection(0)
         sView.pb.visibility = View.INVISIBLE
    X.err("     3")
         //delay(700)
     }
 }

class SubjectsView:AnkoComponent<SubjectsActivity>{
    private val X = KHelp(this.javaClass.simpleName)
    private object Ids{
        const val myText1= 10
        const val myText2= 20
        const val myText3= 30
        const val myText4= 35
        const val myList  = 40
        const val myPb   = 45
    }
    lateinit var myText1: TextView
    lateinit var myText2: TextView
    lateinit var myText3: TextView
    lateinit var myText4: TextView
    lateinit var mListView: ListView
    lateinit var mAdapter:SubjectsAdapter
    lateinit var pb: ProgressBar

    override fun createView(ui: AnkoContext<SubjectsActivity>): View = with(ui) {
       mAdapter= SubjectsAdapter(owner)
       constraintLayout{
           pb=progressBar(){
               isIndeterminate=true
               visibility=View.INVISIBLE
               id=Ids.myPb
           }

           mListView = themedListView{
               background = resources.getDrawable( R.drawable.border_edit_text, null)
               adapter=mAdapter
               id= Ids.myList
               //setOnItemClickListener { parent, view, position, id -> toast("r.playWordAt(position) ") }
               onScrollListener {
                   setOnItemClickListener { parent, view, position, id ->  startActivity<SubjectActivity>("subjectId" to id) }


                   //setOnItemClickListener { parent, view, position, id -> startActivity<WordActivity>("wordId" to id) }

                   onScroll { view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int ->
                       /*if(!::mListView.isInitialized) return@onScroll
                       if(mListView.getAdapter().getCount()<9) return@onScroll
                       if(mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 && mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                           val r=mListView.adapter.getItem(mListView.adapter.count-1) as Subject
                           async(UI){owner.posdelabona(r.id.toString(),pb,mListView)}
                       }*/
                       //X.err("On Scroll firstVisibleItem=$firstVisibleItem  visibleItemCount=$visibleItemCount  totalItemCount=$totalItemCount ")
                       //X.err("can scroll up ${view?.canScrollList(1)}  can scroll down ${view?.canScrollList(-1)}")
                   }
                   onScrollStateChanged{view, i ->
                       if(i!=0)return@onScrollStateChanged
                       X.err(" On ScrollStateChanged $i")
                       //X.err("can scroll up ${view?.canScrollList(1)}  can scroll down ${view?.canScrollList(-1)}")

                       if (view?.canScrollList(1)==false && view?.canScrollList(-1)==false ){
                           X.err("la llista es mes petita dels que hi caben fi que pasa si son els  ultims? OK")
                           async(UI) {
                               owner.posdelabona( endevant = false)
                           }
                           //return@onScrollStateChanged
                       }
                       if(view?.canScrollList(-1)==false){
                           X.err("vui anar enrera")
                           async(UI) {
                               owner.posdelabona( endevant = false)
                           }
                       }
                       if(view?.canScrollList(1)==false){
                          X.err("vui anar endevant")
                           async(UI) {
                               owner.posdelabona( endevant = true)
                           }

                       }


                   }
                   //0 idle  1 touch scroll 2 fling
                   /*onScrollStateChanged{absListView, i ->
                       if (mListView.firstVisiblePosition==0)
                           when(i){
                               AbsListView.OnScrollListener.SCROLL_STATE_FLING -> DBHelp.X.err(" state fling ")
                               AbsListView.OnScrollListener.SCROLL_STATE_IDLE ->{
                                   //DBHelp.X.err(" state IDLE ")
                                   val r=mListView.adapter.getItem(0) as Subject
                                   async(UI){
                                       val l=r.id
                                       if(l>1) {
                                           //if(mListView.getAdapter().getCount()<9) toast("End Reached")
                                           //else
                                           DBHelp.X.err("enredera------->$l")
                                           owner.posdelabona(r.id.toString() ,pb,mListView,endevant = false)
                                       }
                                       else toast("Start reached")
                                   }
                               }
                               //AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> DBHelp.X.err("state TOUCH SCROLL")
                           }

                   }*/
               }
           }.lparams(0,0){
               setMargins(4,4,4,4)
           }
           applyConstraintSet {
               Ids.myList {
                   connect(
                           KC.TOP of Ids.myList to KC.TOP of PARENT_ID,
                           KC.START of Ids.myList to KC.START of PARENT_ID,
                           KC.BOTTOM of Ids.myList to KC.BOTTOM of PARENT_ID,
                           KC.END of Ids.myList to KC.END of PARENT_ID
                   )
               }
               Ids.myPb{
                   connect(
                           KC.TOP of Ids.myPb to KC.TOP of PARENT_ID,
                           KC.BOTTOM of Ids.myPb to KC.BOTTOM of PARENT_ID,
                           KC.START of  Ids.myPb to KC.START of PARENT_ID,
                           KC.END   of Ids.myPb to KC.END of PARENT_ID
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
                textView(subject.id.toString()+" "+subject.translated){
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