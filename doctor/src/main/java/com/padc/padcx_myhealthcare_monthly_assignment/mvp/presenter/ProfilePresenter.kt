package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ProfileView
import com.padc.share.mvp.presenter.BasePresenter

interface ProfilePresenter : BasePresenter<ProfileView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner)

    fun onTapEditProfile()

    fun updateUserData(bitmap: Bitmap,
                       speciality : String,
                       phone : String, degree : String,
                       biography : String, address : String,
                       experience : String, dateOfBirth : String,
                       gender: String)
}
