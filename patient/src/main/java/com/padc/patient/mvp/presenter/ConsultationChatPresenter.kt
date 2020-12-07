package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.share.mvp.presenter.BasePresenter

interface ConsultationChatPresenter : BasePresenter<ConsultationChatView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun onTapMedicineInfo()
    fun onTapSendMessage()
}