package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.GeneralQuestionItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.GeneralQuestionView
import com.padc.share.mvp.presenter.BasePresenter

interface GeneralQuestionPresenter : BasePresenter<GeneralQuestionView> , GeneralQuestionItemDelegate{
    fun onUiReady(lifecycleOwner: LifecycleOwner,speciality : String,consultationID : String)
}