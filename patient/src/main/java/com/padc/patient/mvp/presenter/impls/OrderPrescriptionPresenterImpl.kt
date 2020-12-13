package com.padc.patient.mvp.presenter.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.padc.patient.mvp.presenter.OrderPrescriptionPresenter
import com.padc.patient.mvp.view.OrderPrescriptionView
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.*
import com.padc.share.mvp.presenter.AbstractBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class OrderPrescriptionPresenterImpl : OrderPrescriptionPresenter,AbstractBasePresenter<OrderPrescriptionView>() {

    private val mPatientModel: PatientModel = PatientModelImpl
    private val mPrescriptionDataLists = MutableLiveData<List<PrescriptionVO>>()
    private val mPatientFullAddress = MutableLiveData<String>()
    override fun onUiReady(lifecycleOwner: LifecycleOwner, patientID: String,consultationID : String) {

        mPatientModel.getPatientByEmail(patientID,onSuccess = {
            mView?.displayPatientAddress(it.address)
        },onError = {

        })


        mPatientModel.getPrescriptionByID(consultationID,onSuccess = {
            mView?.displayPrescribeMedicineLists(it)
           mPrescriptionDataLists.value = it
        },
        onFailure = {
            mView?.showErrorMessage(it)
        })


    }

    override fun onTapMadePayment(patientVO: PatientVO) {

       mPatientModel.registerNewPatient(patientVO = patientVO,onSuccess = {
           mView?.showPaymentDialog()
       },onFailure = {})


    }

    override fun checkOutMedicine(checkOutVO: CheckOutVO) {
        mPatientModel.checkoutMedicine(checkOutVO,onSuccess = {},onFailure = {
            mView?.showErrorMessage(it)
        })
    }

    override fun getPrescriptionLists(): LiveData<List<PrescriptionVO>> {
        return mPrescriptionDataLists
    }

    override fun getPatientFullAddress(): LiveData<String> {
        return mPatientFullAddress
    }

    override fun onTapAddress(fullAddress: String) {
       mPatientFullAddress.value = fullAddress
    }


}