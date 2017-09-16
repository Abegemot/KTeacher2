package com.begemot.KTeacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.AdapterView.OnItemClickListener
import android.content.Intent


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.*
import com.begemot.klib.KHelp
import org.jetbrains.anko.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val X = KHelp(this.javaClass.simpleName)
    lateinit var myListAdapter:ArrayAdapter<KLesson>
    lateinit var DBH : DBHelp
    var lesonList = ArrayList<KLesson>()
    lateinit var selectedLesson:KLesson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localize()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        myListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,lesonList)

        myList.adapter=myListAdapter
        myList.onItemClickListener = object : OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                X.warn("onItemClickListener position = $position    id= $id")

                selectedLesson = myList.getItemAtPosition(position) as KLesson



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

        startActivityForResult(intento1,2 )
        //val intento1 = Intent(this, Main3Activity::class.java)
        //startActivityForResult(intento1,2 )

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
        DBH.CEA()
    }

    fun addLessonClick(view: View){
        X.warn("")
        val intento1 = Intent(this, Main2Activity::class.java)
        startActivityForResult(intento1,2 )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        X.warn ("onActivityResult")
        if (resultCode === 2) {
            // fetch the message String
            val message = data?.getStringExtra("MESSAGE")
            // Set the message string in textView
           // toast(message)
            //lesonList.add(KLesson(message!!,33))
            lesonList.add(KLesson(33,message!!))
            myListAdapter.notifyDataSetChanged()
           //   toast("XXXXXactivity result  Code : $message")
        }else{
            toast("Lesson could not have been inserted ")
        }




    }

    fun loadLessons(){
        X.warn ("loadLessons")
        /*var a:List<KLesson> = DBH.loadLessons()
        for(item in a) {
            lesonList.add(item)
        }*/
        for(item in DBH.loadLessons()) lesonList.add(item)
        myListAdapter.notifyDataSetChanged()
        myList.setItemChecked(0,true)
    }

    fun localize(){
        X.warn("start")
        val locale = Locale("es")
        //Locale.setDefault(locale)
        //val config = baseContext.resources.configuration
        //config.setLocale(locale)
        X.warn("language ${locale.displayLanguage}")

        //val locale2 = this.getResources().getConfiguration().setLocale(locale)
        this.resources.configuration.setLocale(locale)

        X.warn("fin")
        //baseContext.resources.configuration
        //baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)

    }


}
