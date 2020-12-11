package com.padc.patient.delegates

import android.view.View
import com.padc.share.data.vos.ConsultationRequestVO


interface ConsultationAcceptDelegate {
    fun onTapStartConsultation(consultationChatId: String, consultationRequestVO: ConsultationRequestVO)
}