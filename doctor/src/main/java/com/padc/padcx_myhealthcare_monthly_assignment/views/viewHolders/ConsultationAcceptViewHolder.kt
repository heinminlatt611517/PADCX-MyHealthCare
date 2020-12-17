package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.view.View
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.iv_patient
import kotlinx.android.synthetic.main.listitem_consultation_accept.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class ConsultationAcceptViewHolder(private val mDelegate: ConsultationDelegate, itemView: View) : BaseViewHolder<ConsultationChatVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }
    override fun bindData(data: ConsultationChatVO) {
        mData = data

        data?.let {
            ImageUtils().showImage(itemView.iv_patient,data.patient_info?.photo.toString(), R.drawable.doctor)
            itemView.tv_patient_name.text = data.patient_info?.name
            itemView.tv_patient_dateofbirth.text = data.patient_info?.dateOfBirth
        }


        itemView.txt_consulated_history.setOnClickListener {
            mDelegate.onTapMedicalRecord(data)
        }

        itemView.txt_prescription.setOnClickListener {
            mDelegate.onTapPrescription(data)
        }

        itemView.txt_comment.setOnClickListener {
            mDelegate.onTapDoctorComment(data)
        }

        itemView.txt_send_message.setOnClickListener {
            mDelegate.onTapSendMessage(data)
        }
    }
}