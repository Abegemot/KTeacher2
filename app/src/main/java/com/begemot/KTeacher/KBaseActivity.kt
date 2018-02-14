package com.begemot.KTeacher

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.begemot.klib.KHelp
import java.util.*
/**
 * Created by dad on 02/02/2018.
 */

abstract class KBaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val X = KHelp(this.javaClass.simpleName)
        val curLang=KApp().getLang2(newBase)
        val newLocale= Locale("$curLang")
        X.err("locale: $curLang")
        val context = ContextWrapper2.wrap(newBase, newLocale)
        super.attachBaseContext(context)
    }
}

class ContextWrapper2(base: Context) : android.content.ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, newLocale: Locale): ContextWrapper2 {
            var context = context
            val res = context.resources
            val configuration = res.configuration
            configuration.setLocale(newLocale)
            context = context.createConfigurationContext(configuration)
            return ContextWrapper2(context)
        }
    }
}
