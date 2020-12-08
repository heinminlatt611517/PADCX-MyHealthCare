package com.padc.share.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class PrescriptionVO(
    var id: String= "",
    var total_price : Int =0,
    var name : String = "",
    var routineVO: ArrayList<RoutineVO> = arrayListOf()
)

