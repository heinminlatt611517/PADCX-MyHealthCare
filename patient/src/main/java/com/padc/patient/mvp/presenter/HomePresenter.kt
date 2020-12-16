package com.padc.patient.mvp.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.delegates.ConsultationAcceptDelegate
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.patient.delegates.SpecialityDoctorItemDelegate
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.views.viewPods.ConsultationRequestViewPod
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>, RecentDoctorItemDelegate,
    SpecialityDoctorItemDelegate, ConsultationAcceptDelegate {
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun onTapConfirm(
        specialityName: String,
        dialogFragment: ConfirmDialogFragment
    )

    fun onCompleteStatus(
        context: Context,
        consultation_chat_id: String,
        consultationRequestVO: ConsultationRequestVO
    )

    fun onTapConfirmDirectRequest(
        specialityName: String,
        dataTime: String,
        questionAnswerLists: QuestionAnswerVO,
        patientVO: PatientVO,
        doctorVO: DoctorVO
    )
}
