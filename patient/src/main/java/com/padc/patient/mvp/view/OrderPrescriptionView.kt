package com.padc.patient.mvp.view

import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.view.BaseView

interface OrderPrescriptionView : BaseView {

    fun displayPatientAddress(addressLists: List<AddressVO>)
    fun showPaymentDialog()
    fun displayFullAddress(fullAddress : String)
    fun displayPrescribeMedicineLists(medicineLists : List<PrescriptionVO>)
    fun showEmptyAddressView()
    fun showRecyclerAddressView()
    fun selectedRecyclerAddress(address: AddressVO, previousPosition: Int)
    fun navigateToHomeFragment()
}