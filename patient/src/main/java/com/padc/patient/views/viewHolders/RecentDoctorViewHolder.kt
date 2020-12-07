package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.share.data.vos.DoctorVO
import com.padc.share.views.viewHolder.BaseViewHolder

class RecentDoctorViewHolder(private val mDelegate : RecentDoctorItemDelegate,itemView: View) :BaseViewHolder<DoctorVO>(itemView) {
    override fun clickItem(it: View?) {
        mData?.dr_id?.let { it1 -> mDelegate.onTapRecentDoctorItem(it1) }
    }

    override fun bindData(data: DoctorVO) {
       mData = data
    }
}