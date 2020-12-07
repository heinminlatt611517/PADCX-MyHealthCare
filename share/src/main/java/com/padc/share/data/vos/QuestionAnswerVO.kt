package com.padc.share.data.vos


import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class QuestionAnswerVO(
    @PrimaryKey
    var id: String= "",
    var question: String ?= "",
    var answer: String ?= ""
)