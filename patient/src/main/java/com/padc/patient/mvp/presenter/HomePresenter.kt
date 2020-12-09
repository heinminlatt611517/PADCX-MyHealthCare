package com.padc.patient.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.patient.delegates.RecentDoctorItemDelegate
import com.padc.patient.delegates.SpecialityDoctorItemDelegate
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.views.viewPods.ConsultationRequestViewPod
import com.padc.share.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,RecentDoctorItemDelegate ,SpecialityDoctorItemDelegate,ConsultationRequestViewPod.Delegate{
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun onTapConfirm(
        specialityName: String,
        dialogFragment: ConfirmDialogFragment
    )
}