package com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.LoginPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.LoginView
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.DoctorModelImpl
import com.padc.share.mvp.presenter.AbstractBasePresenter

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {

    private val mAuthenticatioModel: AuthenticationModel = AuthenticationModelImpl

    private val mDoctorModel : DoctorModel = DoctorModelImpl

    override fun onTapLogin(
        context: Context,
        email: String,
        password: String,
        lifecycleOwner: LifecycleOwner
    ) {
        if(email.isEmpty() || password.isEmpty()){
            mView?.showErrorMessage("Please enter all the fields")
        } else {
            mAuthenticatioModel.login(email, password, onSuccess = {
                mDoctorModel.getDoctorByEmail(email,onSuccess = {}, onError = {})
                mDoctorModel.getDoctorByEmailFromDB(email)
                    .observe(lifecycleOwner, Observer { doctor ->
                        doctor?.let {
                            mView?.navigateToMainScreen(doctor) }
                    })

            }, onFailure = {
                mView?.showErrorMessage(it)
            })
        }
    }

    override fun onTapRegister() {
       mView?.navigateToRegisterScreen()
    }

}