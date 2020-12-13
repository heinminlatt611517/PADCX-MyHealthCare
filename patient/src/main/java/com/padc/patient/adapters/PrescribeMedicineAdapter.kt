package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.patient.views.viewHolders.ConsultationChatViewHolder
import com.padc.patient.views.viewHolders.PatientAddressViewHolder
import com.padc.patient.views.viewHolders.PrescribeMedicineViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.MedicineVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.mvp.view.BaseView
import com.padc.share.views.viewHolder.BaseViewHolder

class PrescribeMedicineAdapter() : BaseRecyclerAdapter<BaseViewHolder<PrescriptionVO>,PrescriptionVO>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PrescriptionVO> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_prescribe_medicine, parent, false)
        return PrescribeMedicineViewHolder(view)
    }
}