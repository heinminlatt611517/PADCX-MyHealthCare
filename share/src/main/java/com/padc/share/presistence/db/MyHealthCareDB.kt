package com.padc.share.presistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.share.data.vos.*
import com.padc.share.presistence.dao.*
import com.padc.share.utils.DATABASE_NAME

@Database(
    entities = [SpecialitiesVO::class, PatientVO::class, DoctorVO::class,
        GeneralQuestionTemplateVO::class,SpecialQuestionVO::class,QuestionAnswerVO::class,ConsultationRequestVO::class
    ,ConsultedPatientVO::class,ConsultationChatVO::class],
    version = 8,
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
    abstract fun questionAnswerDao(): QuestionAnswerDao
    abstract fun consultationRequestDao() : ConsultationRequestDao
    abstract fun consultedPatientDao () : ConsultedPatientDao
    abstract fun consultationChatDao () : ConsultationChatDao

}