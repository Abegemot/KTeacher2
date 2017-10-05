package com.begemot.KTeacher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.begemot.klib.KHelp
import kotlinx.android.synthetic.main.activity_select_exercise.*
import kotlinx.android.synthetic.main.activity_select_kind_of_ex.*
import java.util.*
import kotlin.reflect.KClass

class SelectKindOfEx : AppCompatActivity() {
    private val X = KHelp(this.javaClass.simpleName)

    lateinit var KOfexercisesListAdapter: ArrayAdapter<KKindOfExercice>
    lateinit var DBH : DBHelp
    var KOfexercisesList = ArrayList<KKindOfExercice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_kind_of_ex)
        setTitle(resources.getString(R.string.sel_kind_ofex))

        KOfexercisesListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,KOfexercisesList)


        myListKindOfExercises.adapter=KOfexercisesListAdapter

        myListKindOfExercises.onItemClickListener = object : AdapterView.OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
            X.warn("onItemClickListener position = $position    id= $id")

            val intentMessage = getIntent()
            val KOF:KKindOfExercice = myListKindOfExercises.getItemAtPosition(position) as KKindOfExercice
            X.warn("SELECTED KOF   $KOF" )
            intentMessage.putExtra("IDKind",KOF.ID )
            setResult(Activity.RESULT_OK,intentMessage)
            finish()



        } }


        DBH=DBHelp.getInstance(this)
        loadKindOfExercises()



    }
    fun loadKindOfExercises(){
        X.warn ("")
/*        val a:List<KKindOfExercice> = DBH.loadKindOfExercises()
        for(item in a)   KOfexercisesList.add(item)
        KOfexercisesListAdapter.notifyDataSetChanged()
*/
        //val KOE: Array<String> = resources.getStringArray(R.array.kind_of_ex)
        val KOE:Array<String> = DBH.getKOE()
        var i=1L
        for(item in KOE){
            KOfexercisesList.add( KKindOfExercice(i++,item,""))
            //KOfexercisesList.add(K)
        }
        KOfexercisesListAdapter.notifyDataSetChanged()

    }

    override fun attachBaseContext(newBase: Context) {
        //curLang=getCurrentLang(newBase)

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
        val newLocale= Locale("${KT.getCurrentLang(newBase)}")

        // .. create or get your new Locale object here.

        val context = ContextWrapper.wrap(newBase, newLocale)
        //X.warn("Current Language:   ${getlang()}")
        super.attachBaseContext(context)
    }


}
