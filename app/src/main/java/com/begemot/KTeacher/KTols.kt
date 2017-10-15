package com.begemot.KTeacher

/**
 * Created by Dad on 02-Sep-17.
 */
import android.content.Context
import org.jetbrains.anko.AnkoLogger
import java.util.*
import android.app.Activity

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
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.res.ResourcesCompat.getDrawable
import android.support.v7.app.AlertDialog
import android.text.Spanned


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.*
import com.begemot.KTeacher.R.color.pink
import com.begemot.klib.KHelp
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import java.util.*

const val DEBUG=false

class KT(){
      fun name():String="KT name"
      //fun soundNameFile():String="MYSOUND1.3gp"

      companion object {
            fun soundNameFile():String="MYSOUND1.3gp"

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

            fun setCurrentLang(ctx:Context,sLang:String,app:AppCompatActivity){
                  val sharedpreferences = ctx.getSharedPreferences("KPref",Context.MODE_PRIVATE)
                  //if (sharedpreferences.contains("lang")) {
                  //    curLang=sharedpreferences.getString("lang", "none")
                  //} else curLang="en"
                  val editor = sharedpreferences.edit()
                  editor.putString("lang", sLang)
                  editor.commit()
                  //X.warn(": current Lang = $curLang  newLang =$sLang")
                  APS2(ctx,app)

            }

            fun APS2(ctx:Context,app:AppCompatActivity){
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

                  val i = app.baseContext.packageManager.getLaunchIntentForPackage(app.baseContext.packageName)
                  i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                  app.finish()
                  app.startActivity(i)


            }


            fun showResults(s1:String, s2: Spanned, app:AppCompatActivity,mistakes:Boolean){
                  var tAlert=""
                  if(mistakes) tAlert="ошибки !"
                  else         tAlert="молодец !"
                  app.alert(Appcompat) {
                        customView {
                              verticalLayout {

                                    if(mistakes) background=context.getDrawable(R.color.pink)
                                    else background=context.getDrawable(R.color.green)
                                    themedButton(R.style.cat_style){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
                                          horizontalMargin = dip(0)
                                          padding=dip(1)
                                    }

                                    textView(s1){textSize=19f; padding=dip(8)}
                                    textView(s2){textSize=19f; padding=dip(8)}
                                    //yesButton {  }
                                    padding = dip(3)
                              }
                        }
                  }.show()

            }


            fun gimeADialog(s1:String, s2: Spanned, app:AppCompatActivity,mistakes:Boolean): AlertBuilder<AlertDialog> {
                  var tAlert=""
                  if(mistakes) tAlert="ошибки !"
                  else         tAlert="молодец !"
                  val A=app.alert(Appcompat) {
                        customView {
                              verticalLayout {

                                    if(mistakes) background=context.getDrawable(R.color.pink)
                                    else background=context.getDrawable(R.color.green)
                                    themedButton(R.style.cat_style){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
                                          horizontalMargin = dip(0)
                                          padding=dip(1)
                                    }

                                    textView(s1){textSize=19f; padding=dip(8)}
                                    textView(s2){textSize=19f; padding=dip(8)}
                                    //yesButton {  }
                                    padding = dip(3)
                              }
                        }
                  }
                  return A

            }

            fun  getD(s1:String, s2: Spanned, app:AppCompatActivity,mistakes:Boolean):AlertDialog{
                  var tAlert=""
                  if(mistakes) tAlert="ошибки !"
                  else         tAlert="молодец !"
                  val A=app.alert(Appcompat) {
                        customView {
                              verticalLayout {

                                    if(mistakes) background=context.getDrawable(R.color.pink)
                                    else background=context.getDrawable(R.color.green)
                                    themedButton(R.style.cat_style){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
                                          horizontalMargin = dip(0)
                                          padding=dip(1)
                                    }

                                    textView(s1){textSize=19f; padding=dip(8)}
                                    textView(s2){textSize=19f; padding=dip(8)}
                                    //yesButton {  }
                                    padding = dip(3)
                              }
                        }
                  }
                  return A.show()
            }

            fun  getAlertD(s2: Spanned, app:AppCompatActivity,mistakes:Boolean):AlertDialog{
                  var tAlert=""
                  if(mistakes) tAlert="ошибки !"
                  else         tAlert="молодец !"
                  val A=app.alert(Appcompat) {
                        customView {
                              verticalLayout {

                                    if(mistakes) background=context.getDrawable(R.color.pink)
                                    else background=context.getDrawable(R.color.green)
                                    //themedButton(R.style.cat_style){text=tAlert;background=context.getDrawable(android.R.color.holo_blue_light)}.lparams(width = wrapContent) {

                                    themedButton(R.style.cat_style){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
                                          height= dip(35)
                                          horizontalMargin = dip(10)
                                          padding=dip(1)
                                    }
                                    textView(s2){textSize=20f; horizontalPadding=dip(20); bottomPadding=dip(8)}
                                    //yesButton {  }
                                    padding = dip(0)
                              }
                        }
                  }
                  return A.show()
            }


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

