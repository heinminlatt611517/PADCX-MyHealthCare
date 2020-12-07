package com.padc.share.data.vos

import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ConsultationRequestVO (
    @PrimaryKey
    var id: String= "",
    var speciality : String ?= "",
    var date_time : String ?=null ,
    var patient_info : PatientVO ?=null,
    var doctor_info : DoctorVO ?=null,
    var case_summary : ArrayList<QuestionAnswerVO> = arrayListOf()
)
