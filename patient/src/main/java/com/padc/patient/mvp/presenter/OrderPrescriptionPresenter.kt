package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.patient.mvp.view.OrderPrescriptionView
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.CheckOutVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.presenter.BasePresenter

interface OrderPrescriptionPresenter : BasePresenter<OrderPrescriptionView> ,PatientAddressItemDelegate{
    fun onUiReady(lifecycleOwner: LifecycleOwner,patientID : String,consultationID : String)
    fun onTapMadePayment(addressLists : List<AddressVO>)
    fun checkOutMedicine(checkOutVO: CheckOutVO)
    fun getPrescriptionLists() : LiveData<List<PrescriptionVO>>

    fun getPatientFullAddress() : LiveData<String>

}