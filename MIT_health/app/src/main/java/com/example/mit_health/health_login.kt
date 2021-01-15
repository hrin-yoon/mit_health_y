package com.example.mit_health

import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.health_login.*


class health_login: AppCompatActivity() {
    private lateinit var callback: PackageInstaller.SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_login)

        login_p.setOnClickListener {
            val nextdata = Intent(this, health_data::class.java)
            startActivity(nextdata)
        }


        }

    }



//        var callback = PackageInstaller.SessionCallback
//        Session.getCurrentSession().addCallback(callback)
//        Session.getCurrentSession().checkAndImplicitOpen()

//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        //Session.getCurrentSession().removeCallback(callback)
//    }
//
//    // 앱의 해쉬 키 얻는 함수
//    private fun getAppKeyHash() {
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md: MessageDigest
//                md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val something = String(Base64.encode(md.digest(), 0))
//                Log.e("Hash key", something)
//            }
//        } catch (e: Exception) {
//            // TODO Auto-generated catch block
//            Log.e("name not found", e.toString())
//        }
//
//    }
//
//    private inner class SessionCallback : ISessionCallback {
//        override fun onSessionOpened() {
//            // 로그인 세션이 열렸을 때
//            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
//                override fun onSuccess(result: MeV2Response?) {
//                    // 로그인이 성공했을 때
//                    var intent = Intent(this@health_login, health_data::class.java)
////                    intent.putExtra("name", result!!.getNickname())
////                    intent.putExtra("profile", result!!.getProfileImagePath())
//                    startActivity(intent)
//                    finish()
//                }
//
//                override fun onSessionClosed(errorResult: ErrorResult?) {
//                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
//                    Toast.makeText(
//                            this@health_login,
//                            "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
//                            Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//
//        override fun onSessionOpenFailed(exception: KakaoException?) {
//            // 로그인 세션이 정상적으로 열리지 않았을 때
//            if (exception != null) {
//                com.kakao.util.helper.log.Logger.e(exception)
//                Toast.makeText(
//                        this@health_login,
//                        "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception",
//                        Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }
//
////    private fun redirectSignupActivity() {
////        val intent = Intent(this, health_login::class.java)
////        startActivity(intent)
////        finish()
////    }

















