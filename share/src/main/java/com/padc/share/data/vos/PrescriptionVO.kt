package com.padc.share.data.vos

import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padc.share.presistence.typeConverters.RoutineTypeConverter

@TypeConverters(RoutineTypeConverter::class)
@IgnoreExtraProperties
data class PrescriptionVO(
    var id: String= "",
    var total_price : Int =0,
    var name : String = "",
    var routineVO: ArrayList<RoutineVO> = arrayListOf()
)

