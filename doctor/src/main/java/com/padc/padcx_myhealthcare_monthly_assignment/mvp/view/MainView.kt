package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView

interface MainView : BaseView {
  fun showPatientDialog(patientVO: PatientVO)
  fun displayPatientData(patientVO: PatientVO)
}