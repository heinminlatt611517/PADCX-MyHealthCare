package com.padc.patient.activities


import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.padc.patient.R
import com.padc.share.activities.BaseActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getFirebaseInstanceID()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }
}