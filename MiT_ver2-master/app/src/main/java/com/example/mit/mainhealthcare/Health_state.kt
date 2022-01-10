package com.example.mit.mainhealthcare

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mit.R
import com.example.mit.VitalSign.logger.Log
import com.example.mit.databinding.ActivityHealthStateBinding
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.*

const val tag = "state"

class Health_state : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_state)

        val state_1 : Button = findViewById(R.id.s_button1)
        val state_2 : Button = findViewById(R.id.s_button2)
        val state_3 : Button = findViewById(R.id.s_button3)
        val state_4 : Button = findViewById(R.id.s_button4)
        val state_5 : Button = findViewById(R.id.s_button5)
        val state_6 : Button = findViewById(R.id.s_button6)
        val state_7 : Button = findViewById(R.id.s_button7)
        val state_ok : Button = findViewById(R.id.s_button_ok)


        //날짜 및 시간 형식 지정
        //날짜 및 시간 형식 지정
        val simpleDateFormat = SimpleDateFormat("[ yy-MM-dd E HH:mm:ss ]")

        //Date 객체 사용

        //Date 객체 사용
        val date = Date()
        val time1: String = simpleDateFormat.format(date)

        //Calendar 클래스의 getTime()함수 사용

        //Calendar 클래스의 getTime()함수 사용
        val calendar: Calendar = Calendar.getInstance()
        val time2: String = simpleDateFormat.format(calendar.getTime())

        //System 클래스의 currentTimeMillis()함수 사용

        //System 클래스의 currentTimeMillis()함수 사용
        val time3: String = simpleDateFormat.format(System.currentTimeMillis())

        Log.d("time", "time1 : $time1")
        Log.d("time", "time2 : $time2")
        Log.d("time", "time3 : $time3")



        val s1 = state_1.setOnClickListener {
            println("환복중 "+" $time1")
        }


       val s2 =  state_2.setOnClickListener {
            println("대기실 착석" +" $time1")

        }

        val s3 = state_3.setOnClickListener {
            println("수술실 입실 "+" $time1")
        }

        val s4 =  state_4.setOnClickListener {
            println("소독 시작 "+" $time1")
        }

        val s5 = state_5.setOnClickListener {
            println("주입술 시작 "+" $time1")
        }

        val s6 = state_6.setOnClickListener {
            println("수술/시술 마무리 "+" $time1")
        }

        val s7 = state_7.setOnClickListener {
            println("퇴실 "+" $time1")
        }

        state_ok.setOnClickListener {
            val intent1 = Intent(this, Health_scroll::class.java)
            startActivity(intent1)
        }

        val ID = intent.getStringExtra("ID")
        println( "아이디 : $ID")









    }


//    private fun connect(ID: String,
//                        S1 : String , S2 : String, S3 : String, S4 : String, S5 : String, S6 : String, S7 : String) {
//
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//
//        val jdbcURL = "jdbc:postgresql://203.255.56.25:5432/postgres"
//        val username = "postgres"
//        val password = "postgressselab0812"
//
//        try {
//            val connection = DriverManager.getConnection(jdbcURL, username, password) //연결한다,
//            println("Connected to PostgreSQL server")
//
//            // 데이터베이스에 키, 몸무게의 값을 업데이트(수정)하는 코드
//            var sql = "INSERT INTO state (user_id ,S1, S2, S3, S4, S5, S6 , S7)"+ " VALUES (?,?,?,?,?,?,?,?)"
//
//
//            val statement: PreparedStatement = connection.prepareStatement(sql)
//            statement.executeUpdate()
//
//            if (ID != null) {
//                println("A new contact has been inserted.")
//                Toast.makeText(this, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
//            }
//            //연결을 끊어준다.
//            connection.close()
//
//        } catch (e: SQLException) {
//            println("Error in connected to PostgreSQL server")
//            e.printStackTrace()
//            Toast.makeText(this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
//        }
//    }

}