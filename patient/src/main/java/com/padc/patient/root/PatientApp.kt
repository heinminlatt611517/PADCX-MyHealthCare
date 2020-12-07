package com.padc.patient.root

import android.app.Application
import com.padc.share.presistence.db.MyHealthCareDB

class PatientApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MyHealthCareDB.getDbInstance(applicationContext)
    }
}