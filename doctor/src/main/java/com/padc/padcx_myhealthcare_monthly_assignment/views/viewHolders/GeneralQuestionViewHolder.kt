package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.text.Editable
import android.view.View
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.GeneralQuestionItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.share.data.vos.*
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.iv_patient
import kotlinx.android.synthetic.main.list_item_general_question.view.*
import kotlinx.android.synthetic.main.list_item_medicine.view.*
import kotlinx.android.synthetic.main.listitem_consultation_accept.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class GeneralQuestionViewHolder(private val delegate: GeneralQuestionItemDelegate, itemView: View) :
    BaseViewHolder<RelatedQuestionVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {
        }
    }
    override fun bindData(data: RelatedQuestionVO) {
        mData = data

        data?.let {
             itemView.ed_general_question.text =  Editable.Factory.getInstance().newEditable(data.question)
        }

        itemView.ed_general_question.setOnClickListener {
            delegate.onTapGeneralQuestion(data.question.toString())
        }



    }
}