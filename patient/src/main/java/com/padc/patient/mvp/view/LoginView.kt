package com.padc.patient.mvp.view

import com.padc.share.mvp.view.BaseView

interface LoginView : BaseView {
    fun navigateToRegisterScreen()
    fun navigateToMainScreen(patientID : String)
}