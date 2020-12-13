package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.GeneralQuestionItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.PrescribeMedicineItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.GeneralQuestionViewHolder
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.MedicineViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.MedicineVO
import com.padc.share.data.vos.RelatedQuestionVO
import com.padc.share.views.viewHolder.BaseViewHolder

class GeneralQuestionAdapter(delegate: GeneralQuestionItemDelegate) :
    BaseRecyclerAdapter<BaseViewHolder<RelatedQuestionVO>,RelatedQuestionVO>() {

    val mDelegate = delegate

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RelatedQuestionVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_general_question, parent, false)
        return GeneralQuestionViewHolder(mDelegate,view)
    }
}
