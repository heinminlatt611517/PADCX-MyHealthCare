package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padc.share.presistence.typeConverters.AddressTypeConverter
import com.padc.share.presistence.typeConverters.OneTimeGeneralQuestionTypeConverter

@Entity(tableName = "Patient")
@IgnoreExtraProperties
@TypeConverters(OneTimeGeneralQuestionTypeConverter::class,AddressTypeConverter::class)
data class PatientVO (
    @PrimaryKey
    var id: String= "",
    var name: String = "",
    var email: String = "",
    var deviceID: String? = "",
    var photo: String? = "",
    var blood_type: String? = "",
    var blood_pressure: String? = "",
    var address: ArrayList<AddressVO> = arrayListOf(),
    var weight: String? = "",
    var height: String? = "",
    var dateOfBirth: String= "",
    var allergic_reactions: String? = "",
    var oneTimeGeneralQuestionVO: ArrayList<OneTimeGeneralQuestionVO> = arrayListOf()

)
