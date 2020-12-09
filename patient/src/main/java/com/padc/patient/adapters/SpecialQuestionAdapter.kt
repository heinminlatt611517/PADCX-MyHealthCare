package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.SpecialQuestionDelegate
import com.padc.patient.views.viewHolders.SpecialQuestionViewHolder
import com.padc.patient.views.viewHolders.SpecialityViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.views.viewHolder.BaseViewHolder

class SpecialQuestionAdapter(private val mDelegate: SpecialQuestionDelegate) : BaseRecyclerAdapter<BaseViewHolder<SpecialQuestionVO>,SpecialQuestionVO>() {



    var mQuestionAnswerList: List<QuestionAnswerVO> = arrayListOf()

    fun setQuestionAnswerList( questionAnswerList: List<QuestionAnswerVO>)
    {
        mQuestionAnswerList =questionAnswerList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SpecialQuestionVO> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_special_question, parent, false)
        return SpecialQuestionViewHolder(view,mQuestionAnswerList,mDelegate)
    }
}