package com.padc.patient.mvp.view

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.share.data.vos.*
import com.padc.share.mvp.view.BaseView

interface HomeView : BaseView {
    fun showConsultationRequestDialogFragment()
    fun displaySpecialistDoctorLists(specialistDoctorLists : List<SpecialitiesVO>)
    fun displayRecentDoctorLists(recentDoctorLists : ArrayList<DoctorVO>)
    fun showConfirmDialog(specialityName : String)
    fun navigateToEmptyCaseSummaryScreen(context: Context,speciality : String)
    fun navigateToCaseSummaryScreen(speciality : String)
    fun displayPatientData(patientVO: PatientVO)
    fun navigateToChatScreen(consultation_chat_id : String,consultationRequestVO: ConsultationRequestVO)
    fun displayConsultationRequestList(consultationRequestVO: List<ConsultationRequestVO>)

    fun showRecentDoctorDialog(doctorVO: DoctorVO,consultationRequestVO: ConsultationRequestVO)

    fun showSuccessStatus(message : String)
    fun navigateToMainScreen()
}