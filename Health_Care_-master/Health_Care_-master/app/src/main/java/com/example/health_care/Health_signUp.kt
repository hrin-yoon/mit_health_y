package com.example.health_care

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import clojure.lang.Compiler
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.sql.DriverManager
import java.util.*


class Health_signUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_signup)

        Clickbtn()

    }

    private fun Clickbtn() {
        val button1: Button = findViewById(R.id.signup_ok)
        button1.setOnClickListener {

            var sign_id: EditText = findViewById(R.id.sign_up_id)
            val sign_pw: EditText = findViewById(R.id.sign_up_pw)

            val UserID = sign_id.text.toString()
            val UserPW = sign_pw.text.toString()

            val intent = Intent(this, Health_data::class.java)
            Toast.makeText(this, UserID + "님 반갑습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)

            producer()
        }

        val button2: Button = findViewById(R.id.id_check_btn)
        button2.setOnClickListener { Toast.makeText(this, "아이디 중복 체크", Toast.LENGTH_SHORT).show() }

    }
    //args: Array<String>
    private fun producer () {

        var sign_id: EditText = findViewById(R.id.sign_up_id)
        val sign_pw: EditText = findViewById(R.id.sign_up_pw)
        val UserID = sign_id.text.toString()
        val UserPW = sign_pw.text.toString()

        val TOPIC_NAME : String= "login"
        val BOOTSTRAP_SERVERS :String = "13.209.87.167:9092" //13.209.87.167

        val configs = Properties()

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

        val producer = KafkaProducer<String, String>(configs)

        for (index in 0..2) {
            val data = "This is record " + index
            val record = ProducerRecord<String, String>(TOPIC_NAME, data)

            try {
                producer.send(record)
                DriverManager.println("Send to " + TOPIC_NAME +"| ID : " + UserID +"| Password : "  + UserPW + "| data :" + data)
                Thread.sleep( 127000)
            } catch (e: Exception) { println(e) }
        }
    }
}



//class Helath_signUp : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(com.example.health_care.R.layout.health_signup)
//
//        //아이디값 찾아주기
//        val join_email : EditText = findViewById(R.id.sign_up_id)
//        val join_password : EditText = findViewById(R.id.sign_up_pw)
//        val join_name : EditText  = findViewById(R.id.name)
//        val join_pwck: EditText   = findViewById(R.id.sign_up_pw_check)
//
//
//        //회원가입 버튼 클릭 시 수행
//        val join_button : Button = findViewById(R.id.signup_ok)
//        join_button.setOnClickListener(View.OnClickListener {
//            val UserEmail = join_email.getText().toString()
//            val UserPwd = join_password.getText().toString()
//            val UserName = join_name.getText().toString()
//            val PassCk = join_pwck.getText().toString()
//
//            fun onResponse(response: String) {
//                try {
//                    //회원가입 성공시
//                    if (UserPwd == PassCk) {
//                        if (success) {
//                            Toast.makeText(
//                                applicationContext,
//                                UserName + "님 가입을 환영합니다.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            val intent: Intent = Intent(this, Health_data::class.java)
//                            startActivity(intent)
//                            //회원가입 실패
//                        } else {
//                            Toast.makeText(applicationContext, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT)
//                                .show()
//                            return
//                        }
//                    } catch (e: Exception) { println(e) }
//                }
//            }
//        }
//    }
//}
//            //아이디 중복체크 했는지 확인
//            if (!validate) {
//                val builder = AlertDialog.Builder(this@RegisterActivity)
//                dialog =
//                    builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create()
//                dialog!!.show()
//                return@OnClickListener
//            }

            //한 칸이라도 입력 안했을 경우
//            if (UserEmail == "" || UserPwd == "" || UserName == "") {
//                val builder = AlertDialog.Builder(this@RegisterActivity)
//                dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create()
//                dialog!!.show()
//                return@OnClickListener
//            }


//        fun onResponse(response: String?) {
//            try {
//                val jsonObject = JSONObject(response)
//                val success = jsonObject.getBoolean("success")
//
//                //회원가입 성공시
//                if (UserPwd == PassCk) {
//                    if (success) {
//                        Toast.makeText(
//                            applicationContext,
//                            String.format("%s님 가입을 환영합니다.", UserName),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val intent = Intent(
//                            this@RegisterActivity,
//                            LoginActivity::class.java
//                        )
//                        startActivity(intent)
//                        //회원가입 실패
//                    } else {
//                        Toast.makeText(
//                            applicationContext,
//                            "회원가입에 실패하였습니다.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        return
//                    }
//                } else {
//                    val builder = AlertDialog.Builder(this@Health_signUp)
//                    dialog =
//                        builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null)
//                            .create()
//                    dialog!!.show()
//                    return
//                }
//            } catch (e: Exception) { println(e) }
