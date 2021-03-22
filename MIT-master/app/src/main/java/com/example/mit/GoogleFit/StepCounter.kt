package com.example.mit.GoogleFit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.TextViewCompat
import com.example.mit.BuildConfig
//import com.example.mit.GoogleFit.logger.Log
import com.example.mit.GoogleFit.logger.LogView
import com.example.mit.GoogleFit.logger.LogWrapper
import com.example.mit.GoogleFit.logger.MessageOnlyLogFilter
import com.example.mit.R
import com.example.mit.mainhealthcare.Health_main
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.sum
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit


const val TAG = "StepCounter"
enum class FitActionRequestCode { SUBSCRIBE, READ_DATA }

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DUPLICATE_LABEL_IN_WHEN")
class StepCounter : AppCompatActivity() {

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
        .build()

    private val runningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stepcounter)

        initializeLogging()
        checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)
    }

    private fun checkPermissionsAndRun(fitActionRequestCode: FitActionRequestCode) {
        if (permissionApproved()) {
            fitSignIn(fitActionRequestCode)
        } else {
            requestRuntimePermissions(fitActionRequestCode)
        }
    }

    private fun fitSignIn(requestCode: FitActionRequestCode) {
        if (oAuthPermissionsApproved()) {
            performActionForRequestCode(requestCode)
        } else {
            requestCode.let {
                GoogleSignIn.requestPermissions(
                    this,
                    requestCode.ordinal,
                    getGoogleAccount(), fitnessOptions
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                val postSignInAction = FitActionRequestCode.values()[requestCode]
                performActionForRequestCode(postSignInAction)
            }
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }

    private fun performActionForRequestCode(requestCode: FitActionRequestCode) =
        when (requestCode) {
            FitActionRequestCode.READ_DATA -> readData()
            //FitActionRequestCode.READ_DATA -> historyAPI()
            FitActionRequestCode.SUBSCRIBE -> subscribe()
        }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
        Log.e(TAG, message)
    }

    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(
        getGoogleAccount(),
        fitnessOptions
    )


    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

    private fun subscribe() {

        Fitness.getRecordingClient(this, getGoogleAccount())
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "Successfully subscribed!")
                } else {
                    Log.w(TAG, "There was a problem subscribing.", task.exception)
                }
            }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i(TAG,"Data point:")
            Log.i(TAG,"\tType: ${dp.dataType.name}")
            Log.i(TAG,"\tStart: ${dp.getStartTimeString()}")
            Log.i(TAG,"\tEnd: ${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i(TAG,"\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }
        }
    }

    private fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    private fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()



    private fun readData() {

        val data_list = mutableListOf<Int>()

        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val startTime = endTime.minusWeeks(1)
        Log.i(TAG, "Range Start: $startTime")
        Log.i(TAG, "Range End: $endTime")

        //val data_list = ArrayList<Int>()

        //오늘 걸음 수
        Fitness.getHistoryClient(this, getGoogleAccount())
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { dataSet ->
                var today = when {
                    dataSet.isEmpty -> 0
                    else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                }


                //var total_step : Int = sum()

                var text7 : TextView = findViewById(R.id.textView7)
                text7.setText("오늘 걸음 수 : $today")
                Log.i(TAG, "Today steps: $today")

                ////
                //var text8 : TextView = findViewById(R.id.textView8)
                // text8.setText("7일 총걸음 수 : $total_step ")

                val ID = intent.getStringExtra("ID")
                var today_String = today.toString()
                //connect("$ID", "$total_String")

                var text6 : TextView = findViewById(R.id.textView6)

                val readRequest =
            DataReadRequest.Builder()
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)

                    val hit_total = when {
                        dataSet.isEmpty -> 0
                        else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
                    }
                    Log.i(TAG, "과거 걸음 수 : $hit_total")

                    data_list.add(hit_total)
                }
                println(data_list)

                var text : TextView = findViewById(R.id.textView6)
                text.setText("[7일 걸음 기록]"+"\n"+" 6일전 : " + data_list[1]+" / 5일전 : " + data_list[2]+"\n"+" 4일전 : "
                                + data_list[3]+" / 3일전 : " + data_list[4]+"\n"+" 2일전 : "
                                + data_list[5]+" / 1일전 : " + data_list[6])

                val lineChart : LineChart = findViewById(R.id.Chart)
                val visitors = ArrayList<Entry>()

                visitors.add(Entry(1.0f, data_list[0].toFloat()))
                visitors.add(Entry(2.0f, data_list[1].toFloat()))
                visitors.add(Entry(3.0f, data_list[2].toFloat()))
                visitors.add(Entry(4.0f, data_list[3].toFloat()))
                visitors.add(Entry(5.0f, data_list[4].toFloat()))
                visitors.add(Entry(6.0f, data_list[5].toFloat()))
                visitors.add(Entry(7.0f, data_list[6].toFloat()))
                visitors.add(Entry(8.0f, today.toFloat()))

                println("=========$data_list")

                val lineDataSet = LineDataSet(visitors, "7일 전부터 오늘의 걸음 수 그래프")

                lineDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
                lineDataSet.valueTextColor = Color.BLACK
                lineDataSet.valueTextSize = 16f

                val lineData = LineData(lineDataSet)

                lineChart.data = lineData
                lineChart.invalidate()
                // barChart.description.text = "Bar Chart Example"
                lineChart.animateY(2000)

            }
            .addOnFailureListener { e ->
                Log.w(TAG,"There was an error reading data from Google Fit", e)
            }


            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem getting the step count.", e)
            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the main; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_read_data) {
            fitSignIn(FitActionRequestCode.READ_DATA)
            return true
        }
        if (id == R.id.action_logout) {
            val logout_intent = Intent(this, Health_main::class.java)
            Toast.makeText(this, "로그아웃을 누르셨습니다.", Toast.LENGTH_SHORT)
            startActivity(logout_intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeLogging() {
        val logWrapper = LogWrapper()
        val msgFilter = MessageOnlyLogFilter()

        logWrapper.next = msgFilter

        val logView = findViewById<View>(R.id.sample_logview) as LogView
        TextViewCompat.setTextAppearance(logView, R.style.Log)
        logView.setBackgroundColor(Color.WHITE)
        msgFilter.next = logView
        Log.i(TAG, "Ready")
    }

    private fun permissionApproved(): Boolean {
        return if (runningQOrLater) {
            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else {
            true
        }
    }

    private fun requestRuntimePermissions(requestCode: FitActionRequestCode) {
        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        requestCode.let {
            if (shouldProvideRationale) {
                Log.i(TAG, "Displaying permission rationale to provide additional context.")
                Snackbar.make(
                    findViewById(R.id.main_activity_view),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.ok) {
                        // Request permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                            requestCode.ordinal
                        )
                    }
                    .show()
            } else {
                Log.i(TAG, "Requesting permission")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    requestCode.ordinal
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when {
            grantResults.isEmpty() -> {

                Log.i(TAG, "User interaction was cancelled.")
            }
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                val fitActionRequestCode = FitActionRequestCode.values()[requestCode]
                fitActionRequestCode.let {
                    fitSignIn(fitActionRequestCode)
                }
            }
            else -> {
                Snackbar.make(
                    findViewById(R.id.main_activity_view),
                    R.string.permission_denied_explanation,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.settings) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }

    private fun connect(ID: String, STEP: String) {

        //이 부분 없으면 오류 이유 파익 x
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val jdbcURL = "jdbc:postgresql://192.168.0.4:5432/server" //서버 주소
        val username = "postgres" // 유저 이름
        val password = "150526" // 비번

        try {
            val connection = DriverManager.getConnection(jdbcURL, username, password) //연결한다,
            println("Connected to PostgreSQL server")

            /** 입력 */
            // 쿼리에 입력한다.
            var sql = "UPDATE data SET 걸음 = '$STEP' WHERE 아이디='$ID'"

            val statement: PreparedStatement = connection.prepareStatement(sql)

            // 이 값을 테이블에 넣음
            //statement.setString(1, "$STEP")

            val rows = statement.executeUpdate()

            if (rows > 0) {
                println("A new contact has been inserted.")
                intent.putExtra("ID", ID)
                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
                println("불러온 걸음 수 : $STEP")
            }
            connection.close()

        } catch (e: SQLException) {
            println("데이터 저장 실패")
            e.printStackTrace()
            Toast.makeText(this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}


//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Color
//import android.net.Uri
//import android.os.Bundle
//import android.os.StrictMode
//import android.provider.Settings
//import android.util.Log
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.widget.TextViewCompat
//import com.example.mit.BuildConfig
//import com.example.mit.GoogleFit.logger.LogView
//import com.example.mit.GoogleFit.logger.LogWrapper
//import com.example.mit.GoogleFit.logger.MessageOnlyLogFilter
//import com.example.mit.R
//import com.example.mit.mainhealthcare.Health_main
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//
//import com.github.mikephil.charting.utils.ColorTemplate
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.fitness.Fitness
//import com.google.android.gms.fitness.FitnessOptions
//import com.google.android.gms.fitness.data.DataPoint
//import com.google.android.gms.fitness.data.DataSet
//import com.google.android.gms.fitness.data.DataType
//import com.google.android.gms.fitness.data.Field
//import com.google.android.gms.fitness.request.DataReadRequest
//import com.google.android.material.snackbar.Snackbar
//import java.sql.DriverManager
//import java.sql.PreparedStatement
//import java.sql.SQLException
//import java.time.Instant
//import java.time.LocalDateTime
//import java.time.ZoneId
//import java.util.*
//import java.util.concurrent.TimeUnit
//
//
//const val TAG = "StepCounter"
//
//enum class FitActionRequestCode {
//    SUBSCRIBE,
//    READ_DATA
//}
//
//@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//class StepCounter : AppCompatActivity() {
//    private val fitnessOptions = FitnessOptions.builder()
//            .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
//            .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
//            .build()
//
//    private val runningQOrLater =
//            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_stepcounter)
//
//        val button : Button = findViewById(R.id.button2)
//
//        button.setOnClickListener{
//            initializeLogging()
//            checkPermissionsAndRun(FitActionRequestCode.SUBSCRIBE)
//        }
//    }
//
//    private fun checkPermissionsAndRun(fitActionRequestCode: FitActionRequestCode) {
//        if (permissionApproved()) {
//            fitSignIn(fitActionRequestCode)
//        } else {
//            requestRuntimePermissions(fitActionRequestCode)
//        }
//    }
//
//
//    private fun fitSignIn(requestCode: FitActionRequestCode) {
//        if (oAuthPermissionsApproved()) {
//            performActionForRequestCode(requestCode)
//        } else {
//            requestCode.let {
//                GoogleSignIn.requestPermissions(
//                        this,
//                        requestCode.ordinal,
//                        getGoogleAccount(), fitnessOptions
//                )
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (resultCode) {
//            RESULT_OK -> {
//                val postSignInAction = FitActionRequestCode.values()[requestCode]
//                performActionForRequestCode(postSignInAction)
//            }
//            else -> oAuthErrorMsg(requestCode, resultCode)
//        }
//    }
//
//    private fun performActionForRequestCode(requestCode: FitActionRequestCode) =
//            when (requestCode) {
//                FitActionRequestCode.READ_DATA -> readData()
//                FitActionRequestCode.SUBSCRIBE -> subscribe()
//            }
//
//    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
//        val message = """
//            There was an error signing into Fit. Check the troubleshooting section of the README
//            for potential issues.
//            Request code was: $requestCode
//            Result code was: $resultCode
//        """.trimIndent()
//        Log.e(TAG, message)
//    }
//
//    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(
//            getGoogleAccount(),
//            fitnessOptions
//    )
//
//
//    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
//
//    private fun subscribe() {
//
//        Fitness.getRecordingClient(this, getGoogleAccount())
//                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.i(TAG, "Successfully subscribed!")
//                    } else {
//                        Log.w(TAG, "There was a problem subscribing.", task.exception)
//                    }
//                }
//    }
//
//
//    private fun readData() {
//        Fitness.getHistoryClient(this, getGoogleAccount())
//                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
//                .addOnSuccessListener { dataSet ->
//                    val total = when {
//                        dataSet.isEmpty -> 0
//                        else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
//                    }
//                    var text : TextView = findViewById(R.id.textView7)
//                    text.setText("총 걸음 수 : $total")
//                    Log.i(TAG, "Total steps: $total")
//
//                    val ID = intent.getStringExtra("ID")
//
//                    var total_String = total.toString()
//
//                    //connect("$ID", "$total_String")
//
//                    // 차트
//                    val barChart = findViewById<BarChart>(R.id.barChart)
//                    val visitors = ArrayList<BarEntry>()
//
//
//                    visitors.add(BarEntry(16.0f, total.toFloat())) // (8, 400)
//
//                    val barDataSet = BarDataSet(visitors, "Visitors")
//
//                    barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
//                    barDataSet.valueTextColor = Color.BLACK
//                    barDataSet.valueTextSize = 16f
//
//                    val barData = BarData(barDataSet)
//
//                    barChart.setFitBars(true)
//                    barChart.data = barData
//                    // barChart.description.text = "Bar Chart Example"
//                    barChart.animateY(2000)
//                    //여기까지
//
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "There was a problem getting the step count.", e)
//                }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the main; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.action_read_data) {
//            fitSignIn(FitActionRequestCode.READ_DATA)
//            return true
//        }
//        if (id == R.id.action_logout) {
//            val logout_intent = Intent(this, Health_main::class.java)
//            Toast.makeText(this, "로그아웃을 누르셨습니다.", Toast.LENGTH_SHORT)
//            startActivity(logout_intent)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun initializeLogging() {
//        val logWrapper = LogWrapper()
//        val msgFilter = MessageOnlyLogFilter()
//
//        logWrapper.next = msgFilter
//
//        val logView = findViewById<View>(R.id.sample_logview) as LogView
//        TextViewCompat.setTextAppearance(logView, R.style.Log)
//        logView.setBackgroundColor(Color.WHITE)
//        msgFilter.next = logView
//        Log.i(TAG, "Ready")
//    }
//
//    private fun permissionApproved(): Boolean {
//        return if (runningQOrLater) {
//            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACTIVITY_RECOGNITION
//            )
//        } else {
//            true
//        }
//    }
//
//    private fun requestRuntimePermissions(requestCode: FitActionRequestCode) {
//        val shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.ACTIVITY_RECOGNITION
//                )
//
//        requestCode.let {
//            if (shouldProvideRationale) {
//                Log.i(TAG, "Displaying permission rationale to provide additional context.")
//                Snackbar.make(
//                        findViewById(R.id.main_activity_view),
//                        R.string.permission_rationale,
//                        Snackbar.LENGTH_INDEFINITE
//                )
//                        .setAction(R.string.ok) {
//                            // Request permission
//                            ActivityCompat.requestPermissions(
//                                    this,
//                                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
//                                    requestCode.ordinal
//                            )
//                        }
//                        .show()
//            } else {
//                Log.i(TAG, "Requesting permission")
//                ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
//                        requestCode.ordinal
//                )
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//            requestCode: Int, permissions: Array<String>,
//            grantResults: IntArray
//    ) {
//        when {
//            grantResults.isEmpty() -> {
//
//                Log.i(TAG, "User interaction was cancelled.")
//            }
//            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
//                val fitActionRequestCode = FitActionRequestCode.values()[requestCode]
//                fitActionRequestCode.let {
//                    fitSignIn(fitActionRequestCode)
//                }
//            }
//            else -> {
//
//
//                Snackbar.make(
//                        findViewById(R.id.main_activity_view),
//                        R.string.permission_denied_explanation,
//                        Snackbar.LENGTH_INDEFINITE
//                )
//                        .setAction(R.string.settings) {
//                            val intent = Intent()
//                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                            val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                            intent.data = uri
//                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                        }
//                        .show()
//            }
//        }
//    }
//
//    private fun connect(ID: String, STEP : String) {
//
//        //이 부분 없으면 오류 이유 파익 x
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//        val jdbcURL = "jdbc:postgresql:/192.168.0.2/:5432/server" //서버 주소
//        val username = "postgres" // 유저 이름
//        val password = "281328" // 비번
//
//        try {
//            val connection = DriverManager.getConnection(jdbcURL, username, password) //연결한다,
//            println("Connected to PostgreSQL server")
//
//            /** 입력 */
//            // 쿼리에 입력한다.
//            var sql = "UPDATE data SET 걸음 = '$STEP' WHERE 아이디='$ID'"
//
//            val statement: PreparedStatement = connection.prepareStatement(sql)
//
//            // 이 값을 테이블에 넣음
//            //statement.setString(1, "$STEP")
//
//            val rows = statement.executeUpdate()
//
//
//            if (rows > 0) {
//                println("A new contact has been inserted.")
//                intent.putExtra("ID",ID)
//                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
//                println("불러온 걸음 수 : $STEP")
//            }
//            connection.close()
//
//        } catch (e: SQLException) {
//            println("데이터 저장 실패")
//            e.printStackTrace()
//            Toast.makeText(this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun historyAPI() {
//        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
//        val startTime = endTime.minusWeeks(1)
//        Log.i(TAG, "Range Start: $startTime")
//        Log.i(TAG, "Range End: $endTime")
//
//        val data_list = ArrayList<Int>()
//
//        val readRequest =
//            DataReadRequest.Builder()
//                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
//                .bucketByTime(1, TimeUnit.DAYS)
//                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
//                .build()
//
//        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
//            .readData(readRequest)
//            .addOnSuccessListener { response ->
//                for (dataSet in response.buckets.flatMap { it.dataSets }) {
//                    dumpDataSet(dataSet)
//
//                    val hi_total = when {
//                        dataSet.isEmpty -> 0
//                        else -> dataSet.dataPoints.first().getValue(Field.FIELD_STEPS).asInt()
//                    }
//                    Log.i(TAG, "과거 걸음 수 : $hi_total")
//
//                    data_list.add(hi_total)
//                }
//                println(data_list);
//
//                var text : TextView = findViewById(R.id.textView6) // xml 파일에 값추가
//                text.setText("$data_list")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG,"There was an error reading data from Google Fit", e)
//            }
//    }
//
//
//    fun dumpDataSet(dataSet: DataSet) {
//        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")
//        for (dp in dataSet.dataPoints) {
//            Log.i(TAG,"Data point:")
//            Log.i(TAG,"\tType: ${dp.dataType.name}")
//            Log.i(TAG,"\tStart: ${dp.getStartTimeString()}")
//            Log.i(TAG,"\tEnd: ${dp.getEndTimeString()}")
//            for (field in dp.dataType.fields) {
//                Log.i(TAG,"\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
//            }
//        }
//    }
//
//    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
//        .atZone(ZoneId.systemDefault())
//        .toLocalDateTime().toString()
//
//    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
//        .atZone(ZoneId.systemDefault())
//        .toLocalDateTime().toString()
//
//}
//
//