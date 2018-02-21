package com.begemot.klib

/**
 * Created by Dad on 02-Sep-17.
 */
import android.content.Context
import java.util.*

//import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.content.Intent
import android.media.MediaPlayer
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.res.ResourcesCompat.getDrawable
import android.support.v7.app.AlertDialog
import android.text.Spanned


//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.content_main.*
//import com.begemot.KTeacher.R.color.pink
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

const val DEBUG=true

class KT(){
      fun name():String="KT name"
      //fun soundNameFile():String="MYSOUND1.3gp"
      companion object {
            private  val  X = KHelp(this.javaClass.simpleName)

            fun soundNameFile():String="MYSOUND1.3gp"

            fun saveSoundToFile(ctx: Context,soundData:ByteArray){
                //val path = File(Environment.getExternalStorageDirectory().getPath())
                val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
                val archivo = File(path, soundNameFile())

                try {
                    val fos = FileOutputStream(archivo)
                    fos.write(soundData)
                    fos.close()

                } catch (e: IOException) {
                    X.err("Exception creating temp file: ${e.toString()} ")
                }
                X.err("writeappend  datasize ${soundData.size.toString()}")
            }

          fun emptySoundFile(ctx: Context){
              val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
              val archivo = File(path, soundNameFile())
              FileWriter(archivo).close()
              X.err("teoricament deixa l'arxiu a zero")
          }

          fun playSound(ctx: Context){
              val path = ctx.getDir("KDir",Context.MODE_PRIVATE)
              val archivo = File(path, soundNameFile())
              lateinit var player: MediaPlayer
              lateinit var mediaListener:MediaPlayer.OnCompletionListener


              mediaListener=object:MediaPlayer.OnCompletionListener{
                  override fun onCompletion(p0: MediaPlayer?) {
                      p0?.reset()
                      p0?.release()
                  }
              }


              if(archivo.length()==0L){ if(DEBUG)X.warn("-----------EMMPPPTY SOUND FILE"); return }
              try {
                  player = MediaPlayer()
                  player?.setOnCompletionListener(mediaListener)
                  player?.setDataSource(archivo.getAbsolutePath())
                  if(DEBUG)X.warn("length file in Bytes: ${archivo.length().toString()}")

              } catch (e: IOException) {
                  if(DEBUG)X.warn(" no se ha podido detener: ${e.message}")
                  if(DEBUG)X.warn("${e.printStackTrace()}")
              }

              try {
                  player?.prepare()
              } catch (e: IOException) {
                  if(DEBUG)X.warn(" exception en player prepare al reproducir: ${e.message}")
                  if(DEBUG)X.warn("${e.printStackTrace()}")
              }
              player?.start()
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

            fun setCurrentLang(ctx:Context,sLang:String,app:AppCompatActivity){
                  val sharedpreferences = ctx.getSharedPreferences("KPref",Context.MODE_PRIVATE)
                  //if (sharedpreferences.contains("lang")) {
                  //    curLang=sharedpreferences.getString("lang", "none")
                  //} else curLang="en"
                  val editor = sharedpreferences.edit()
                  editor.putString("lang", sLang)
                  editor.commit()
                  //if(DEBUG)X.warn(": current Lang = $curLang  newLang =$sLang")
                  APS2(ctx, app)

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
                                    themedButton(R.style.cat_style2){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
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
                                    themedButton(R.style.cat_style2){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
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
                                    themedButton(R.style.cat_style2){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
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

                                    themedButton(R.style.cat_style2){text=tAlert;background=context.getDrawable(android.R.color.transparent)}.lparams(width = wrapContent) {
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

