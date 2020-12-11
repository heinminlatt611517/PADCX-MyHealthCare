package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationAcceptDelegate
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.views.viewHolders.ConsultationAcceptViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ConsultationAcceptAdapter(delegate : ConsultationAcceptDelegate) :
    BaseRecyclerAdapter<BaseViewHolder<ConsultationRequestVO>, ConsultationRequestVO>() {

    var mDelegate = delegate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationRequestVO> {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_accept_consulation_request, parent, false)
        return ConsultationAcceptViewHolder(mDelegate,view)
    }
}