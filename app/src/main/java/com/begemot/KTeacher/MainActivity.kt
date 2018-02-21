package com.begemot.KTeacher

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.AdapterView.OnItemClickListener
import android.content.Intent


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.*
import org.jetbrains.anko.*

import java.util.*
//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale
import android.content.DialogInterface
import com.begemot.klib.*
import com.begemot.klib.DBHelp

//import java.awt.SplashScreen

class MainActivity : KBaseActivity() {
    private val X = KHelp(this.javaClass.simpleName)
    lateinit var DBH : DBHelp
    var curLang=""
    private class RequestCode {
         companion object {
             val CHANGE_LANG :Int = 60
         }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!KApp().initialized(this)){
            startActivityForResult<SelectLangActivity>(RequestCode.CHANGE_LANG)
        }
        curLang=KApp().getLang()
        X.err("currlang : $curLang")

        //startActivityForResult<SelectLevelActivity>(RequestCode.SELECT_LEVEL)

        //setContentView(R.layout.activity_main)
        //var stitle=curLang+" KLessons"
        //act.title=stitle
       // setTitle(resources.getString(R.string.klessons))

        SelectLevelView().setContentView(this)
        title=resources.getText(R.string.choselevel)
        supportActionBar?.setIcon(R.drawable.ic_unionjack)

      //  setSupportActionBar(toolbar)
      //  this.supportActionBar?.setTitle(R.string.choselevel)
      //  this.supportActionBar?.setDisplayShowTitleEnabled(true)
      //  supportActionBar?.setDisplayShowHomeEnabled(true)


        DBHelp.reopen(curLang)
        DBH=DBHelp.getInstance(this)
        DBH.createEX1Examples(this)

    }

    fun getDataProvider():List<KLevel>{
        return DBHelp.getInstance(this).loadAll<KLevel>()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.langmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(DEBUG)X.warn("")
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.select_language->consume{ selectLanguage()}
            R.id.select_language2->{ startActivity<DictionaryActivity>(); return true}
            R.id.menusubjects->{startActivity<SubjectsActivity>();return true}
            else -> super.onOptionsItemSelected(item)
        }
    }
    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }

    fun selectLanguage():Boolean{
       startActivityForResult<SelectLangActivity>(RequestCode.CHANGE_LANG)
      return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==RequestCode.CHANGE_LANG){
            when(resultCode){
                 Activity.RESULT_OK-> {
                     toast("CHANGE LANG !!! ITEACH ${data?.getStringExtra("ITEACH")} MYSTUDENTS ${data?.getStringExtra("MYSTUDENTS")} ")
                     KApp().APS3(this)
                 }
                 else ->toast("NO CHANGE LANG !!! ")
            }

        }

    }



}

