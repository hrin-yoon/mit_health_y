package com.example.mit_health

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.health_data.*

class health_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_data)

        detail_save.setOnClickListener {
            val nextIntent2 = Intent(this,health_main::class.java)
            startActivity(nextIntent2)
        }

    }
}