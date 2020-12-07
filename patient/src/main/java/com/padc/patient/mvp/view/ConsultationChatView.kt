package com.padc.patient.mvp.view

import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.mvp.view.BaseView

interface ConsultationChatView : BaseView {
    fun navigateToMedicineCaseSummary()
    fun navigateToChatScreen()
    fun displayFinishConsultationChatLists(lists : List<ConsultationChatVO>)
}