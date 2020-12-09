package com.padc.patient.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.LoginView
import com.padc.share.mvp.presenter.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(email: String, password: String,lifecycleOwner: LifecycleOwner)
    fun onTapRegister()

}