package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.presenter.ConsultationChatPresenter
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ConsultationChatPresenterImpl : ConsultationChatPresenter,AbstractBasePresenter<ConsultationChatView>(){

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, patientID: String) {
       mPatientModel.getFinishConsultationByPatientID(patientID,onSuccess = {
           mView?.displayFinishConsultationChatLists(it)
       },onFailure = {
           mView?.showErrorMessage(it)
       })
    }

    override fun onTapSendText() {

    }

    override fun onTapMedicineInfo() {

    }


}