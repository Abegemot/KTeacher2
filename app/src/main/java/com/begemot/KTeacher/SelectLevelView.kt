package com.begemot.KTeacher

import android.graphics.Color.WHITE
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.begemot.klib.KHelp
import com.begemot.klib.KLevel
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.viewPager

/**
 * Created by dad on 03/02/2018.
 */
class SelectLevelView : AnkoComponent<MainActivity> {
    private val X = KHelp(this.javaClass.simpleName)
    var mAdapter: MyAdapter? = null


    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        mAdapter = MyAdapter(owner)

        coordinatorLayout {
           // fitsSystemWindows=true
            lparams(width = matchParent, height = matchParent)
           // themedAppBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {

                /*var toolbar = toolbar().lparams(width = matchParent, height = wrapContent) {

                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                }*/
             //   setExpanded(true)

               // toolbar {

                 //   title="mala pa malu"
                 //   inflateMenu(R.menu.langmenu)
               // }
                /*var tabLayout = tabLayout().lparams(width = matchParent, height = wrapContent) {
                    scrollFlags = 0
                }*/

            //}.lparams(width = matchParent, height = wrapContent)

            owner.supportActionBar?.setIcon(R.drawable.ic_unionjack)
            owner.toolbar().setNavigationIcon(R.drawable.ic_unionjack)
            verticalLayout {

                listView {

                    adapter = mAdapter
                    setOnItemClickListener { parent, view, position, id -> startActivity<LessonsLevelActivity>("level" to id) }
                }
            }.lparams(width= matchParent,height = matchParent){
             // behavior=AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}



class MyAdapter(val activity: MainActivity):BaseAdapter(){
    var list=activity.getDataProvider()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val level=getItem(p0)
         return with(p2!!.context){
             relativeLayout{
                 textView(level.id.toString()){
                     textSize=22f
                     leftPadding=dip(6+p0*12)
                     //leftPadding=12

                 }.lparams{
                     alignParentLeft()
                 }
                 /*textView("mamon"){
                     textSize=16f
                 }.lparams{
                     alignParentBottom()
                     alignParentRight()
                 }*/

             }
         }
    }

    override fun getItem(p0: Int): KLevel {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
               return getItem(p0).id
    }

}