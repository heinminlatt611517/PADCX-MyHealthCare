package com.padc.padcx_myhealthcare_monthly_assignment.delegate

import com.padc.share.data.vos.MedicineVO

interface PrescribeMedicineItemDelegate {
    fun onTapAddMedicine(medicineVO : MedicineVO)
    fun onTapRemoveMedicine(medicineVO: MedicineVO)
}