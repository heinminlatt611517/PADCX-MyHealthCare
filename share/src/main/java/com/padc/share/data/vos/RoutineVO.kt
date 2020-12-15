package com.padc.share.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RoutineVO (
    var id: String= "",
    var day: String? = "",
    var times: String? ="",
    var type : String? ="",
    var commment : String? = ""

)
