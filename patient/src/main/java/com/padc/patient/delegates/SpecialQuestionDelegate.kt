package com.padc.patient.delegates

import com.padc.share.data.vos.QuestionAnswerVO


interface SpecialQuestionDelegate {
    fun onAnswerChange(position: Int, questionAnswerVO : QuestionAnswerVO)
}