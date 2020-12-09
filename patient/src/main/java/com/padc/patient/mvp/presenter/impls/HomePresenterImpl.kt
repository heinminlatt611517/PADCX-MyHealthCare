package com.padc.patient.mvp.presenter.impls


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.activities.CaseSummaryActivity
import com.padc.patient.activities.EmptyCaseSummaryActivity
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

        mPatientModel.getSpecialities(onSuccess = {}, onError = {})

        mPatientModel.getSpecialitiesFromDB()
                .observe(lifecycleOwner, Observer {
                    mView?.displaySpecialistDoctorLists(it)
                })


//
        mPatientModel.getRecentDoctorLists(SessionManager.patient_id.toString(),
                onSuccess = {
                    mView?.displayRecentDoctorLists(it as ArrayList<DoctorVO>)
                },
                onFailure = {
                    mView?.showErrorMessage("Fail to load recent doctor lists!")
                })



    }

    override fun onTapConfirm(
        specialityName: String,
        dialogFragment: ConfirmDialogFragment
    ) {

        mPatientModel.getPatientByEmail(SessionManager.patient_email.toString(),
        onSuccess = { it ->

            it.blood_type?.let {
                if (it.isBlank()) {
                    dialogFragment.startActivity(dialogFragment.context?.let { context ->
                        EmptyCaseSummaryActivity.newIntent(
                            context,specialityName
                        )
                    })
                    dialogFragment.dismiss()
                }
                else{
                    dialogFragment.startActivity(dialogFragment.context?.let {context ->
                        CaseSummaryActivity.newIntent(
                            context,specialityName
                        )
                    })
                    dialogFragment.dismiss()
                }
            }
        },onError = {})


    }

    override fun onTapRecentDoctorItem(doctorID: String) {

    }

    override fun onTapSpecialityDoctorItem(specialitiesID: String) {
        mView?.showConfirmDialog(specialitiesID)
    }

    override fun onTapStartConsultation() {

        mView?.navigateToChatScreen()

    }


}