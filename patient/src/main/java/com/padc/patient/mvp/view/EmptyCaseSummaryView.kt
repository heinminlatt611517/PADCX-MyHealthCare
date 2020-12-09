package com.padc.patient.mvp.view

import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView

interface EmptyCaseSummaryView : BaseView{
    fun savePatientDataAndNavigateToCaseSummaryScreen(patientVO: PatientVO,speciality : String)
}