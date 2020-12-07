package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.views.viewHolder.BaseViewHolder

class SpecialQuestionViewHolder(itemView: View) :BaseViewHolder<SpecialQuestionVO>(itemView) {
    override fun clickItem(it: View?) {

    }

    override fun bindData(data: SpecialQuestionVO) {
        mData = data
    }
}