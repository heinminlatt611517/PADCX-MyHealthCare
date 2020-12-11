package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.ConsultationAcceptViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ConsultationAcceptAdapter : BaseRecyclerAdapter<BaseViewHolder<ConsultationChatVO>,ConsultationChatVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationChatVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_consultation_accept, parent, false)
        return ConsultationAcceptViewHolder(view)
    }
}
