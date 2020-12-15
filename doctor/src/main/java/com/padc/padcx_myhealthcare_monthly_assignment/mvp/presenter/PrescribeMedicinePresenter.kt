package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.MedicineVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.presenter.BasePresenter

interface PrescribeMedicinePresenter : BasePresenter<PrescribeMedicineView>,
    PrescribeMedicineItemDelegate {
    fun onUiReady(lifecycleOwner: LifecycleOwner, speciality: String,consultationID: String)
    fun onTapStopConsultation(consultationVO : ConsultationChatVO)
    fun addMedicineCount(data: String)
    fun addMedicineType(data: String)
    fun addToPrescriptionLists(
        medicine: String,
        amount: String,
        day: String,
        spinnerItem: String,
        comment: String,
        type : String,
        count : String
    )


}