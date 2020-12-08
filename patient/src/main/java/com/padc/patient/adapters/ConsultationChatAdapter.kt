package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.patient.views.viewHolders.ConsultationChatViewHolder
import com.padc.patient.views.viewHolders.RecentDoctorViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ConsultationChatAdapter(delegate : ConsultationChatItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<ConsultationChatVO>,ConsultationChatVO>() {
    val mDelegate  = delegate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ConsultationChatVO> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_consultation_chat, parent, false)
        return ConsultationChatViewHolder(mDelegate,view)
    }
}