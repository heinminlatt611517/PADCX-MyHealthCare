package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.MedicineVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import com.padc.share.mvp.presenter.BasePresenter

class PrescribeMedicinePresenterImpl : PrescribeMedicinePresenter , AbstractBasePresenter<PrescribeMedicineView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, speciality: String) {

        mDoctorModel.getMedicineBySpeciality(speciality,onSuccess = {
            mView?.displayMedicineLists(it)
        },onFailure = {})

    }

    override fun onTapStopConsultation() {

    }

    override fun onTapAddMedicine(medicineVO: MedicineVO) {
      mView?.showPrescribeMedicineDialog(medicineVO)
    }
}