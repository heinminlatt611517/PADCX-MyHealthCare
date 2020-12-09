package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.presenter.CaseSummarySpecialQuestionPresenter
import com.padc.patient.mvp.view.CaseSummarySpecialQuestionView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class CaseSummarySpecialQuestionPresenterImpl : CaseSummarySpecialQuestionPresenter,
    AbstractBasePresenter<CaseSummarySpecialQuestionView>(){

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(speciality: String, lifecycleOwner: LifecycleOwner) {
        mPatientModel.getSpecialQuestionBySpeciality(speciality = speciality,
        onSuccess = {
            mView?.displaySpecialQuestionLists(it)
        },onFailure = {})
    }

    override fun onTapStartConsultation() {
        mView?.navigateToConfirmCaseSummaryScreen()
    }

    override fun onAnswerChange(position: Int, questionAnswerVO: QuestionAnswerVO) {
        mView?.replaceAnswerList(position,questionAnswerVO)

    }

}