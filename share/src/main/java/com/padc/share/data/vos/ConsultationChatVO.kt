package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
class ConsultationChatVO(
    @PrimaryKey
    var id: String= "",
    var doctor_case_summary : String? = "",
    var finish_flag : Boolean? = null,
    var patient_id : String,
    var patient_info : PatientVO ? =null ,
    var doctor_info : DoctorVO ? = null,
    var case_summary : ArrayList<QuestionAnswerVO>? = arrayListOf(),
    var prescription : ArrayList<PrescriptionVO> ? = arrayListOf()
)