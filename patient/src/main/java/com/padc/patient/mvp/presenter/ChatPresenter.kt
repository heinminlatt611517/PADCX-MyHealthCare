package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.ChatView
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> {

    fun onUiReady(lifecycleOwner: LifecycleOwner,consultationID: String)
    fun onTapSend(consultationID : String,message : ChatMessageVO)
    fun onTapSeeMore()
    fun onTapAttach()
}