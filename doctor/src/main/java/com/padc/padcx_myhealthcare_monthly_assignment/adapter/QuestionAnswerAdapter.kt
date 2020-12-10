package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.QuestionAnswerViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.views.viewHolder.BaseViewHolder

class QuestionAnswerAdapter : BaseRecyclerAdapter<BaseViewHolder<QuestionAnswerVO>,QuestionAnswerVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<QuestionAnswerVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_question_answer, parent, false)
        return QuestionAnswerViewHolder(view)
    }
}