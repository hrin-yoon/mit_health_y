package com.example.health_care

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Health_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_login)

        val button1 : Button = findViewById<Button>(R.id.login_btn)
        val button2 : Button = findViewById(R.id.sign_up_btn)

        button1.setOnClickListener {
            val nextlogin = Intent(this,Health_data::class.java)
            startActivity(nextlogin)
        }
        button2.setOnClickListener {
            val nextsignup = Intent(this,Health_signUp::class.java)
            startActivity(nextsignup)
        }
    }
}