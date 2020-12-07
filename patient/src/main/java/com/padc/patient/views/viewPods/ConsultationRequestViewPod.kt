package com.padc.patient.views.viewPods

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

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

    }

    interface Delegate {

    }

}