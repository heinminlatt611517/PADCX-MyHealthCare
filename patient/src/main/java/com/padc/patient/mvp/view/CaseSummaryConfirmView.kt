package com.padc.patient.mvp.view

import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.view.BaseView

interface CaseSummaryConfirmView : BaseView
{
    fun displayQuestionAnswerLists(lists : List<QuestionAnswerVO>)

    fun navigateToHomeScreen()

}