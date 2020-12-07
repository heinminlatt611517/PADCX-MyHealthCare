package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.CaseSummaryPresenter
import com.padc.patient.mvp.view.CaseSummaryView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class CaseSummaryPresenterImpl : CaseSummaryPresenter,AbstractBasePresenter<CaseSummaryView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUIReady(lifecycleOwner: LifecycleOwner, patientName: String) {
       mPatientModel.getPatientFromDatabase(patientName)
           .observe(lifecycleOwner, Observer {
              // mView?.displayPatientInfo(it)
           })
    }

    override fun onTapContinue(specialityName: String, patientID: String) {
       mView?.navigateToSpecialQuestionCaseSummaryScreen(specialityName,patientID)
    }


}