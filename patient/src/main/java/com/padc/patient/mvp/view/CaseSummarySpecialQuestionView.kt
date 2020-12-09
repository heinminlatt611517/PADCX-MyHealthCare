package com.padc.patient.mvp.view

import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.mvp.view.BaseView

interface CaseSummarySpecialQuestionView : BaseView {
    fun navigateToConfirmCaseSummaryScreen()
    fun displaySpecialQuestionLists(list: List<SpecialQuestionVO>)
    fun replaceAnswerList(position : Int , questionAnswerVO: QuestionAnswerVO)
}