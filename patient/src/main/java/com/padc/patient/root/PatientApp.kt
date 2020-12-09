package com.padc.patient.root

import android.app.Application
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.presistence.db.MyHealthCareDB

class PatientApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PatientModelImpl.initDatabase(applicationContext)
        SessionManager.init(applicationContext)
    }
}