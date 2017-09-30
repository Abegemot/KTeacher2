package com.begemot.KTeacher

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edi_text.*
import org.jetbrains.anko.toast


class EdiTextActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edi_text)

        eT1.setText(intent.getStringExtra("TL1"))
        tW1.setText(intent.getStringExtra("TL2"))


        eT1.setBackgroundResource(R.drawable.border_edit_text  )
        eT1.imeOptions = EditorInfo.IME_ACTION_DONE
        eT1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        eT1.setHorizontallyScrolling(false)
        eT1.setLines(4)
        eT1.setSelection(eT1.text.toString().length)
        //attachKeyboardListeners()

        //eT1.text = intent.getStringExtra("TL1")

        /*eT1.setOnEditorActionListener(TextView.OnEditorActionListener() {
            if(actionId==EditorInfo.IME_ACTION_DONE){
                //do something
                return true
            }
            return false
        }*/

        eT1.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val intent = Intent()
                intent.putExtra("TL1", eT1.text.toString())
                setResult(Activity.RESULT_OK, intent)
                //else setResult(Activity.RESULT_CANCELED, intent)
                finish()

                true
            } else {
                toast("hola")
                false
            }
        }



    }
  override fun onBackPressed(){
      finish()
  }

// override fun onHideKeyboard(){
//     finish()
//   }


}
