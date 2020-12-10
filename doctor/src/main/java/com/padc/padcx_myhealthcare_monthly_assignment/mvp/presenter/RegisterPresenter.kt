package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import android.content.Context
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.RegisterView
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.BasePresenter
import com.padc.share.mvp.view.BaseView

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(username: String, email: String, password: String, token : String, speciality_name: String,
                       phone: String, degree: String)
}