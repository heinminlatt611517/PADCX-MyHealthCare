package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MedicalCommentView
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.mvp.presenter.BasePresenter

interface MedicalCommentPresenter: BasePresenter<MedicalCommentView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String)
    fun onTapSaveMedicalRecord(consultationChatVO: ConsultationChatVO)
}