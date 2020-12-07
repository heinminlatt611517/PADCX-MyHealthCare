package com.padc.share.presistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.share.data.vos.*
import com.padc.share.presistence.dao.DoctorDao
import com.padc.share.presistence.dao.GeneralQuestionTemplateDao
import com.padc.share.presistence.dao.PatientDao
import com.padc.share.presistence.dao.SpecialityDao
import com.padc.share.utils.DATABASE_NAME

@Database(
    entities = [SpecialitiesVO::class, PatientVO::class, DoctorVO::class,
        GeneralQuestionTemplateVO::class,SpecialQuestionVO::class],
    version = 3,
    exportSchema = false
)

abstract class MyHealthCareDB : RoomDatabase() {

    companion object {
        var dbInstance: MyHealthCareDB? = null

        fun getDbInstance(context: Context): MyHealthCareDB {
            when (dbInstance) {
                null -> {
                    dbInstance =
                        Room.databaseBuilder(context, MyHealthCareDB::class.java, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            val i = dbInstance
            return i!!
        }
    }

    abstract fun doctorDao(): DoctorDao
    abstract fun patientDao(): PatientDao
    abstract fun specialityDao() : SpecialityDao
    abstract fun generalQuestionTemplateDao() : GeneralQuestionTemplateDao

}