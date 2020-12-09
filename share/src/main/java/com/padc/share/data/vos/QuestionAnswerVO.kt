package com.padc.share.data.vos


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
@Entity(tableName = "question_answer")
@IgnoreExtraProperties
data class QuestionAnswerVO(
    @PrimaryKey
    var id: String= "",
    var question: String ?= "",
    var answer: String ?= ""
)