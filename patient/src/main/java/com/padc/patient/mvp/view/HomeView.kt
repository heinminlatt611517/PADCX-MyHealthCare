package com.padc.patient.mvp.view

import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.mvp.view.BaseView

interface HomeView : BaseView {
    fun showConsultationRequestDialogFragment()
    fun displaySpecialistDoctorLists(specialistDoctorLists : ArrayList<SpecialitiesVO>)
    fun displayRecentDoctorLists(recentDoctorLists : ArrayList<DoctorVO>)
    fun showDialog()
    fun navigateToEmptyCaseSummaryScreen(speciality : String)
    fun navigateToCaseSummaryScreen(patientVO: PatientVO,speciality : String)

}