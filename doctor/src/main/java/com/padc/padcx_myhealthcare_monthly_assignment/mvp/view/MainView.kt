package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.view.BaseView

interface MainView : BaseView {
  fun showPatientDialog(patientVO: PatientVO)
  fun displayConsultationRequestLists(list: List<ConsultationRequestVO>)
  fun displayConsultationAcceptList(list: List<ConsultationChatVO>)
  fun navigateToPatientCaseSummary(consultation_request_id : String)
  fun displayConsultedPatient(list : List<ConsultedPatientVO>)
  fun navigateToChatScreen(consultation_chat_id : String)
  fun displayConsultationRequestPatient(consultationRequestVO: ConsultationRequestVO)
}