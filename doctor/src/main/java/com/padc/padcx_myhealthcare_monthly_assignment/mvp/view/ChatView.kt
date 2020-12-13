package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.mvp.view.BaseView

interface ChatView : BaseView{

    fun displayChatMessage(messageLists : List<ChatMessageVO>)

    fun displayPatientRequestData(data : ConsultationRequestVO)

    fun navigateToPrescribeMedicineScreen()

    fun navigateToGeneralQuestionScreen()

}