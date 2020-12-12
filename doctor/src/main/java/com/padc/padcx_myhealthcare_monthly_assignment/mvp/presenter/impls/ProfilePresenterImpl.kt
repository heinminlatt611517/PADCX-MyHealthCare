package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ProfilePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ProfileView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ProfilePresenterImpl : ProfilePresenter,AbstractBasePresenter<ProfileView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val patientModel : PatientModel = PatientModelImpl
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun updateUserProfile(bitmap: Bitmap) {
        patientModel.uploadPhotoToFirebaseStorage(bitmap,
            onSuccess = {
                mView?.saveUserData()
                mAuthenticationModel.updateProfile(it,onSuccess = {}, onFailure = {})
            },
            onFailure = {
                mView?.showErrorMessage("Profile Update Failed")
            })

    }

    override fun onTapCancelUserData() {

    }

    override fun onTapEditProfileImage() {
        mView?.editProfileImage()
    }

}