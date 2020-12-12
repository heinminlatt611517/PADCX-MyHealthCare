package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.share.mvp.presenter.BasePresenter

interface PrescribeMedicinePresenter : BasePresenter<PrescribeMedicineView> ,PrescribeMedicineItemDelegate {
  fun onUiReady(lifecycleOwner: LifecycleOwner,speciality : String)

  fun onTapStopConsultation()
}