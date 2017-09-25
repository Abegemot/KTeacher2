package com.begemot.KTeacher

/**
 * Created by Dad on 02-Sep-17.
 */
import android.content.Context
import org.jetbrains.anko.AnkoLogger
import java.util.*

class KT(){
      fun name():String="KT name"

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

fun getCurrentLang(ctx:Context):String{
      var L:String = ""
      val sharedpreferences = ctx.getSharedPreferences("KPref",Context.MODE_PRIVATE)
      if (sharedpreferences.contains("lang")) {
            L=sharedpreferences.getString("lang", "none")
      } else L="en"
      val editor = sharedpreferences.edit()
      editor.putString("lang", L)
      editor.commit()
      return L
}