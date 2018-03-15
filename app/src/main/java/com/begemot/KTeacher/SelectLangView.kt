package com.begemot.KTeacher

import android.view.Gravity
import com.begemot.klib.KHelp
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by dad on 29/01/2018.
 */

class SelectLangView : AnkoComponent<SelectLangActivity> {
    private val X = KHelp(this.javaClass.simpleName)
    //val llang= listOf("en","es","ru","zh")
    //var ll   : Array<String> = resources.getStringArray()
    val llang = KApp.llang
    var languageITeach     = 3
    var languageMyStudents = 3


    override fun createView(ui: AnkoContext<SelectLangActivity>)= with(ui) {
        val ll=resources.getStringArray(R.array.languages)
        val sizel=ll.size
        val edit=true
        if (edit) {
              languageITeach = Kprefs.sLangIteach
              languageMyStudents = Kprefs.sLangmyStudents
        }
        //toast("languageITeach $languageITeach  languageMyStudents $languageMyStudents")

        verticalLayout{

            lparams(width= matchParent,height = matchParent)
            textView{
                text=resources.getText(R.string.langtoteach)
                textSize=25f
            }.lparams(){margin=dip(10)}

            val rgLangToTeach=radioGroup {

                var n=0
                ll.forEach {
                    radioButton {
                        id=n
                        text = it.toString()
                        if (id==languageITeach){
                              setChecked(true)
                        }
                        onClick {
                             languageITeach=id
                        }
                    }.lparams(){leftPadding=dip(20)}
                    n++
                }
            }

            textView{
                text=resources.getText(R.string.langstudents)
                textSize=25f
            }.lparams(){margin=dip(10)}

           val rgLangStudents=radioGroup {
                var n=0
                ll.forEach {
                    radioButton {
                        id=n+sizel
                        tag=n
                        text = it.toString()
                        //if (id-sizel==languageMyStudents){
                        if (tag as Int==languageMyStudents){
                            setChecked(true)
                            //X.err("Student $text    ${id-sizel}")
                        }

                        onClick {
                            //languageMyStudents=id-sizel
                            languageMyStudents=tag as Int
                           // setChecked(true)
                           // toast(" students selected ")
                        }
                    }.lparams(){leftPadding=dip(20)}
                    n++
                }
            }



            linearLayout(){
                this.gravity=Gravity.CENTER_HORIZONTAL
                button("OK"){
                    onClick {
                        val i=rgLangToTeach.checkedRadioButtonId
                        val j=rgLangStudents.checkedRadioButtonId-sizel
                        X.err("i $i  j  $j")
                        if (i==j){
                            alert(R.string.noequallang ){ yesButton {  } }.show()
                            return@onClick
                        }
                        if( i<0 || j<0 ){
                            alert(R.string.bothlang ){ yesButton {  } }.show()
                            return@onClick
                        }
                        X.err(" pasa  i $i  j  $j")
                        if (Kprefs.sLangIteach!=i || Kprefs.sLangmyStudents!=j) {
                            Kprefs.sLangIteach=i
                            Kprefs.sLangmyStudents=j
                            ui.owner.aps(llang[i], llang[j])
                        }else ui.owner.finish()



                        //toast("LangToTeach:$i  LangStudents:$j ")

                    }
                }.lparams(){margin=dip(25)}
                /*button("Cancel"){
                    onClick { toast("Hola patata") }
                }.lparams(){margin=dip(25)}*/

            }
            //X.err("langiageITeach: $languageITeach  languageMyStudents: $languageMyStudents")
        }

    }



}