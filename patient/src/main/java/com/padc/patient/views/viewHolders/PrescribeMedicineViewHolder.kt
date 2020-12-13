package com.padc.patient.views.viewHolders

import android.text.Editable
import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.delegates.PatientAddressItemDelegate
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.*
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_order_prescription.view.*
import kotlinx.android.synthetic.main.list_item_consultation_chat.view.*
import kotlinx.android.synthetic.main.list_item_patient_adderss.view.*
import kotlinx.android.synthetic.main.list_item_prescribe_medicine.view.*
import kotlinx.android.synthetic.main.list_item_speciality.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class PrescribeMedicineViewHolder(itemView: View) :
    BaseViewHolder<PrescriptionVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: PrescriptionVO) {
        mData = data

        data?.let {
            itemView.tv_medicineName.text = data.medicine
            itemView.tv_medicineAmount.text = data.price.toString()
            itemView.tv_medicineTab.text = data.count.toString()

        }

    }
}