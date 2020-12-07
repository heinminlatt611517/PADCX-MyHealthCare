package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ConsultationChatViewHolder(itemView: View) :BaseViewHolder<ConsultationChatVO>(itemView) {
    override fun clickItem(it: View?) {

    }
    override fun bindData(data: ConsultationChatVO) {
       mData = data
    }
}