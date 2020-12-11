package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ChatView
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> {

    fun onUiReady(lifecycleOwner: LifecycleOwner, consultationID: String)
    fun onTapSend(consultationID : String,message : ChatMessageVO)
    fun onTapAttach()
    fun onTapSeeMore()
    fun onTapQuestion()
    fun onTapPrescribeMedicine()
    fun onTapMedicineHistory()

}