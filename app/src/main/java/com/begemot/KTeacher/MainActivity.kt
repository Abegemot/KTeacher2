package com.begemot.KTeacher

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
import android.content.SharedPreferences








class MainActivity : AppCompatActivity() {

    private val X = KHelp(this.javaClass.simpleName)
    lateinit var myListAdapter:ArrayAdapter<KLesson>
    lateinit var DBH : DBHelp
    var lesonList = ArrayList<KLesson>()
    lateinit var selectedLesson:KLesson

    class RequestCode {
         companion object {
             val SELECT_KINDOF_EXERCISE :Int = 10
             val GOTO_LESSON :Int = 20
             val ADD_LESSON  :Int = 30
         }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // localize()
        setContentView(R.layout.activity_main)
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

        DBH=DBHelp.getInstance(this)
        loadLessons()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
       // menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun goToLesson(iD:Long){
        X.warn("")
        val intento1 = Intent(this, SelectExerciseActivity::class.java)
        intento1.putExtra("lessonID",selectedLesson.id)
        intento1.putExtra("lessonName",selectedLesson.name)
        startActivityForResult(intento1,RequestCode.GOTO_LESSON )
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
        localize()
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
        }
        if(requestCode==RequestCode.GOTO_LESSON){
            //toast("GOTOLESSON  ")
        }


    }

    fun loadLessons(){
        X.warn ("loadLessons")
        for(item in DBH.loadLessons()) lesonList.add(item)
        myListAdapter.notifyDataSetChanged()
        myList.setItemChecked(0,true)
    }

    fun localize(){
        X.warn("start")
        //val locale = Locale("es")
        //Locale.setDefault(locale)
        //val config = baseContext.resources.configuration
        //config.setLocale(locale)
        //X.warn("language ${locale.displayLanguage}")

        //val locale2 = this.getResources().getConfiguration().setLocale(locale)
        //this.resources.configuration.setLocale(locale)


        //baseContext.resources.configuration
        //baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        //configuration.setLocale(locale)
        //resources.updateConfiguration(configuration, resources.getDisplayMetrics())


         //val locale = Locale("en")
         //Locale.setDefault(locale)

         //val config = Configuration(resources.getConfiguration())
        //if (Build.VERSION.SDK_INT >= 17) {
         //config.setLocale(locale)
         //val  newContext: Context = createConfigurationContext(config)
         //recreate()
        //} else {
            //config.locale = locale
            //res.updateConfiguration(config, res.getDisplayMetrics())
        //}
        //val lang:String=getString(R.string.app_lang)
        //X.warn("XXXXXXXXXXXXXXXX   lang  $lang")

        //X.warn("${getlang()}")
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
        var L:String = ""
        val sharedpreferences = newBase.getSharedPreferences("KPref",Context.MODE_PRIVATE)
        if (sharedpreferences.contains("lang")) {
            L=sharedpreferences.getString("lang", "none")
        } else L="en"
        val editor = sharedpreferences.edit()
        editor.putString("lang", L)
        editor.commit()
        X.warn("ZXXXXXXXXXXXXXXXX   lang  $L")



        val lang:String= newBase.getString(R.string.app_lang)
        X.warn("XXXXXXXXXXXXXXXX   lang  $lang")
        val newLocale= Locale("es")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }

}

class ContextWrapper(base: Context) : android.content.ContextWrapper(base) {
    companion object {

        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context

            val res = context.resources
            val configuration = res.configuration

            /*if (BuildUtils.isAtLeast24Api()) {
                configuration.setLocale(newLocale)

                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.locales = localeList

                context = context.createConfigurationContext(configuration)*/

            //} else if (BuildUtils.isAtLeast17Api()) {
                configuration.setLocale(newLocale)
                context = context.createConfigurationContext(configuration)

           /* } else {
                configuration.locale = newLocale
                res.updateConfiguration(configuration, res.displayMetrics)
            }*/

            return ContextWrapper(context)
        }
    }
}