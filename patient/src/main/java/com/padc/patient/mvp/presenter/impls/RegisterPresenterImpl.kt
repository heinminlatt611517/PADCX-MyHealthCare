package com.padc.patient.mvp.presenter.impls

import android.content.Context
import com.padc.patient.mvp.presenter.RegisterPresenter
import com.padc.patient.mvp.view.RegisterView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.AbstractBasePresenter
import java.util.*

class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mPatientModel: PatientModel = PatientModelImpl


    override fun onTapRegister(context: Context, username: String, email: String, password: String, token: String) {
        if(email.isEmpty() || password.isEmpty() || username.isEmpty()){
            mView?.showErrorMessage("Please enter all fields")
        } else {

            val patientVO = PatientVO(
                    id = UUID.randomUUID().toString(),
                    name = username,
                    email = email,
                    deviceID = token
            )
            mAuthenticationModel.register(email,password,username, onSuccess = {

                mPatientModel.registerNewPatient(patientVO,
                        onSuccess = {
                    mView?.navigateToLoginScreen()

                },onFailure = {
                    mView?.showErrorMessage(it)
                } )

            }, onFailure = {
                mView?.showErrorMessage(it)
            })

        }
    }

}