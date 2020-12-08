package com.padc.patient.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.padc.patient.R
import com.padc.share.activities.BaseActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.padc.patient.fragments.HomeFragment

class MainActivity : BaseActivity() {

    companion object {

        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,patientID : String) : Intent{
            val intent = Intent(context,MainActivity::class.java)
            intent.putExtra(ID_EXTRA,patientID)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        Log.d("PatientMainID",intent.getStringExtra(ID_EXTRA).toString())

    }

}