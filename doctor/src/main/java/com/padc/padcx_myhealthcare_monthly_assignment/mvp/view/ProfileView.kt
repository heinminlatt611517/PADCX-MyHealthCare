package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.view.BaseView

interface ProfileView : BaseView {
    fun displayDoctorData( doctorVO: DoctorVO)
    fun hideProgressDialog()
    fun navigateToEditProfileScreen()
    fun navigateToMainScreen()

}