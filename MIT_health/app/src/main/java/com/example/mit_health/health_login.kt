package com.example.mit_health

import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.health_login.*


class health_login: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_login)

        login_p.setOnClickListener {
            val nextdata = Intent(this, health_data::class.java)
            startActivity(nextdata)
        }



        }

    }




















