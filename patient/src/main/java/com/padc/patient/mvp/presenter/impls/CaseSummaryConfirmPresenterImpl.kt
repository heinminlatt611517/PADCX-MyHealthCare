package com.padc.patient.mvp.presenter.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.mvp.presenter.CaseSummaryConfirmPresenter
import com.padc.patient.mvp.view.CaseSummaryConfirmView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.DoctorVO
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
        doctorEmail: String,
        questionAnswerLists: List<QuestionAnswerVO>
    ) {

        val mAddressLists: ArrayList<AddressVO> = arrayListOf()

        mPatientModel.getPatientByID(SessionManager.patient_id.toString(),
            onSuccess = {
                mAddressLists.addAll(it.address)
            }, onFailure = {})

        val patientVO = PatientVO(
            SessionManager.patient_id.toString(),
            SessionManager.patient_name.toString(),
            SessionManager.patient_email.toString(),
            SessionManager.patient_device_id,
            SessionManager.patient_photo,
            SessionManager.patient_bloodType,
            SessionManager.patient_bloodPressure,
            mAddressLists,
            SessionManager.patient_weight,
            SessionManager.patient_height,
            SessionManager.patient_phone,
            SessionManager.patient_dateOfBirth.toString(),
            SessionManager.patient_allegric.toString(),
            arrayListOf()
        )


        if (doctorEmail.isNullOrEmpty()) {
            mPatientModel.sendBroadCastConsultationRequest(
                speciality,
                questionAnswerLists,
                patientVO,
                DoctorVO(),
                DateUtils().getCurrentDate(),
                onSuccess = {
                    mView?.navigateToHomeScreen()
                }, onFailure = {

                }
            )
        } else {
            mPatientModel.getDoctorByEmail(doctorEmail, onSuccess = { doctorVO ->
                if (doctorVO.id.isEmpty()) {
                    mPatientModel.sendBroadCastConsultationRequest(
                        speciality,
                        questionAnswerLists,
                        patientVO,
                        DoctorVO(),
                        DateUtils().getCurrentDate(),
                        onSuccess = {
                            mView?.navigateToHomeScreen()
                        }, onFailure = {

                        }
                    )
                } else {
                    mPatientModel.sendBroadCastConsultationRequest(
                        speciality,
                        questionAnswerLists,
                        patientVO,
                        doctorVO,
                        DateUtils().getCurrentDate(),
                        onSuccess = {
                            mView?.navigateToHomeScreen()
                        }, onFailure = {

                        }
                    )
                }

            }, onFailure = {
                mView?.showErrorMessage(it)
            })
        }

    }


}