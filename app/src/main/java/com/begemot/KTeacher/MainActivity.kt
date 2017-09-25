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
import android.content.DialogInterface
import android.content.SharedPreferences
import android.app.AlarmManager
import android.app.PendingIntent
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
         }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        X.warn("")
        curLang=getCurrentLang(this)

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
        DBHelp.reopen()
        DBH=DBHelp.getInstance(this,curLang)
        loadLessons()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
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

        X.warn("POS old  ${getCurrentLang(this)}  new  $I")
        if(getCurrentLang(this)=="en") if(I==0) return
        if(getCurrentLang(this)=="es") if(I==1) return
        if(I==0) { setCurrentLang("en"); return }
        if(I==1) { setCurrentLang("es"); return }
        X.warn("POS  $I")
    }

    fun APS(){
        /*
//        val mStartActivity = Intent(this@MainActivity, SplashScreen::class.java)
        val mStartActivity = Intent(this,MainActivity::class.java)
        val mPendingIntentId = 123456
        val mPendingIntent = PendingIntent.getActivity(this@MainActivity, mPendingIntentId, mStartActivity,
                PendingIntent.FLAG_CANCEL_CURRENT)
        val mgr = this@MainActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
*/

        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(i)


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
            else {
                toast("${getString(R.string.err_leson)}")
            }
        }
        if(requestCode==RequestCode.GOTO_LESSON){
            //toast("GOTOLESSON  ")
        }


    }

    fun loadLessons(){
        X.warn ("loadLessons  $curLang")
        for(item in DBH.loadLessons()) lesonList.add(item)
        myListAdapter.notifyDataSetChanged()
        myList.setItemChecked(0,true)
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




    fun setCurrentLang(sLang:String){
        val sharedpreferences = getSharedPreferences("KPref",Context.MODE_PRIVATE)
        //if (sharedpreferences.contains("lang")) {
        //    curLang=sharedpreferences.getString("lang", "none")
        //} else curLang="en"
        val editor = sharedpreferences.edit()
        editor.putString("lang", sLang)
        editor.commit()
        X.warn(": current Lang = $curLang  newLang =$sLang")
        APS()

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

