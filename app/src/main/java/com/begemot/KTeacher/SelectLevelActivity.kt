package com.begemot.KTeacher

/**
 * Created by dad on 03/02/2018.
 */
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.begemot.klib.DBHelp
import com.begemot.klib.KHelp
import com.begemot.klib.KLevel
import org.jetbrains.anko.*

class SelectLevelActivity : KBaseActivity() {
    private val X = KHelp(this.javaClass.simpleName)

    /*fun aps(langITeach:String,langMyStudents:String){
        val intent=Intent()
        intent.putExtra("ITEACH",langITeach)
        intent.putExtra("MYSTUDENTS",langMyStudents)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title=resources.getText(R.string.choselevel)
        //SelectLevelView().setContentView(this)
    }

    fun getDataProvider():List<KLevel>{
        return DBHelp.getInstance(this).loadAll<KLevel>()
    }

}
