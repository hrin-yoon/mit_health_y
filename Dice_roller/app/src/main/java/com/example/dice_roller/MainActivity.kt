package com.example.dice_roller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat

// 카운트업 -> 주사위 그림 바꾸기  -> 코딩 간단화 / UI 추가 ( 스타트 버튼 누르면 시작 ( 창을 새로 만들어야 하나 ?)


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rollButton: Button = findViewById(R.id.roll_button) // 바인딩과의 차이점 -> 개념이해
        rollButton.setOnClickListener { rollDice() }


        val countButton: Button = findViewById(R.id.countup_button)
        countButton.setOnClickListener { countUp() }

        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener { resetDice()}
    }


    private fun rollDice() {

        val randomInt = (1..6).random() // TYPE : INT
        val randomInt2 = (1..6).random()
        val total = randomInt + randomInt2

        val resultText: TextView = findViewById(R.id.result_text)
        resultText.text = randomInt.toString()
        val resultText2: TextView = findViewById(R.id.result_text2)
        resultText2.text = randomInt2.toString()

        val totalDiceText : TextView = findViewById(R.id.total_dice)
        totalDiceText.text = total.toString()

        /////////////////////////// 주사위

        val diceImage: ImageView = findViewById(R.id.imageView)
        // findViewById만 사용하면 지연이 발생할 수 있다 -> view 를 필드에대한 참조를 유지하면 시스템이 언제든지 접근 -> 기능향상

        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        //특정 이미지를 리소스 한다.


        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = randomInt.toString()

        ///////////////////////// 주사위 2

        val diceImage2: ImageView = findViewById(R.id.imageView2)
        val drawableResource2 = when (randomInt2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage2.setImageResource(drawableResource2)
        diceImage2.contentDescription = randomInt2.toString()


    }


    private fun countUp() {

        val resultText: TextView = findViewById(R.id.result_text)
        val resultText2: TextView = findViewById(R.id.result_text2)



        if (resultText.text == "RESULT1") {
            resultText.text = "1"

        } else {

            var resultInt = resultText.text.toString().toInt()


            if (resultInt < 6) {
                resultInt++
                resultText.text = resultInt.toString()

            }
        }

        val countInt = resultText.text.toString().toInt()
        val diceImage: ImageView = findViewById(R.id.imageView)

        val drawableResource = when (countInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = countInt.toString()


        if (resultText2.text == "RESULT2") {
            resultText2.text = "1"
        } else {

            var resultInt = resultText2.text.toString().toInt()

            if (resultInt < 6) {
                resultInt++
                resultText2.text = resultInt.toString()

            }
        }


        val countInt2 = resultText2.text.toString().toInt()
        val diceImage2: ImageView = findViewById(R.id.imageView2)

        val drawableResource2 = when (countInt2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage2.setImageResource(drawableResource2)
        diceImage2.contentDescription = countInt2.toString()


    }


    private fun resetDice() { //주사위 초기화

        val resultText: TextView = findViewById(R.id.result_text)
        var resultInt = resultText.text.toString().toInt()
        val totalDiceText : TextView = findViewById(R.id.total_dice)


        if (resultInt > 0) {
            resultText.text = "RESULT1"
            totalDiceText.text = "TOTAL"
        }

        val diceImage: ImageView = findViewById(R.id.imageView)

        val drawableResource = when (resultInt) {
            1-> R.drawable.dice_1
            else ->  R.drawable.dice_1
        }

        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = resultInt.toString()




        val resultText2: TextView = findViewById(R.id.result_text2)
        var resultInt2 = resultText2.text.toString().toInt()


        if (resultInt2 > 0) {
            resultText2.text = "RESULT2"
        }

        val diceImage2: ImageView = findViewById(R.id.imageView2)

        val drawableResource2 = when (resultInt2) {
            1-> R.drawable.dice_1
            else ->  R.drawable.dice_1
        }

        diceImage2.setImageResource(drawableResource2)
        diceImage2.contentDescription = resultInt2.toString()

    }


}




