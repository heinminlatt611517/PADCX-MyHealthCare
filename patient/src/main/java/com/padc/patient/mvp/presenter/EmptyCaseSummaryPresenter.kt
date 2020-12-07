package com.padc.patient.mvp.presenter

import com.padc.patient.mvp.view.EmptyCaseSummaryView
import com.padc.share.mvp.presenter.BasePresenter

interface EmptyCaseSummaryPresenter : BasePresenter<EmptyCaseSummaryView> {
    fun onTapContinue()
}