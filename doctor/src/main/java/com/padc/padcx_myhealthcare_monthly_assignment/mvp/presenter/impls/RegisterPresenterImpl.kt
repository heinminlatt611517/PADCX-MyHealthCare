package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.RegisterPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.RegisterView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.data.vos.DoctorVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mDoctorModel: DoctorModel = DoctorModelImpl


    override fun onTapRegister(doctorVO: DoctorVO, password: String) {
        if (doctorVO.email.isEmpty() || password.isEmpty() || doctorVO.name.isEmpty()) {
            mView?.showErrorMessage("Please enter all data")
        } else {

            mAuthenticationModel.register(email = doctorVO.email,
                password = password,
                userName = doctorVO.name,
                onSuccess = {

                            mDoctorModel.registerNewDoctor(doctorVO = doctorVO,
                                onSuccess = {
                                    mView?.navigateToLoginScreen()
                                },
                                onFailure = {})
                        }, onFailure = {
                    mView?.showErrorMessage("Register Fail")
                })




        }
    }
}