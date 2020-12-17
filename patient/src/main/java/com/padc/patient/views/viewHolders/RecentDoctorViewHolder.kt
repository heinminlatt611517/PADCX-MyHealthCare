package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.DoctorVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.item_accept_consulation_request.view.*
import kotlinx.android.synthetic.main.list_item_recent_doctor.view.*
import kotlinx.android.synthetic.main.list_item_speciality.view.*

class RecentDoctorViewHolder(private val mDelegate : RecentDoctorItemDelegate,itemView: View) :BaseViewHolder<DoctorVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.let { it1 -> mDelegate.onTapRecentDoctorItem(it1) }
    }

    override fun bindData(data: DoctorVO) {
        mData = data

        data?.let {
            itemView.tv_doctorName.text = data?.name
            itemView.tv_doctor_specialityName.text = data?.speciality
            data?.photo?.let {
                ImageUtils().showImage(itemView.iv_doctor, it, R.drawable.sample_doctor)
            }
        }
    }
}