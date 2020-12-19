package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.CaseSummaryConfirmView
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.BasePresenter

interface CaseSummaryConfirmPresenter : BasePresenter<CaseSummaryConfirmView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun onTapStartConsultationButton(speciality : String,doctorEmail : String,questionAnswerLists : List<QuestionAnswerVO>)
}