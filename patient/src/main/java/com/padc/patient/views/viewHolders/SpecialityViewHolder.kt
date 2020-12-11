package com.padc.patient.views.viewHolders

import android.view.View
import com.padc.patient.R
import com.padc.patient.delegates.SpecialityDoctorItemDelegate
import com.padc.share.data.vos.SpecialitiesVO
import com.padc.share.utils.ImageUtils
import com.padc.share.views.viewHolder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_speciality.view.*

class SpecialityViewHolder(private val mDelegate : SpecialityDoctorItemDelegate,itemView: View) : BaseViewHolder<SpecialitiesVO>(itemView) {
    override fun clickItem(it: View?) {
       mData?.let { mDelegate.onTapSpecialityDoctorItem(it.name) }
    }

    override fun bindData(data: SpecialitiesVO) {
        mData = data

        data?.let {
            itemView.txt_specialityname.text =data.name
            data?.photo?.let{
                ImageUtils().showImage(itemView.img_speciality,it,  R.drawable.speciality_thumbnail)
            }

        }

    }
}