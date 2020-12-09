package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.EmptyCaseSummaryView
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.BasePresenter

interface EmptyCaseSummaryPresenter : BasePresenter<EmptyCaseSummaryView> {
    fun onTapContinue(patientVO: PatientVO,lifecycleOwner: LifecycleOwner)
    fun onUiReady(lifecycleOwner: LifecycleOwner)

}