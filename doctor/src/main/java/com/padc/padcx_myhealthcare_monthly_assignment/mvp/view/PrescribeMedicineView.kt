package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.MedicineVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.view.BaseView

interface PrescribeMedicineView : BaseView {
    fun displayMedicineLists(lists : List<MedicineVO>)
    fun showPrescribeMedicineDialog(medicineVO: MedicineVO)
    fun showText(text : String)
    fun navigateToSplashScreen()
    fun displayPatientRequestData(data : ConsultationChatVO)
    fun displayPrescriptionLists(prescriptionVO: PrescriptionVO )
    fun navigateToChatScreen()
}