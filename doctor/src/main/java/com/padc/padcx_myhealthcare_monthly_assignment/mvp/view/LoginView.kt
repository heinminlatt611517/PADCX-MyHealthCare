package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView

interface LoginView : BaseView {
    fun navigateToRegisterScreen()
    fun navigateToMainScreen(doctorVO: DoctorVO )
}