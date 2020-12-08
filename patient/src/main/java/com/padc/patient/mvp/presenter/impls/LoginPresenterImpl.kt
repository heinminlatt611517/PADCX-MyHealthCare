package com.padc.patient.mvp.presenter.impls

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.padc.patient.R
import com.padc.patient.mvp.presenter.LoginPresenter
import com.padc.patient.mvp.view.LoginView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {

    private val mAuthenticatioModel: AuthenticationModel = AuthenticationModelImpl
    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onTapLogin(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()){
            mView?.showErrorMessage("Please enter all the fields")
        }
        else{
            mAuthenticatioModel.login(email,password,
                    onSuccess = {
                        mPatientModel.getPatientByEmail(email,onSuccess = {
                            mView?.navigateToMainScreen(it.id)
                        }, onError = {})

                    },onFailure = {
                mView?.showErrorMessage(it)
            })
        }

    }

    override fun onTapRegister() {
       mView?.navigateToRegisterScreen()
    }


}