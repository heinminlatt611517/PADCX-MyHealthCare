package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ChatPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ChatView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl : ChatPresenter,AbstractBasePresenter<ChatView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String,requestID : String) {
        mDoctorModel.getBroadConsultationRequest(requestID,onSuccess = {
            mView?.displayPatientRequestData(it)
        },onFailure = {})


        mDoctorModel.getAllChatMessage("c08e9900-3b08-11eb-9d22-4d096b6a3638",
            onSuccess = {
                mView?.displayChatMessage(it)
            }, onFailure = {
                mView?.showErrorMessage(it)
            })

    }


    override fun onTapSend(consultationID: String, message: ChatMessageVO) {
        mDoctorModel.sendMessage(consultationID, message, onSuccess = {
        }, onFailure = {})
    }

    override fun onTapAttach() {

    }

    override fun onTapSeeMore() {

    }

    override fun onTapQuestion() {

    }

    override fun onTapPrescribeMedicine() {

    }

    override fun onTapMedicineHistory() {

    }
}