package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MainView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {
    private val mDoctorModel: DoctorModel = DoctorModelImpl

    lateinit var mOwner: LifecycleOwner
    override fun onUiReady(lifecycleOwner: LifecycleOwner, requestID: String) {
//        if (requestID != "null") {
//            mDoctorModel.getBroadConsultationRequest(requestID, onSuccess = {
//             mView?.displayConsultationRequestPatient(it)
//            }, onFailure = {})
//
//        }
        mOwner = lifecycleOwner

        mDoctorModel.getBrodcastConsultationRequests(
            SessionManager.doctor_speciality.toString(),
            onSuccess = {},
            onError = {})



        mDoctorModel.getBrodcastConsultationRequestsFromDB(SessionManager.doctor_speciality.toString())
            .observe(lifecycleOwner, Observer { consultationRequest ->
                consultationRequest?.let {
                    val data = consultationRequest.filter {
                        it.status.toString() == "none"
                    }
                    mView?.displayConsultationRequestLists(data)
                }
            })

//        mDoctorModel.getConsultationByDoctorId(
//            SessionManager.doctor_id.toString(),
//            onSuccess = {},
//            onError = {})
//
//        mDoctorModel.getConsultationByDoctorIdFromDB(SessionManager.doctor_id.toString())
//            .observe(lifecycleOwner, Observer { data ->
//                data?.let {
//                    mView?.displayConsultationAcceptList(data)
//                }
//            })


//        mDoctorModel.getConsultedPatient(
//            SessionManager.doctor_id.toString(),
//            onSuccess = {},
//            onError = {})
//
//        mDoctorModel.getConsultedPatientFromDB(SessionManager.doctor_id.toString())
//            .observe(lifecycleOwner, Observer { data ->
//                data?.let {
//                    mView?.displayConsultedPatient(data)
//                }
//            })

        mDoctorModel.getConsultationChatForDoctor(SessionManager.doctor_id.toString(),onSuccess = {
            mView?.displayConsultationAcceptList(it)
        },onFailure = {})

        mDoctorModel.getConsultationPatient(SessionManager.doctor_id.toString(),
        onSuccess = {
            mView?.displayConsultedPatient(it)
        },onFailure = {

        })

    }

    override fun onTapSetting() {
        mView?.navigateToSettingScreen()
    }
//
//    override fun onTapAcceptMain(consultationRequestVO: ConsultationRequestVO) {
//        var doctorVo = DoctorVO(
//            id = SessionManager.doctor_id.toString(),
//            deviceID = SessionManager.doctor_device_id.toString(),
//            name = SessionManager.doctor_name.toString(),
//            phone = SessionManager.doctor_phone,
//            degree = SessionManager.doctor_degree,
//            email = SessionManager.doctor_email.toString(),
//            biography = SessionManager.doctor_bigraphy,
//            photo = SessionManager.doctor_photo,
//            speciality = SessionManager.doctor_speciality
//        )
//        mDoctorModel.acceptRequest(
//            "accept",
//            consultationRequestVO.id,
//            consultationRequestVO.case_summary,
//            consultationRequestVO.patient_info,
//            doctorVo, onSuccess = {
//
//
//
//            }, onFailure = {})
//    }


    override fun onTapAccept(consultationRequestVO: ConsultationRequestVO) {
        acceptRequest("accept", 2,consultationRequestVO)

    }

    override fun onTapSkip(consultationRequestVO: ConsultationRequestVO) {
//        mDoctorModel.deleteConsultationRequestById(consultationRequestVO.id)
//            .observe(mOwner, Observer { consultationRequest ->
//                consultationRequest?.let {
//                    mView?.displayConsultationRequestLists(consultationRequest) }
//            })
    }

    override fun onTapNext(consultationRequestVO: ConsultationRequestVO) {

    }

    override fun onTapPostpone(consultationRequestVO: ConsultationRequestVO) {
        mView?.displayPostPoneDialog(consultationRequestVO)
    }

    override fun onTapPostponeTime(
        postPoneTime: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        acceptRequest("postpone $postPoneTime",1, consultationRequestVO)
    }

    override fun onTapMedicalRecord(data: ConsultationChatVO) {

    }

    override fun onTapPrescription(data: ConsultationChatVO) {

    }

    override fun onTapSendMessage(data: ConsultationChatVO) {
        mView?.navigateToChatScreen(data.id)
    }

    override fun onTapDoctorComment(data: ConsultationChatVO) {

    }

    private fun acceptRequest(status: String, type : Int,   consultationRequestVO: ConsultationRequestVO) {
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

        mDoctorModel.acceptRequest(
                "accept",
                consultationRequestVO.id,
                consultationRequestVO.case_summary,
                consultationRequestVO.patient_info,
                doctorVo, onSuccess = {}, onFailure = {})




        if(type == 2) {
            mView?.navigateToPatientCaseSummary(consultationRequestVO.id)
        }else{

        }
    }

}