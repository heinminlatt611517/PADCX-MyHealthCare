package com.padc.patient.mvp.presenter.impls

import com.padc.patient.mvp.presenter.RegisterPresenter
import com.padc.patient.mvp.view.RegisterView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onTapRegister(patientVO: PatientVO, password: String) {
        if (patientVO.email.isEmpty() || password.isEmpty() || patientVO.name.isEmpty()) {
            mView?.showErrorMessage("Please enter all data")
        } else {
            mAuthenticationModel.register(
                email = patientVO.email,
                password = password,
                userName = patientVO.name
                ,
                onSuccess = {
                    mPatientModel.registerNewPatient(
                        email = patientVO.email,
                        password = password,
                        userName = patientVO.name,
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