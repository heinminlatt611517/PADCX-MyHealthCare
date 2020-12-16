package com.padc.padcx_myhealthcare_monthly_assignment.views.viewPods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.share.data.vos.PrescriptionVO
import kotlinx.android.synthetic.main.recommend_mecidine_for_chat_viewpod.view.*

class RecommendMedicineViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()

    }

    fun setPrescriptionData(prescription : List<PrescriptionVO>, doctorPhoto: String) {

        var str : String = ""
        if(prescription.isNotEmpty())
        {
            for( item in prescription)
            {
                str += item.medicine +"\n"
            }
        }
        tv_medicine_list_name.text = str.toString()
    }

    private fun setUpListener() {

    }

    interface Delegate {
        fun onTapPrescription()
    }

}