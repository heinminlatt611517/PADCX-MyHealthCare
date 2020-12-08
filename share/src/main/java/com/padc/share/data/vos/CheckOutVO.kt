package com.padc.share.data.vos

import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class CheckOutVO (
    @PrimaryKey
    var id: String= "",
    var delivery_address: String = "",
    var total_price : Int? =0,
    var patientVO: PatientVO ?=null,
    var doctorVO: DoctorVO ?=null,
    var delivery_routine : DeliveryRoutineVO ?= null,
    var prescription : ArrayList<PrescriptionVO> ? = arrayListOf()
)

@IgnoreExtraProperties
data class DeliveryRoutineVO(
        var id: String= "",
        var delivery_date : String ?= ""
)