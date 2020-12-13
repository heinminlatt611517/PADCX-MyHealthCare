package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.RelatedQuestionVO
import com.padc.share.mvp.view.BaseView

interface GeneralQuestionView : BaseView {
    fun displayGeneralQuestionLists (lists : List<RelatedQuestionVO>)
    fun showQuestion(question : String)
    fun navigateToChatScreen()
}