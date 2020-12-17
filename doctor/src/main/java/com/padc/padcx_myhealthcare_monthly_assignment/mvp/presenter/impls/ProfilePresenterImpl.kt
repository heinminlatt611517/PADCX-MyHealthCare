package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ProfilePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ProfileView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ProfilePresenterImpl : ProfilePresenter,AbstractBasePresenter<ProfileView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mDoctorModel: DoctorModel = DoctorModelImpl
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

        mDoctorModel.getDoctorByEmail(SessionManager.doctor_email.toString(),onSuccess = {}, onError = {})
        mDoctorModel.getDoctorByEmailFromDB(SessionManager.doctor_email.toString())
            .observe(lifecycleOwner, Observer { data ->
                data?.let {
                    mView?.displayDoctorData(data) }
            })

    }

    override fun onTapEditProfile() {
        mView?.navigateToEditProfileScreen()
    }

    override fun updateUserData(
        bitmap: Bitmap,
        specialityName: String,
        speciality: String,
        phone: String,
        degree: String,
        biography: String,
        address: String,
        experience: String,
        dateOfBirth: String,
        gender: String
    ) {

        mDoctorModel.uploadPhotoToFirebaseStorage(bitmap,
            onSuccess = {
                mAuthenticationModel.updateProfile(it,onSuccess = {}, onFailure = {})

                mView?.hideProgressDialog()

                var doctorVO = DoctorVO(
                    id= SessionManager.doctor_id.toString(),
                    deviceID = SessionManager.doctor_device_id.toString(),
                    name = SessionManager.doctor_name.toString(),
                    email = SessionManager.doctor_email.toString(),
                    photo = it,
                    speciality = speciality,
                    phone = phone,
                    dateofBirth = dateOfBirth,
                    degree = degree,
                    biography = biography,
                    address = address,
                    experience = experience,
                    gender = gender
                )

            },
            onFailure = {
                mView?.showErrorMessage("Profile Update Failed")
            })

    }


}