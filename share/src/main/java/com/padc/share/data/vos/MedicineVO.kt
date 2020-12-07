package com.padc.share.data.vos


import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class MedicineVO(
    @PrimaryKey
    var id: String= "",
    var name: String? = "",
    var price : Int =0
)