package com.padc.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.patient.R
import com.padc.patient.delegates.SpecialityDoctorItemDelegate
import com.padc.patient.views.viewHolders.SpecialityViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.views.viewHolder.BaseViewHolder

class SpecialityDoctorAdapter(delegate : SpecialityDoctorItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<SpecialitiesVO>,SpecialitiesVO>() {

    val mDelegate  = delegate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SpecialitiesVO> {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_speciality, parent, false)
        return SpecialityViewHolder(mDelegate,view)
    }
}