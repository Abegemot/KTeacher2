package com.begemot.KTeacher

import android.R.id.background
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.*
import com.begemot.klib.KHelp
import org.jetbrains.anko.*

import java.util.*
//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale
import android.os.Build
import android.os.LocaleList
import android.R.id.edit
import android.content.DialogInterface
import android.content.SharedPreferences
import android.app.AlarmManager
import android.app.PendingIntent
import android.support.v7.appcompat.R.id.text
import android.text.Html
import android.text.Spanned
import com.begemot.KTeacher.KT.Companion.getCurrentLang
import org.jetbrains.anko.custom.customView
import org.w3c.dom.Text

//import java.awt.SplashScreen

class MainActivity : AppCompatActivity() {
    private val X = KHelp(this.javaClass.simpleName)
    lateinit var myListAdapter:ArrayAdapter<KLesson>
    lateinit var DBH : DBHelp
    var lesonList = ArrayList<KLesson>()
    lateinit var selectedLesson:KLesson
    //lateinit var sLang:String
    var curLang=""

    class RequestCode {
         companion object {
             val SELECT_KINDOF_EXERCISE :Int = 10
             val GOTO_LESSON :Int = 20
             val ADD_LESSON  :Int = 30
             val EDIT_LESSON :Int = 40
         }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        X.warn("")
        curLang=KT.getCurrentLang(this)

        setContentView(R.layout.activity_main)
        setTitle(resources.getString(R.string.klessons))


        setSupportActionBar(toolbar)
        myListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,lesonList)

        myList.adapter=myListAdapter
        myList.onItemClickListener = object : OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                X.warn("onItemClickListener position = $position    id= $id")

                selectedLesson = myList.getItemAtPosition(position) as KLesson
                //selectedLesson.name="PATATA"
                //myListAdapter.notifyDataSetChanged()
                goToLesson(id)

        } }

        DBHelp.reopen(curLang)
        DBH=DBHelp.getInstance(this)
        DBH.createEX1Examples(this)

        loadLessons()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        menuInflater.inflate(R.menu.langmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        X.warn("")
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.select_language->consume{ selectLanguage()}
            else -> super.onOptionsItemSelected(item)
        }
    }
    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }

    fun selectLanguage():Boolean{
        //toast("I WILL CHANGE LANGUAGE")
        val countries = listOf("English", "Español"  )

//       selector("${getString(R.string.sel_lang)}", countries, { dialogInterface, i -> toast("So you're living in ${countries[i]}, right?") } )
         selector("${getString(R.string.sel_lang)}", countries,{dialogInterface, i ->  setSelectedLang(dialogInterface,i)})
        return true
    }

// onClick: (DialogInterface, Int) -> Unit
    fun setSelectedLang(dI:DialogInterface,I:Int):Unit {
        //0 en 1 es

        X.warn("POS old  ${KT.getCurrentLang(this)}  new  $I")
        if(KT.getCurrentLang(this)=="en") if(I==0) return
        if(KT.getCurrentLang(this)=="es") if(I==1) return
        if(I==0) { KT.setCurrentLang(this,"en",this); return }
        if(I==1) { KT.setCurrentLang(this,"es",this); return }
        X.warn("POS  $I")
    }




    fun goToLesson(iD:Long){
        X.warn("")
        val intento1 = Intent(this, SelectExerciseActivity::class.java)
        intento1.putExtra("lessonID",selectedLesson.id)
        intento1.putExtra("lessonName",selectedLesson.name)
        //startActivityForResult(intento1,RequestCode.GOTO_LESSON )
        startActivity(intento1)
    }



//TODO erase the lesson clicked
    fun deleteLessonClick(view: View){
        X.warn ("deleteLessonClick")
        DBH.deleteLesson(10)
        lesonList.clear()
        myListAdapter.notifyDataSetChanged()
    }

    fun editLessonClick(view: View){
        X.warn ("editLessonClick")
        //DBH.CEA()
        //val intento1 = Intent(this, ZActivity::class.java)
        val intento1 = Intent(this,Main2Activity::class.java)
        intento1.putExtra("lessonID",selectedLesson.id)
        intento1.putExtra("lessonName",selectedLesson.name)
        startActivityForResult(intento1,RequestCode.EDIT_LESSON )
    }

    fun addLessonClick(view: View){
        X.warn("")
        val intento1 = Intent(this, Main2Activity::class.java)
        startActivityForResult(intento1,RequestCode.ADD_LESSON )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        X.warn ("onActivityResult requestCode: $requestCode  resultCode: $resultCode")

        if(requestCode==RequestCode.ADD_LESSON){
            if(resultCode==Activity.RESULT_OK) {
                val nameL = data?.getStringExtra("NAME")
                val idL = data?.getLongExtra("ID", 0L)
                lesonList.add(KLesson(idL!!, nameL!!))
                myListAdapter.notifyDataSetChanged()
                myList.setItemChecked(myListAdapter.count-1,true)
                myList.setSelection(myListAdapter.count-1)
                X.warn("new lesson ID ${idL.toString()}   name: $nameL")
            }
            else {
                //toast("${getString(R.string.err_leson)}")
            }
        }
        if(requestCode==RequestCode.GOTO_LESSON){
            toast("GOTOLESSON  ")
            // itwill never happen activity without result
        }

        if(requestCode==RequestCode.EDIT_LESSON){
             if(resultCode==Activity.RESULT_OK){
                val nameL =""+ data?.getStringExtra("NAME")
                selectedLesson.name=nameL
                myListAdapter.notifyDataSetChanged()

            }
            //if(resultCode==Activity.RESULT_CANCELED){
            //   toast("Editleson result Canceled ")
             //
            //}

        }


    }

    fun loadLessons(){
        X.warn ("loadLessons  $curLang")
        for(item in DBH.loadLessons()) lesonList.add(item)
        myListAdapter.notifyDataSetChanged()
        myList.setItemChecked(0,true)
        selectedLesson = myList.getItemAtPosition(0) as KLesson


    }

    fun localize(){
        X.warn("start")
         X.warn("fin")
    }


fun getlang():String {

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return resources.getConfiguration().getLocales().get(0).language.toString();
    } else {
        return resources.getConfiguration().locale.language.toString();
    }*/

     val C:Context=applicationContext
     val R:Resources=C.resources
     val K:Configuration=R.configuration
     var L:String=K.locale.language.toString()
     return L


}





    override fun attachBaseContext(newBase: Context) {
        curLang=getCurrentLang(newBase)

     /*   val sharedpreferences = newBase.getSharedPreferences("KPref",Context.MODE_PRIVATE)
        if (sharedpreferences.contains("lang")) {
            curLang=sharedpreferences.getString("lang", "none")
        } else curLang="en"

        val editor = sharedpreferences.edit()
        editor.putString("lang", curLang)
        editor.commit()
        X.warn("ZXXXXXXXXXXXXXXXX   lang  $curLang")*/



        //val lang:String= newBase.getString(R.string.app_lang)
        //X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("$curLang")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }

}

