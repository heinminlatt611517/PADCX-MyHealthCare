package com.padc.share.data.vos


import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class OneTimeGeneralQuestionVO(
    @PrimaryKey
    var question: String ?= ""

)