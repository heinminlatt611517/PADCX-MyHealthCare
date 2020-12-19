package com.padc.patient.mvp.view

import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView


interface ProfileView : BaseView {

    fun displayPatientData(patientData : PatientVO)
    fun hideProgressDialog()
    fun navigateToSplashScreen()
    fun navigateToEditProfileScreen()
    fun navigateToMainScreen()
}