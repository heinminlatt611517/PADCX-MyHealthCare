package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PatientCaseSummaryPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PatientCaseSummaryView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class PatientCaseSummaryPresenterImpl : PatientCaseSummaryPresenter,AbstractBasePresenter<PatientCaseSummaryView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, requestID: String) {
        mDoctorModel.getBroadConsultationRequest(requestID,onSuccess = {
            mView?.displayPatientData(it)
        },onFailure = {})

    }

    override fun onTapStartConsultation() {
       mView?.navigateToChatScreen()
    }
}