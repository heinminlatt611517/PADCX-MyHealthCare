package com.padc.padcx_myhealthcare_monthly_assignment.root

import android.app.Application
import com.padc.share.presistence.db.MyHealthCareDB

class DoctorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MyHealthCareDB.getDbInstance(applicationContext)
    }
}