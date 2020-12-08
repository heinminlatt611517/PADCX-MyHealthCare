package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "general_question_template")
@IgnoreExtraProperties
data class GeneralQuestionTemplateVO(
    @PrimaryKey
    var id: String= "",
    var type: String ?= "",
    var name:  String ? = ""
)