package com.padc.patient.views.viewHolders

import android.text.Editable
import android.view.View
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_special_question.view.*

class SpecialQuestionViewHolder(itemView: View,var mQuestionAnswerList: List<QuestionAnswerVO>) :BaseViewHolder<SpecialQuestionVO>(itemView) {
    override fun clickItem(it: View?) {

    }

    override fun bindData(data: SpecialQuestionVO) {
        mData = data
        data?.let {
            itemView.tv_specialQuestion.text = "(${adapterPosition+1}) ${data.name}"
        }

        mQuestionAnswerList?.let {
            itemView.ed_answer.text = Editable.Factory.getInstance().newEditable(mQuestionAnswerList[adapterPosition].answer)
        }

    }
}