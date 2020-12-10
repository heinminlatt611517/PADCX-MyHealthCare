package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.LoginView
import com.padc.share.mvp.presenter.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context: Context, email: String, password: String, lifecycleOwner: LifecycleOwner)
    fun onTapRegister()
}