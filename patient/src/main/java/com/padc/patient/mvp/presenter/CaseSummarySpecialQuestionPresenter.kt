package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.CaseSummarySpecialQuestionView
import com.padc.share.mvp.presenter.BasePresenter

interface CaseSummarySpecialQuestionPresenter : BasePresenter<CaseSummarySpecialQuestionView>{

    fun onUiReady(speciality : String, lifecycleOwner: LifecycleOwner)
    fun onTapStartConsultation()
}