package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.mvp.view.BaseView

interface MedicalCommentView : BaseView
{
    fun displayConsultationPatientData(consultationChatVO: ConsultationChatVO)
    fun showSnackBar(message : String)
}