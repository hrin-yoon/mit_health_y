package com.example.mit_health

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.util.helper.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.join_p
import kotlinx.android.synthetic.main.health_main.*

class health_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_main)

        title_login.setOnClickListener {
            val nextlogin = Intent(this,health_login::class.java)
            startActivity(nextlogin)
        }

        title_signup.setOnClickListener {
            val nextsignup = Intent(this,MainActivity::class.java)
            startActivity(nextsignup)
        }

//        var keyHash = Utility.getKeyHash(this)
//
//        Log.d("KEY_HASH", keyHash)



    }

}