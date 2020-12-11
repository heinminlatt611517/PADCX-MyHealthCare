package com.padc.padcx_myhealthcare_monthly_assignment.delegate

import com.padc.share.data.vos.ConsultationRequestVO

interface ConsultationRequestItemDelegate {
    fun onTapAccept(consultationRequestVO: ConsultationRequestVO)
    fun onTapSkip(consultationRequestVO: ConsultationRequestVO)
}