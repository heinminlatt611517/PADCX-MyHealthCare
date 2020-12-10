package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.view.View
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class QuestionAnswerViewHolder(itemView: View) : BaseViewHolder<QuestionAnswerVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: QuestionAnswerVO) {
        mData = data

        data?.let {
            itemView.txt_question.text = "(${adapterPosition + 1}) " + data.question
            itemView.txt_answer.text = data.answer
        }

    }
}