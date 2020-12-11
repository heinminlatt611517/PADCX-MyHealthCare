package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.ChatMessageViewHolder
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.QuestionAnswerViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ChatMessageAdapter : BaseRecyclerAdapter<BaseViewHolder<ChatMessageVO>,ChatMessageVO>() {


    val MESSAGE_TYPE_PATIENT = 1
    val MESSAGE_TYPE_DOCTOR = 2

    var sendType: List<ChatMessageVO> = arrayListOf()

    fun setTypeListList( Lists: List<ChatMessageVO>)
    {
        sendType =Lists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ChatMessageVO> {

        return when (viewType) {
            MESSAGE_TYPE_PATIENT -> {
                ChatMessageViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_chat_message_patient, parent, false))
            }
            MESSAGE_TYPE_DOCTOR -> {
                ChatMessageViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_chat_message_doctor, parent, false))
            }
            else -> ChatMessageViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_chat_message_patient, parent, false))

        }

    }


    override fun getItemViewType(position: Int): Int {
        return when (sendType[position].senderType?.name) {
            "patient" -> {
                MESSAGE_TYPE_PATIENT
            }
            "doctor" -> {
                MESSAGE_TYPE_DOCTOR
            }
            else ->
                MESSAGE_TYPE_PATIENT
        }

    }
}