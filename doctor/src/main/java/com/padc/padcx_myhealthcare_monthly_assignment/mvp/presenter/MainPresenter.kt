package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MainView
import com.padc.share.mvp.presenter.BasePresenter
import com.padc.share.mvp.view.BaseView

interface MainPresenter : BasePresenter<MainView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner,patientID : String)
    fun onTapSkip()
    fun onTapAccept()

}