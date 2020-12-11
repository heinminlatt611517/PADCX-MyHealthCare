package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PatientCaseSummaryPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PatientCaseSummaryView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.DateUtils

class PatientCaseSummaryPresenterImpl : PatientCaseSummaryPresenter,AbstractBasePresenter<PatientCaseSummaryView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, requestID: String) {
        mDoctorModel.getBroadConsultationRequest(requestID,onSuccess = {
            mView?.displayPatientData(it)
        },onFailure = {})

    }

    override fun onTapStartConsultation(consultationRequestVO: ConsultationRequestVO) {

        var doctorVo = DoctorVO(
            id = SessionManager.doctor_id.toString(),
            deviceID = SessionManager.doctor_device_id.toString(),
            name = SessionManager.doctor_name.toString(),
            phone = SessionManager.doctor_phone,
            degree = SessionManager.doctor_degree,
            email = SessionManager.doctor_email.toString(),
            biography = SessionManager.doctor_bigraphy,
            photo = SessionManager.doctor_photo,
            speciality = SessionManager.doctor_speciality
        )

        mDoctorModel.startConsultation(consultationRequestVO.id,
            DateUtils().getCurrentDate(), consultationRequestVO.case_summary,
            consultationRequestVO.patient_info, doctorVo,
            onSuccess = {} , onFailure = {})
        mView?.navigateToChatScreen()
    }



}