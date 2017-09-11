package com.begemot.kteacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main3.*
import org.jetbrains.anko.*
import com.begemot.klib.*

class   Main3Activity : AppCompatActivity() {
    //private val log = AnkoLogger("MYPOS")
    private val X = KHelp("${this.javaClass.simpleName}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)
        X.warn("constructor")
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        if (savedInstanceState == null) {
            X.warn("begintransaction")
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(Main3ActivityFragment(),"fragment_main3")
                    //  .add(R.layout.activity_main3, Main3ActivityFragment(), "fragment_main3")
                    .commit();
            X.warn("endtransaction   XWARN")

        }
    }

}
