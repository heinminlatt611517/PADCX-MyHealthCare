package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.text.Editable
import android.view.View
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_chat_message_patient.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class ChatMessageViewHolder(itemView: View) : BaseViewHolder<ChatMessageVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: ChatMessageVO) {
        mData = data

        mData = data
        data?.let {
            itemView.message_text.text = Editable.Factory.getInstance().newEditable(it.messageText)
        }

    }
}