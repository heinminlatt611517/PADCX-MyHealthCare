package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.view.View
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import kotlinx.android.synthetic.main.listitem_question_answer.view.*

class ConsultationRequestViewHolder(var consulted_patient :List<ConsultedPatientVO>, private val mDelegate : ConsultationRequestItemDelegate, itemView: View) : BaseViewHolder<ConsultationRequestVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }

    override fun bindData(data: ConsultationRequestVO) {
        mData = data

        data?.let {


            ImageUtils().showImage(itemView.iv_patient,data.patient_info?.photo.toString(), R.drawable.doctor_thumbnail)
            itemView.tv_patientName.text = data.patient_info?.name
            itemView.tv_patientBirthDate.text = data.patient_info?.dateOfBirth
        }

        itemView.btn_accept.setOnClickListener {
            mDelegate.onTapAccept(data)
        }

        itemView.btn_skip.setOnClickListener {
            mDelegate.onTapSkip(data)
        }

    }
}