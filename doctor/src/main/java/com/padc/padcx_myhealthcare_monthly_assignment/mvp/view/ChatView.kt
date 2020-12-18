package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.view.BaseView

interface ChatView : BaseView{

    fun displayChatMessage(messageLists : List<ChatMessageVO>)

    fun displayPatientRequestData(data : ConsultationChatVO)

    fun navigateToPrescribeMedicineScreen()

    fun navigateToGeneralQuestionScreen()

    fun displayPrescriptionLists(lists : List<PrescriptionVO>)

    fun navigateToMedicalCommentScreen()
}