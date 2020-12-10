package com.padc.padcx_myhealthcare_monthly_assignment.root

import android.app.Application
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.presistence.db.MyHealthCareDB

class DoctorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DoctorModelImpl.initDatabase(applicationContext)
        SessionManager.init(applicationContext)
    }
}