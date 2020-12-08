package com.padc.patient.mvp.presenter.impls


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.activities.CaseSummaryActivity
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.view.HomeView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, id: String) {

        mPatientModel.getSpecialities(onSuccess = {}, onError = {})

        mPatientModel.getSpecialitiesFromDB()
                .observe(lifecycleOwner, Observer {
                    mView?.displaySpecialistDoctorLists(it)
                })


//
//        mPatientModel.getRecentDoctorLists("",
//                onSuccess = {
//                    mView?.displayRecentDoctorLists(it as ArrayList<DoctorVO>)
//                },
//                onFailure = {
//                    mView?.showErrorMessage("Fail to load recent doctor lists!")
//                })

    }

    override fun onTapConfirm(
        specialityName: String,
        patientID: String,
        dialogFragment: ConfirmDialogFragment
    ) {
        dialogFragment.startActivity(dialogFragment.context?.let {
            CaseSummaryActivity.newIntent(
                it,specialityName,patientID
            )
        })
    }

    override fun onTapRecentDoctorItem(doctorID: String) {

    }

    override fun onTapSpecialityDoctorItem(specialitiesID: String) {
        mView?.showConfirmDialog(specialitiesID)
    }


}