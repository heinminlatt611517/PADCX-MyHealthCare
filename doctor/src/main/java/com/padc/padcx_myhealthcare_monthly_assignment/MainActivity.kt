package com.padc.padcx_myhealthcare_monthly_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padc.share.activities.BaseActivity
import com.padc.share.services.FCMService

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFirebaseInstanceID()
        FCMService()
    }
}