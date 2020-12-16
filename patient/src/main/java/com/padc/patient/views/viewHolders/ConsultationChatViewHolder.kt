package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_consultation_chat.view.*
import kotlinx.android.synthetic.main.list_item_speciality.view.*

class ConsultationChatViewHolder(private val mDelegate: ConsultationChatItemDelegate,itemView: View) :BaseViewHolder<ConsultationChatVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: ConsultationChatVO) {
        mData = data
        data?.let {
            itemView.tv_doctor_name.text = data.doctor_info?.name
            itemView.tv_doctor_speciality.text = data.doctor_info?.speciality
            itemView.tv_consultation_chatDate.text = data.start_consultation_date

            data?.doctor_info?.photo?.let {
                ImageUtils().showImage(itemView.iv_doctor, it, R.drawable.speciality_thumbnail)
            }

            itemView.layout_sendText.setOnClickListener {
                mDelegate.onTapSendText()
            }

            itemView.layout_medicine_info.setOnClickListener {
                mDelegate.onTapMedicineInfo()
            }
        }
    }
}