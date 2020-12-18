package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MedicalCommentPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MedicalCommentView
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class MedicalCommentPresenterImpl : MedicalCommentPresenter,AbstractBasePresenter<MedicalCommentView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String) {

        mDoctorModel.getConsultationChat(consultationID,onSuccess = {}, onError = {})

        mDoctorModel.getConsultationChatFromDB(consultationID)
            .observe(lifecycleOwner, Observer { data ->
                data?.let {
                    mView?.displayConsultationPatientData(data)
                }
            })


    }

    override fun onTapSaveMedicalRecord(consultationChatVO: ConsultationChatVO) {
        mDoctorModel.saveMedicalRecord(consultationChatVO,onSuccess = {
            mView?.showSnackBar("Success")
        },onError = {
            mView?.showSnackBar("Fail")
        })
    }
}