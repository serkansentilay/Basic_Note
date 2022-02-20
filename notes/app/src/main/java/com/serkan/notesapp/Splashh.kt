package com.serkan.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splashh : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashh)
        Handler().postDelayed({
            var intent = Intent(this@Splashh,MainActivity::class.java)
            startActivity(intent)
        },1000)
    }
}