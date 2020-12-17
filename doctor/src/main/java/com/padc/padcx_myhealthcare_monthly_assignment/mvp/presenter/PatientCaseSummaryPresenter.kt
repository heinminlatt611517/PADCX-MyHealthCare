package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PatientCaseSummaryView
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.mvp.presenter.BasePresenter

interface PatientCaseSummaryPresenter : BasePresenter<PatientCaseSummaryView> {

    fun onUiReady(lifecycleOwner: LifecycleOwner,requestID : String)
    fun onTapStartConsultation(consultationRequestVO : ConsultationRequestVO)
    fun onUiReadyConsultation( consulationRequestId : String ,  owner: LifecycleOwner)
}