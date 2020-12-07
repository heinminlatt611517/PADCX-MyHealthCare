package com.padc.patient.mvp.presenter.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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
    lateinit var mPatientVO: PatientVO
    lateinit var mSpecialitiesVO: SpecialitiesVO
    override fun onUiReady(lifecycleOwner: LifecycleOwner, id: String) {
        mPatientModel.getSpecialitiesFromDB()
            .observe(lifecycleOwner, Observer {
                mView?.displaySpecialistDoctorLists(it as ArrayList<SpecialitiesVO>)
            })

        mPatientModel.getRecentDoctorLists(id = id,
            onSuccess = {
                mView?.displayRecentDoctorLists(it as ArrayList<DoctorVO>)
            },
            onFailure = {
                mView?.showErrorMessage("Fail to load recent doctor lists!")
            })

        mPatientModel.getPatientFromDatabase(id)
            .observe(lifecycleOwner, Observer {
                mPatientVO = it
            })
    }


    override fun onTapConfirm() {
        if (mPatientVO.name.isEmpty()) {
            mView?.navigateToEmptyCaseSummaryScreen(mSpecialitiesVO.name)
        } else {
            mView?.navigateToCaseSummaryScreen(mPatientVO,mSpecialitiesVO.name)
        }
    }

    override fun onTapRecentDoctorItem(doctorID: String) {

    }

    override fun onTapSpecialityDoctorItem(specialitiesVO: SpecialitiesVO) {
        mSpecialitiesVO = specialitiesVO
        mView?.showDialog()
    }


}