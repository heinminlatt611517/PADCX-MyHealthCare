package com.padc.patient.mvp.presenter

import com.padc.patient.mvp.view.RegisterView
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.BasePresenter

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(patientVO: PatientVO,password : String)
}