package com.padc.patient.views.viewPods

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_accept_consulation_request.view.*


class ConsultationRequestViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }



    fun setDelegate(delegate: Delegate) {
        mDelegate = delegate
    }

    private fun setUpListener() {
        tv_startConsultation.setOnClickListener {
           mDelegate?.onTapStartConsultation()
        }
    }

    interface Delegate {
       fun onTapStartConsultation()
    }

}