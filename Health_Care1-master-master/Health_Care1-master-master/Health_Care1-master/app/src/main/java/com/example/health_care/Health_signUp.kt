package com.example.health_care

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import org.apache.kafka.clients.producer.*
import java.sql.DriverManager
import java.util.*
import java.util.concurrent.ExecutionException


class Health_signUp() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_signup)

        val button1: Button = findViewById(R.id.signup_ok)
        val button2: Button = findViewById(R.id.id_check_btn)

        val sign_id: EditText = findViewById(R.id.sign_up_id)
        val sign_pw: EditText = findViewById(R.id.sign_up_pw)

        val UserID = sign_id.text.toString()
        val UserPW = sign_pw.text.toString()

        button1.setOnClickListener {
            val intent = Intent(this, Health_data::class.java)
            Toast.makeText(this,  " 회원가입 완료입니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        button2.setOnClickListener {
            Toast.makeText(this, "아이디 중복 체크", Toast.LENGTH_SHORT).show()
        }

    }


    internal class User() {

        val mc = Health_signUp()

        val sign_id: EditText = mc.findViewById(R.id.sign_up_id)
        val sign_pw: EditText = mc.findViewById(R.id.sign_up_pw)

        internal var UserID = sign_id.text.toString()
        internal var UserPW = sign_pw.text.toString()



    }
}



//class producer {
//
//    companion object {
//        @JvmStatic
//        open fun main(args: Array<String>) {
//
//            val TOPIC_NAME: String = "login"
//            val BOOTSTRAP_SERVERS: String = "localhost:9092"
//
//            val UserID: String = sign_id.getText().toString()
//            val UserPW: String = sign_pw.getText().toString()
//
//            val configs = Properties()
//
//            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
//            configs.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                "org.apache.kafka.common.serialization.StringSerializer"
//            )
//            configs.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                "org.apache.kafka.common.serialization.StringSerializer"
//            )
//
//            val producer = KafkaProducer<String, String>(configs)
//
//            for (index in 0..2) {
//                val data = "This is record " + index
//                val record = ProducerRecord<String, String>(TOPIC_NAME, data)
//
//                try {
//                    val intent = Intent(this, Health_data::class.java)
//                    Toast.makeText(this, "회원가입완료", Toast.LENGTH_SHORT).show()
//                    startActivity(intent)
//
//                    producer.send(record)
//                    DriverManager.println("ID $UserID| Password :$UserPW | data :$data")
//                    Thread.sleep(1000)
//
//
//                } catch (e: Exception) {
//                    println(e)
//                }
//            }
//        }
//    }
//}

//fun producer() {
//
//    val TOPIC_NAME: String = "login"
//    val BOOTSTRAP_SERVERS: String = "localhost:9092"
//
//    val UserID: String = sign_id.getText().toString()
//    val UserPW: String = sign_pw.getText().toString()
//
//    val configs = Properties()
//
//    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
//    configs.put(
//        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//        "org.apache.kafka.common.serialization.StringSerializer"
//    )
//    configs.put(
//        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//        "org.apache.kafka.common.serialization.StringSerializer"
//    )
//
//    val producer = KafkaProducer<String, String>(configs)
//
//    for (index in 0..2) {
//        val data = "This is record " + index
//        val record = ProducerRecord<String, String>(TOPIC_NAME, data)
//
//        try {
//            val intent = Intent(this, Health_data::class.java)
//            Toast.makeText(this, "회원가입완료", Toast.LENGTH_SHORT).show()
//            startActivity(intent)
//
//            producer.send(record)
//            DriverManager.println("ID $UserID| Password :$UserPW | data :$data")
//            Thread.sleep(1000)
//
//
//        } catch (e: Exception) {
//            println(e)
//        }
//    }
//}

//class Health_signUp : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.health_signup)
//
//        val button1: Button = findViewById(R.id.signup_ok)
//        val button2: Button = findViewById(R.id.id_check_btn)
//        var sign_id: EditText = findViewById(R.id.sign_up_id)
//        val sign_pw: EditText = findViewById(R.id.sign_up_pw)
//
//        button1.setOnClickListener {
//            val nextlogin = Intent(this, Health_data::class.java)
//            startActivity(nextlogin)
//        }
//
//        button2.setOnClickListener {
//            Toast.makeText(this, "아이디 중복 체크", Toast.LENGTH_SHORT).show()
//        }
//    }
//}

//class Producer  {
//
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//
//            val TOPIC_NAME : String= "login"
//            val BOOTSTRAP_SERVERS :String = "13.209.87.167:9092"
//
//            val configs = Properties()
//
//            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
//            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
//            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
//
//            val producer = KafkaProducer<String, String>(configs)
//
//            for (index in 0..2) {
//                val data = "This is record " + index
//                val record = ProducerRecord<String, String>(TOPIC_NAME, data)
//
//                try {
//                    producer.send(record)
//                    DriverManager.println("ID " + ID + "| data :" + data)
//                    Thread.sleep(1000)
//                } catch (e: Exception) { println(e) }
//            }
//            for (index in 0..9) {
//                val data = "This is record " + index
//                val record = ProducerRecord<String, String>(TOPIC_NAME, data)
//
//                try {
//                    producer.send(record)
//                    DriverManager.println("Send to " + TOPIC_NAME + "| data :" + data)
//                    Thread.sleep(1000)
//                } catch (e: Exception) { println(e) }
//            }
//        }
//    }
//}