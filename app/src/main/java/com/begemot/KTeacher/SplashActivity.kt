package com.begemot.KTeacher


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
