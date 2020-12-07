package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.delegates.SpecialityDoctorItemDelegate
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.views.viewHolder.BaseViewHolder

class SpecialityViewHolder(private val mDelegate : SpecialityDoctorItemDelegate,itemView: View) : BaseViewHolder<SpecialitiesVO>(itemView) {
    override fun clickItem(it: View?) {
       mData?.let { mDelegate.onTapSpecialityDoctorItem(it) }
    }

    override fun bindData(data: SpecialitiesVO) {
        mData = data
    }
}