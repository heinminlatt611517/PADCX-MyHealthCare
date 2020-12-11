package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.ConsultationAcceptDelegate
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.item_accept_consulation_request.view.*


class ConsultationAcceptViewHolder(private val mDelegate : ConsultationAcceptDelegate,itemView: View) :BaseViewHolder<ConsultationRequestVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let {

        }
    }
    override fun bindData(data: ConsultationRequestVO) {
        mData = data
        data?.let {
            itemView.txt_consulation.text = data.doctor_info?.speciality + itemView.resources.getString(R.string.consultation_request_message)
            ImageUtils().showImage(itemView.img_userprofile, data.doctor_info?.photo.toString(), R.drawable.doctor)
            itemView.txt_doctorname.text = data.doctor_info?.name
            itemView.tv_specialityName.text = data.doctor_info?.speciality
            itemView.txt_doctor_bigoraphy.text = data.doctor_info?.biography

        }

        itemView.tv_startConsultation.setOnClickListener {
            mDelegate.onTapStartConsultation(consultationChatId = data.consultation_id.toString(),
                consultationRequestVO = data
            )
        }

        }


}