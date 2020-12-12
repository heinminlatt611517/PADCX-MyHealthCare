package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.ConsultationAcceptViewHolder
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.PrescribeMedicineViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.MedicineVO
import com.padc.share.views.viewHolder.BaseViewHolder

class PrescribeMedicineAdapter(delegate : PrescribeMedicineItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<MedicineVO>,MedicineVO>() {

    var mDelegate  = delegate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MedicineVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_prescribe_medicine, parent, false)
        return PrescribeMedicineViewHolder(mDelegate,view)
    }
}
