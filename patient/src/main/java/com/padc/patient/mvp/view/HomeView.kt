package com.padc.patient.mvp.view

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.mvp.view.BaseView

interface HomeView : BaseView {
    fun showConsultationRequestDialogFragment()
    fun displaySpecialistDoctorLists(specialistDoctorLists : List<SpecialitiesVO>)
    fun displayRecentDoctorLists(recentDoctorLists : ArrayList<DoctorVO>)
    fun showConfirmDialog(specialityName : String)
    fun navigateToEmptyCaseSummaryScreen(context: Context,speciality : String)
    fun navigateToCaseSummaryScreen(patientVO: PatientVO,speciality : String)
    fun displayPatientData(patientVO: PatientVO)

}