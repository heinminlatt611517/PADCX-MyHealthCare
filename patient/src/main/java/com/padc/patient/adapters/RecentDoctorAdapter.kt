package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.patient.views.viewHolders.RecentDoctorViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.DoctorVO
import com.padc.share.views.viewHolder.BaseViewHolder

class RecentDoctorAdapter(delegate : RecentDoctorItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<DoctorVO>,DoctorVO>() {
    val mDelegate  = delegate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DoctorVO> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_doctor, parent, false)
        return RecentDoctorViewHolder(mDelegate,view)
    }
}