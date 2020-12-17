package com.padc.patient.views.viewHolders

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_order_prescription.view.*
import kotlinx.android.synthetic.main.list_item_patient_adderss.view.*
import kotlinx.android.synthetic.main.list_item_prescription.view.*


class PrescriptionViewHolder(itemView: View) :
    BaseViewHolder<PrescriptionVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: PrescriptionVO) {
        mData = data

        data?.let {
           itemView.tv_medicineName.text = data.medicine
            itemView.tv_time_count.text = data.count.toString()

        }
    }
}