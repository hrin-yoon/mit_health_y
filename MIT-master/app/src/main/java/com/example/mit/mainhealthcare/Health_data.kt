package com.example.mit.mainhealthcare


import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mit.GoogleFit.StepCounter
import com.example.mit.R
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException


const val TAG = "StepCounter"

enum class FitActionRequestCode {
    SUBSCRIBE,
    READ_DATA
}


class Health_data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_data)


        // id 입력값 받아서 상세정보 창에 출력
//        val id_d =intent.getStringExtra("ID")
//
//        var intent = getIntent()
//        var data = intent.getStringExtra("ID")
//
//        var text : TextView = findViewById(R.id.ID_data)
//        text.setText(" 아이디 : "+id_d)

        val button: Button = findViewById(R.id.button)

        val T_birth : TextView = findViewById(R.id.BIRTH_data)
        val T_id : TextView = findViewById(R.id.ID_data)
        val T_name : TextView = findViewById(R.id.NAME_data)
        val T_gender : TextView = findViewById(R.id.GENDER_data)

        if(intent.hasExtra("ID")) {

            val a = intent.getStringExtra("ID")
            //print(a)
            T_id.setText("아이디  :   "+intent.getStringExtra("ID"))

        }

        if(intent.hasExtra("BIRTH")) {

            val b = intent.getStringExtra("BIRTH")
            print(b)
            T_birth.setText("생년월일  :   "+intent.getStringExtra("BIRTH"))
        }

        if(intent.hasExtra("NAME")) {

            val c = intent.getStringExtra("NAME")
            print(c)
            T_name.setText("이름  :   "+intent.getStringExtra("NAME"))
        }

        if(intent.hasExtra("GENDER")) {

            val d = intent.getStringExtra("GENDER")
            print(d)
            T_gender.setText("성별  :   "+intent.getStringExtra("GENDER"))
        }



        button.setOnClickListener {

            val ID = intent.getStringExtra("ID")
            Log.e(TAG, "ID : $ID")

            val height: EditText = findViewById(R.id.height)
            val weight: EditText = findViewById(R.id.weight)

            val name_data: TextView = findViewById(R.id.NAME_data)
            val id_data: TextView = findViewById(R.id.ID_data)
            val birth_data: TextView = findViewById(R.id.BIRTH_data)

            // 키와 무게 입력값 형변환
            val HEIGHT = height.text.toString()
            val WEIGHT = weight.text.toString()

            connect("$ID", "$HEIGHT", "$WEIGHT")

        }

    }

    private fun connect(ID: String, HEIGHT: String, WEIGHT: String) {

        //이 부분 없으면 오류 이유 파익 x
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val jdbcURL = "jdbc:postgresql://192.168.0.2:5432/server" //서버 주소
        val username = "postgres" // 유저 이름
        val password = "281328"// 비번

        try {
            val connection = DriverManager.getConnection(jdbcURL, username, password) //연결한다,
            println("Connected to PostgreSQL server")

            /** 입력 */
            // 쿼리에 입력한다.
            var sql = "INSERT INTO information (아이디, 신장, 몸무게)" + " VALUES (?,?,?)"

            //"INSERT IGNORE INTO data (아이디, 신장, 몸무게) VALUES ('$ID', '$HEIGHT','$WEIGHT') " +
            // "ON DUPLICATE KEY UPDATE 신장='$HEIGHT', 몸무게='$WEIGHT'"

            val statement: PreparedStatement = connection.prepareStatement(sql)

            // 이 값을 테이블에 넣음
            statement.setString(1, "$ID")
            statement.setString(2, "$HEIGHT")
            statement.setString(3, "$WEIGHT")

            val rows = statement.executeUpdate()



            if (rows > 0) {
                println("A new contact has been inserted.")
                val intent = Intent(this, StepCounter::class.java)
                intent.putExtra("ID", ID)
                Toast.makeText(this, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            connection.close()

        } catch (e: SQLException) {
            println("Error in connected to PostgreSQL server")
            e.printStackTrace()
            Toast.makeText(this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}


//            val sql1 = "SELECT 신장 FROM data WHERE 아이디 = '$ID'"
//            val statement1 = connection.createStatement()
//            val result = statement1.executeQuery(sql1)
//
//            while (result.next()) {
//                val height = result.getString("신장")
//                //System.out.print("패스워드 : $password")
//
//                println("키입니당 : $height")
//
//                if (height != null) {
//                    val intent = Intent(this, StepCounter::class.java)
//                    Toast.makeText(this, "이미 등록되었습니다.", Toast.LENGTH_SHORT).show()
//                    intent.putExtra("ID", ID)
//                    startActivity(intent)
//                } else { }
//
