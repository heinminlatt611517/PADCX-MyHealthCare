package com.padc.patient.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.patient.views.viewHolders.ConsultationChatViewHolder
import com.padc.patient.views.viewHolders.PatientAddressViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.AddressVO
import com.padc.share.mvp.view.BaseView
import com.padc.share.views.viewHolder.BaseViewHolder

class PatientAddressAdapter(delegate : PatientAddressItemDelegate,var previousPosition : Int) : BaseRecyclerAdapter<BaseViewHolder<AddressVO>,AddressVO>() {

    val  mDelegate  = delegate

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AddressVO> {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_patient_adderss, parent, false)
        return PatientAddressViewHolder(previousPosition,mDelegate,view)
    }
}