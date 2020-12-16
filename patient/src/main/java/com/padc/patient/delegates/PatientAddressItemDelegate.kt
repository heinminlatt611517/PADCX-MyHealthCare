package com.padc.patient.delegates

interface PatientAddressItemDelegate {
    fun onTapAddress(fullAddress : String)

    fun showEmptyAddressView()
    fun showRecyclerAddressView()
}