package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties


@Entity(tableName = "consulted_patient")
@IgnoreExtraProperties
class ConsultedPatientVO(
    @PrimaryKey
    var id: String= "",
    var patient_id: String ?= ""
)