package com.padc.patient.views.viewHolders

import android.text.Editable
import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationChatItemDelegate
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_chat_message_patient.view.*
import kotlinx.android.synthetic.main.list_item_consultation_chat.view.*
import kotlinx.android.synthetic.main.list_item_speciality.view.*

class ChatMessageViewHolder(itemView: View) :BaseViewHolder<ChatMessageVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: ChatMessageVO) {
        mData = data
        data?.let {
            itemView.message_text.text = Editable.Factory.getInstance().newEditable(it.messageText)
        }
    }
}