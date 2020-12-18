package com.padc.patient.delegates

import com.padc.share.data.vos.AddressVO

interface PatientAddressItemDelegate {
    fun onTapAddress(address : AddressVO,previousPosition: Int)

    fun showEmptyAddressView()
    fun showRecyclerAddressView()


}