package mk.monthlytut.patient.mvp.presenters

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.patient.mvp.presenter.ProfilePresenter
import com.padc.patient.mvp.view.ProfileView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ProfilePresenterImpl : ProfilePresenter, AbstractBasePresenter<ProfileView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val patientModel : PatientModel = PatientModelImpl
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun updateUserProfile(bitmap: Bitmap) {
        patientModel.uploadPhotoToFirebaseStorage(bitmap,
        onSuccess = {
            mView?.onTapSaveUserData()
            mAuthenticationModel.updateProfile(it,onSuccess = {}, onFailure = {})
        },
        onFailure = {
            mView?.showErrorMessage("Profile Update Failed")
        })

    }

    override fun onTapCancelUserData() {
        mView?.onTapCancelUserData()
    }

    override fun onTapEditProfileImage() {
        mView?.onTapEditProfileImage()
    }

}

