package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.padc.patient.mvp.presenter.ConsultationChatPresenter
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ConsultationChatPresenterImpl : ConsultationChatPresenter,AbstractBasePresenter<ConsultationChatView>(){

    private val mPatientModel: PatientModel = PatientModelImpl

    private val mPrescriptionLists = MutableLiveData<List<PrescriptionVO>>()

    override fun onUiReady(lifecycleOwner: LifecycleOwner, patientID: String) {
       mPatientModel.getFinishConsultationByPatientID(patientID,onSuccess = {
           mView?.displayFinishConsultationChatLists(it)
       },onFailure = {
           mView?.showErrorMessage(it)
       })
    }

    override fun getPrescriptionList(): LiveData<List<PrescriptionVO>> {
        return mPrescriptionLists
    }

    override fun onTapSendText(consultationChatID: String) {
        mView?.navigateToChatScreen(consultationChatID)
    }

    override fun onTapMedicineInfo(consultationChatID: String) {

        mPatientModel.getPrescriptionByID(consultationChatID,onSuccess = {
            mView?.showPrescriptionDialog(it)
        },
                onFailure = {
                    mView?.showErrorMessage(it)
                })



    }





}