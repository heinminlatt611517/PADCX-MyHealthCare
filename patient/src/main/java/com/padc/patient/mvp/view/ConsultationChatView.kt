package com.padc.patient.mvp.view

import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.view.BaseView

interface ConsultationChatView : BaseView {
    fun navigateToMedicineCaseSummary()
    fun navigateToChatScreen(consultationChatID : String)
    fun displayFinishConsultationChatLists(lists : List<ConsultationChatVO>)
    fun showPrescriptionDialog(list : List<PrescriptionVO>)
    fun displayList(list : List<PrescriptionVO>)
}