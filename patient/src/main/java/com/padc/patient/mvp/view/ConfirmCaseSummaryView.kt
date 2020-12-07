package com.padc.patient.mvp.view

import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.mvp.view.BaseView

interface ConfirmCaseSummaryView : BaseView {
    fun displayPatientInfo(patientVO: PatientVO)
    fun displaySpecialQuestionLists(lists : List<SpecialQuestionVO>)
    fun navigateToHomeScreen()
}