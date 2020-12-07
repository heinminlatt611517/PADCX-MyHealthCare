package com.padc.share.root

import android.app.Application
import com.padc.share.presistence.db.MyHealthCareDB


class MyHealthCareApp  : Application()
{
    override fun onCreate() {
        super.onCreate()
       MyHealthCareDB.getDbInstance(applicationContext)
    }
}