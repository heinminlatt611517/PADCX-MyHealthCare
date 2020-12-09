package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.EmptyCaseSummaryPresenter
import com.padc.patient.mvp.view.EmptyCaseSummaryView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class EmptyCaseSummaryPresenterImpl : EmptyCaseSummaryPresenter,AbstractBasePresenter<EmptyCaseSummaryView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onTapContinue( patientVO: PatientVO,lifecycleOwner: LifecycleOwner) {
        mPatientModel.registerNewPatient(patientVO,onSuccess = {
            mPatientModel.getPatientByEmail(it.email,onSuccess = {
            }, onError = {})
            mPatientModel.getPatientByEmailFromDB(it.email).
            observe(lifecycleOwner, Observer {
                it?.let {
                    mView?.savePatientDataAndNavigateToCaseSummaryScreen(it,"")
                }
            })
        },onFailure = {
            mView?.showErrorMessage(it)
        })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

}