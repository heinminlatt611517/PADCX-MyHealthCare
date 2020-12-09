package com.padc.patient.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.padc.patient.R
import com.padc.patient.utils.SessionManager

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            if(!SessionManager.login_status) {
                startActivity(LoginActivity.newIntent(this))
            }else{
                startActivity(MainActivity.newIntent(this))
            }
            finish()
        },1800)

    }
}