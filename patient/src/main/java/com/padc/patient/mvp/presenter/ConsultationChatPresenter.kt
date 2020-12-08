package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.share.mvp.presenter.BasePresenter

interface ConsultationChatPresenter : BasePresenter<ConsultationChatView>,ConsultationChatItemDelegate {
    fun onUiReady(lifecycleOwner: LifecycleOwner,patientID : String)

}