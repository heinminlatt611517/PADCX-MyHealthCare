package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.ChatPresenter
import com.padc.patient.mvp.view.ChatView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.DateUtils

class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String) {

        mPatientModel.getPrescription(consultationID,onSuccess = {
            mView?.displayPrescriptionLists(it)
        },onFailure = {
            mView?.showErrorMessage(it)
        })

        mPatientModel.getConsultationChat(consultationID,onSuccess = {}, onError = {})

        mPatientModel.getConsultationChatFromDB(consultationID)
                .observe(lifecycleOwner, Observer { data ->
                    data?.let {
                        mView?.displayPatientRequestData(data)
                    }
                })

        mPatientModel.getAllChatMessage(consultationID,
             onSuccess = {
            mView?.displayChatMessage(it)
        }, onFailure = {
            mView?.showErrorMessage(it)
        })



    }

    override fun onTapSend(consultationID: String, message: ChatMessageVO) {
        mPatientModel.sendMessage(consultationID, message, onSuccess = {
        }, onFailure = {})
    }

    override fun onTapSeeMore() {
        mView?.navigateToRequestPatientDataScreen()
    }

    override fun onTapAttach() {

    }

    override fun onSwipeRefresh(lifecycleOwner: LifecycleOwner, consultationID: String) {
        mView?.enableSwipeRefresh()
        mPatientModel.getPrescription(consultationID,onSuccess = {
            mView?.disableSwipeRefresh()
            mView?.displayPrescriptionLists(it)
        },onFailure = {
            mView?.showErrorMessage(it)
        })
    }

    override fun onTapOrderPrescription() {
        mView?.navigateToOrderPrescriptionScreen()
    }
}