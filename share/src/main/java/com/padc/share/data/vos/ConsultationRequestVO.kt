package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padc.share.presistence.typeConverters.DoctorTypeConverter
import com.padc.share.presistence.typeConverters.GeneralQuestionTypeConverter
import com.padc.share.presistence.typeConverters.PatientTypeConverter
import com.padc.share.presistence.typeConverters.PrescriptionTypeConverter

@Entity(tableName = "consultation_request")
@TypeConverters(
    GeneralQuestionTypeConverter::class,
    PatientTypeConverter::class,
    DoctorTypeConverter::class
    )

@IgnoreExtraProperties
data class ConsultationRequestVO (
    @PrimaryKey
    var id: String= "",
    var speciality : String ?= "",
    var date_time : String ?=null ,
    var status : String ? = "none",
    var patient_info : PatientVO ,
    var doctor_info : DoctorVO ,
    var consultation_id : String ?= "",
    var case_summary : ArrayList<QuestionAnswerVO> = arrayListOf()
)
