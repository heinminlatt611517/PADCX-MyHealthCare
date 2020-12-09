package com.padc.patient.mvp.view

import androidx.lifecycle.LifecycleOwner
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView

interface CaseSummaryView : BaseView {
 fun navigateToSpecialQuestionCaseSummaryScreen(specialityName : String)
    fun displayPatientInfo(patientVO: PatientVO)
}