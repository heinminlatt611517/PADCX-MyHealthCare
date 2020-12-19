package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.ConfirmCaseSummaryPresenter
import com.padc.patient.mvp.view.ConfirmCaseSummaryView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ConfirmCaseSummaryPresenterImpl : ConfirmCaseSummaryPresenter,
    AbstractBasePresenter<ConfirmCaseSummaryView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReady(
        lifecycleOwner: LifecycleOwner,
        patientID: String,
        speciality: String
    ) {

        mPatientModel.getPatientFromDatabase(patientID)
            .observe(lifecycleOwner, Observer {
                mView?.displayPatientInfo(patientVO = it)
            })

        mPatientModel.getSpecialQuestionBySpeciality(
            speciality = speciality,
            onSuccess = {
                mView?.displaySpecialQuestionLists(it)
            },
            onFailure = {})


    }

    override fun onTapStartConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mPatientModel.sendBroadCastConsultationRequest(speciality,
//            questionAnswerList, patientVO, dateTime, onSuccess = {
//                mView?.navigateToHomeScreen()
//            }, onFailure = {})
    }
}