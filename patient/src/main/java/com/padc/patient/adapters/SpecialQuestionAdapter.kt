package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.views.viewHolders.SpecialQuestionViewHolder
import com.padc.patient.views.viewHolders.SpecialityViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.views.viewHolder.BaseViewHolder

class SpecialQuestionAdapter : BaseRecyclerAdapter<BaseViewHolder<SpecialQuestionVO>,SpecialQuestionVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SpecialQuestionVO> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_special_question, parent, false)
        return SpecialQuestionViewHolder(view)
    }
}