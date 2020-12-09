package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.ChatPresenter
import com.padc.patient.mvp.view.ChatView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String) {

        mPatientModel.getBroadConsultationRequest(consultationID, onSuccess = {
            mView?.displayPatientRequestData(it)
        }, onFailure = {
            mView?.showErrorMessage(it)
        })

        mPatientModel.getAllChatMessage("4317dd50-3a0f-11eb-936d-05c256064aa7", onSuccess = {
            mView?.displayChatMessage(it)
        }, onFailure = {
            mView?.showErrorMessage(it)
        })


    }

    override fun onTapSend(consultationID: String, message: ChatMessageVO) {
        mPatientModel.sendMessage(consultationID, message, onSuccess = {}, onFailure = {})
    }

    override fun onTapSeeMore() {
        mView?.navigateToRequestPatientDataScreen()
    }

    override fun onTapAttach() {

    }
}