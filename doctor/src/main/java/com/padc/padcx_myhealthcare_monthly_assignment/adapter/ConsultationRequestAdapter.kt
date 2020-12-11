package com.padc.padcx_myhealthcare_monthly_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.delegate.ConsultationRequestItemDelegate
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.ConsultationRequestViewHolder
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewHolders.QuestionAnswerViewHolder
import com.padc.share.adapters.BaseRecyclerAdapter
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.ConsultedPatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.views.viewHolder.BaseViewHolder

class ConsultationRequestAdapter(delegate : ConsultationRequestItemDelegate) : BaseRecyclerAdapter<BaseViewHolder<ConsultationRequestVO>,ConsultationRequestVO>() {

    var mDelegate  = delegate

    var consulted_patient : List<ConsultedPatientVO> = arrayListOf()

    fun setConsultedPatientList(list: List<ConsultedPatientVO>)
    {
        consulted_patient =list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationRequestVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_consultation_request_item, parent, false)
        return ConsultationRequestViewHolder(consulted_patient,mDelegate,view)
    }
}