package com.padc.patient.mvp.presenter


import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.view.ProfileView
import com.padc.share.mvp.presenter.BasePresenter



interface ProfilePresenter : BasePresenter<ProfileView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun updateUserProfile(bitmap: Bitmap)
    fun onTapCancelUserData()
    fun onTapEditProfileImage()
}