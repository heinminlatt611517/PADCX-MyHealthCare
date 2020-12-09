package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.CaseSummaryConfirmPresenter
import com.padc.patient.mvp.view.CaseSummaryConfirmView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.DateUtils

class CaseSummaryConfirmPresenterImpl : CaseSummaryConfirmPresenter,
    AbstractBasePresenter<CaseSummaryConfirmView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mPatientModel.getQuestionAnswerFromDB()
            .observe(lifecycleOwner, Observer { dataLists ->
                mView?.displayQuestionAnswerLists(dataLists)
            })
    }

    override fun onTapStartConsultationButton(
        speciality: String,
        patientVO: PatientVO,
        questionAnswerLists: List<QuestionAnswerVO>
    ) {
        mPatientModel.sendBroadCastConsultationRequest(
            speciality,
            questionAnswerLists,
            patientVO,
            DateUtils().getCurrentDate(),
            onSuccess = {
                mView?.navigateToHomeScreen()
            },onFailure = {

            }
        )
    }


}