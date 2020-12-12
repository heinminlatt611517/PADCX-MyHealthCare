package com.padc.padcx_myhealthcare_monthly_assignment.mvp.view

import com.padc.share.data.vos.MedicineVO
import com.padc.share.mvp.view.BaseView

interface PrescribeMedicineView : BaseView {
    fun displayMedicineLists(lists : List<MedicineVO>)
    fun showPrescribeMedicineDialog(medicineVO: MedicineVO)
}