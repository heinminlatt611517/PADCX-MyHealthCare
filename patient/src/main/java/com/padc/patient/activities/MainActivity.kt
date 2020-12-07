package com.padc.patient.activities


import android.os.Bundle
import com.padc.patient.R
import com.padc.share.activities.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFirebaseInstanceID()
    }
}