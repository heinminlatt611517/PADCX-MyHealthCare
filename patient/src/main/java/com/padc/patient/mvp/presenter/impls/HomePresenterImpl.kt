package com.padc.patient.mvp.presenter.impls

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.padc.patient.R
import com.padc.patient.activities.CaseSummaryActivity
import com.padc.patient.activities.EmptyCaseSummaryActivity
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.view.HomeView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mPatientModel: PatientModel = PatientModelImpl
    private  var mSpecialitiesVO: SpecialitiesVO = SpecialitiesVO()
    override fun onUiReady(lifecycleOwner: LifecycleOwner, id: String) {

        mPatientModel.getSpecialities(onSuccess = {}, onError = {})

        mPatientModel.getSpecialitiesFromDB()
                .observe(lifecycleOwner, Observer {
                    mView?.displaySpecialistDoctorLists(it)
                })



        mPatientModel.getRecentDoctorLists("f4e94ef0-38bc-11eb-864d-8f34a3b15b20",
                onSuccess = {
                    mView?.displayRecentDoctorLists(it as ArrayList<DoctorVO>)
                },
                onFailure = {
                    mView?.showErrorMessage("Fail to load recent doctor lists!")
                })

    }


    override fun onTapConfirm(specialityName : String,patientID : String,dialogFragment: ConfirmDialogFragment) {
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