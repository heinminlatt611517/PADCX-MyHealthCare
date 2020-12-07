package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.ConfirmCaseSummaryView
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.BasePresenter

interface ConfirmCaseSummaryPresenter : BasePresenter<ConfirmCaseSummaryView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner,patientID : String,speciality : String)
    fun onTapStartConsultationRequest(speciality: String,
                                      questionAnswerList: List<QuestionAnswerVO>,
                                      patientVO: PatientVO,
                                      dateTime: String,
                                      onSuccess: () -> Unit, onFailure: (String) -> Unit)
}