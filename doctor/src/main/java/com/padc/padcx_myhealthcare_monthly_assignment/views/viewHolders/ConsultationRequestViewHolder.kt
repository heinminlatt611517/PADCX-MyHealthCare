package com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders

import android.view.View
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_patient_dialog.view.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.iv_patient
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.tv_patientBirthDate
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.tv_patientName
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

            var data = consulted_patient.filter {
                it.patient_id == data.patient_info.id
            }

            if(data.isEmpty())
            {
                itemView.tv_patientType.text =  itemView.resources.getString(R.string.new_patient)
                itemView.btn_next.visibility =View.GONE
                itemView.btn_postPone.visibility = View.GONE
                itemView.btn_skip.visibility = View.VISIBLE
            }
            else
            {
                itemView.tv_patientType.text =  itemView.resources.getString(R.string.consulated_patient)
                itemView.btn_next.visibility =View.VISIBLE
                itemView.btn_postPone.visibility = View.VISIBLE
                itemView.btn_skip.visibility = View.GONE
            }
        }

        itemView.btn_next.setOnClickListener {
            mDelegate.onTapNext(data)
        }

        itemView.btn_postPone.setOnClickListener {
            mDelegate.onTapPostpone(data)
        }


        itemView.btn_accept.setOnClickListener {
            mDelegate.onTapAccept(data)
        }

        itemView.btn_skip.setOnClickListener {
            mDelegate.onTapSkip(data)
        }

    }
}