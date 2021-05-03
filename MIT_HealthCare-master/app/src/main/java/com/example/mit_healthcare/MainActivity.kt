package com.example.mit_healthcare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //내가 필요한 걸음수 , 걸은 거리, 걸은 시간, 칼로리 데이터를가져오기 위한 필요한 권한을 정의
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED)
            .addDataType(DataType.TYPE_DISTANCE_DELTA)
            .build()

        // 앱 실행 후 연결 된 구글 계정에 fit 접근 권한이 있는지 확인 한다.
        // 권한이 없다면 권한을 요청하는 팝업을 만들어 조고 권한 요청에 대한 결과 값을  onActivityResult()에서 받는다.
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions)
        } else {
            subscribe()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // 권한 요청 결과 값 받음
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                // Google Fitness store와 데이터 동기화
                subscribe()
            }
        }
    }

    private fun subscribe() {

        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnCompleteListener(
                object: OnCompleteListener<Void>() {
                    fun onComplete(@NonNull task: Task<Void>) {
                        if (task.isSuccessful())
                        {
                            // Successfully subscribed!
                        }
                        else
                        {
                            Log.w("There was a problem subscribing.", task.getException())
                            Log.
                        }
                    }
                })
    }


    private fun readDataMy() {
        val cal = Calendar.getInstance()
        val now = Calendar.getInstance().getTime()
        cal.setTime(now)

        // 시작 시간
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val startTime = cal.getTimeInMillis()
        Log.i("걸음 측정 시작 시간: " + getDate(startTime, "yyyy/MM/dd HH:mm:ss"))

        //종료시간
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59)
        val endTime = cal.getTimeInMillis()
        Log.i("걸음 측정 종료 시간: " + getDate(endTime, "yyyy/MM/dd HH:mm:ss"))

    }


}