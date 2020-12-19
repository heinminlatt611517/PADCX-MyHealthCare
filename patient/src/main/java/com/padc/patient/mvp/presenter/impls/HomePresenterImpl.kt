package com.padc.patient.mvp.presenter.impls


import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.patient.activities.CaseSummaryActivity
import com.padc.patient.activities.EmptyCaseSummaryActivity
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.dialogs.RecentDoctorDialogFragment
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.utils.DateUtils

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mPatientModel: PatientModel = PatientModelImpl
    private var mConsultationRequestVO : ConsultationRequestVO? =null

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

        mPatientModel.getSpecialities(onSuccess = {}, onError = {})

        mPatientModel.getSpecialitiesFromDB()
            .observe(lifecycleOwner, Observer {
                mView?.displaySpecialistDoctorLists(it)
            })



        mPatientModel.getRecentDoctorLists(SessionManager.patient_id.toString(),
            onSuccess = {
                mView?.displayRecentDoctorLists(it as ArrayList<DoctorVO>)
            },
            onFailure = {
                mView?.showErrorMessage("Fail to load recent doctor lists!")
            })


        mPatientModel.getConsultationAccepts(
            SessionManager.patient_id.toString(),
            onSuccess = {},
            onError = {})

        mPatientModel.getConsultationAcceptsFromDB()
            .observe(lifecycleOwner, Observer {
                var data = it.filter { it ->
                    it.consultation_id.toString().isNotEmpty()
                }
                mView?.displayConsultationRequestList(data)
            })


    }

    override fun onTapConfirm(
        specialityName: String,
        dialogFragment: ConfirmDialogFragment
    ) {

        mPatientModel.getPatientByEmail(SessionManager.patient_email.toString(),
            onSuccess = { it ->

                it.blood_type?.let {
                    if (it.isBlank()) {
                        dialogFragment.startActivity(dialogFragment.context?.let { context ->
                            EmptyCaseSummaryActivity.newIntent(
                                context, specialityName,
                            "")
                        })
                        dialogFragment.dismiss()
                    } else {
                        dialogFragment.startActivity(dialogFragment.context?.let { context ->
                            CaseSummaryActivity.newIntent(
                                context, specialityName,
                            "")
                        })
                        dialogFragment.dismiss()
                    }
                }
            }, onError = {})


    }

    override fun onCompleteStatus(
        context: Context,
        consultation_chat_id: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        mPatientModel.navigateToChatRoom(consultation_chat_id, consultationRequestVO,
            onSuccess = {}, onError = {})
    }

    override fun onTapConfirmDirectRequest(
        specialityName: String,
        doctorEmail :String,
        dialogFragment: RecentDoctorDialogFragment
    ) {

                    if (SessionManager.patient_allegric?.isEmpty()!!) {
                        dialogFragment.startActivity(dialogFragment.context?.let { context ->
                            EmptyCaseSummaryActivity.newIntent(
                                context, specialityName,doctorEmail
                            )
                        })
                        dialogFragment.dismiss()
                    } else {
                        dialogFragment.startActivity(dialogFragment.context?.let { context ->
                            CaseSummaryActivity.newIntent(
                                context, specialityName,doctorEmail
                            )
                        })
                        dialogFragment.dismiss()
                    }


    }

    override fun onTapDirectRequest(specialityName: String) {
        mPatientModel.getBroadConsultationRequestByDoctorSpeciality(specialityName,onSuccess = {

            mPatientModel.sendDirectRequest(
                specialityName, DateUtils().getCurrentDate(), it.case_summary, it.patient_info,
                it.doctor_info,onSuccess = {
                    mView?.navigateToMainScreen()
                },onFailure = {}
            )

        },onFailure = {
            mView?.showErrorMessage(it)
        })
    }


    override fun onTapRecentDoctorItem(doctorVO: DoctorVO) {
//        mPatientModel.getBroadConsultationRequestByDoctorSpeciality(doctorVO.speciality.toString(),onSuccess = {
//            mView?.showRecentDoctorDialog(it.doctor_info,it)
//        },onFailure = {
//            mView?.showErrorMessage(it)
//        })
        doctorVO.speciality?.let { mView?.showRecentDoctorDialog(doctorVO.email, it) }
    }

    override fun onTapSpecialityDoctorItem(specialitiesID: String) {
        mView?.showConfirmDialog(specialitiesID)
    }

    override fun onTapStartConsultation(
        consultationChatId: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        Log.d("ConsultationId", consultationChatId)
        mView?.navigateToChatScreen(consultationChatId, consultationRequestVO)
    }

}