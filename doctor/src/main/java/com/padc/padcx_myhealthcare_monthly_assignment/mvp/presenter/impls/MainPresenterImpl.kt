package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MainView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class MainPresenterImpl : MainPresenter,AbstractBasePresenter<MainView>() {
    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner,patientID : String) {
        if (patientID != "null")
            mDoctorModel.getPatientByID(patientID,onSuccess = {
                mView?.displayPatientData(it)
            },onFailure = {})

    }

    override fun onTapSkip() {

    }

    override fun onTapAccept() {

    }


}