package com.padc.share.data.vos


import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class MedicineVO(
    @PrimaryKey
    var id: String= "",
    var name: String? = "",
    var price : Int =0,
    @Exclude
    var isSelect : Boolean = false

)