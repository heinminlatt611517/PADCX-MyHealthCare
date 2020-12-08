package com.padc.share.data.vos


import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class OneTimeGeneralQuestionVO(
    @PrimaryKey
    var question: String ?= ""

)