package com.padc.patient.mvp.presenter


import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.ProfileView
import com.padc.share.mvp.presenter.BasePresenter


interface ProfilePresenter : BasePresenter<ProfileView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner)

    fun updateUserData(
        bitmap: Bitmap,
        blood_type: String,
        dateofbirth: String,
        height: String,
        comment: String,
        phone: String,
        address: String,
        lifecycleOwner: LifecycleOwner
    )

    fun onTapLogOut()
    fun onTapEdit()
}