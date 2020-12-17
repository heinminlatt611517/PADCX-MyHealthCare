package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.content.Context
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.RegisterPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.RegisterView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import java.util.*

class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mDoctorModel: DoctorModel = DoctorModelImpl
//    override fun onTapRegister(
//            username: String,
//            email: String,
//            password: String,
//            token: String,
//            speciality_name: String,
//            phone: String,
//            degree: String
//
//    ) {
//        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || speciality_name.isEmpty() ||
//                phone.isEmpty() || degree.isEmpty()) {
//            mView?.showErrorMessage("Please enter all fields")
//        } else {
//            val doctorVO = DoctorVO(
//                    id = UUID.randomUUID().toString(),
//                    name = username,
//                    email = email,
//                    deviceID = token,
//                    degree = degree,
//                    phone = phone,
//                    speciality = speciality_name
//            )
//
//            mAuthenticationModel.register(email, password, username, onSuccess = {
//                mDoctorModel.registerNewDoctor(doctorVO = doctorVO, onSuccess = {
//
//                    mView?.navigateToLoginScreen()
//
//                }, onFailure = {})
//            }, onFailure = {
//                mView?.showErrorMessage(it)
//            })
//
//        }
//    }

    override fun onTapRegister(context: Context, username: String, email: String, password: String, token: String,
                               speciality_name: String, phone: String, degree: String, biography: String,
                               address: String, experience: String, dateOfBirth: String, gender: String) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || speciality_name.isEmpty() ||
                phone.isEmpty() || degree.isEmpty() || biography.isEmpty()) {
            mView?.showErrorMessage("Please enter all fields")
        } else {

            val doctorVO = DoctorVO(
                    id = UUID.randomUUID().toString(),
                    name = username,
                    email = email,
                    deviceID = token,
                    biography = biography,
                    degree = degree,
                    phone = phone,
                    speciality = speciality_name,
                    experience = experience,
                    dateofBirth = dateOfBirth,
                    gender = gender,
                    address = address
            )

            mAuthenticationModel.register(email, password, username, onSuccess = {
                mDoctorModel.registerNewDoctor(doctorVO = doctorVO, onSuccess = {

                    mView?.navigateToLoginScreen()

                }, onFailure = {})
            }, onFailure = {
                mView?.showErrorMessage(it)
            })
        }


    }
}