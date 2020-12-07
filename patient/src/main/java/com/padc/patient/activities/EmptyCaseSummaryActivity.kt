package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.R

class EmptyCaseSummaryActivity : AppCompatActivity() {

    companion object {

        fun newIntent(context: Context) : Intent {
            return Intent(context, EmptyCaseSummaryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_case_summary)
    }
}