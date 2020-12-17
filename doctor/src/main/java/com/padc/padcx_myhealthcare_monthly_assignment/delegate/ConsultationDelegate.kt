package com.padc.padcx_myhealthcare_monthly_assignment.delegate

import com.padc.share.data.vos.ConsultationChatVO



interface ConsultationDelegate {
    fun onTapMedicalRecord(data: ConsultationChatVO)
    fun onTapPrescription(data: ConsultationChatVO)
    fun onTapSendMessage(data: ConsultationChatVO)
    fun onTapDoctorComment(data: ConsultationChatVO)
}