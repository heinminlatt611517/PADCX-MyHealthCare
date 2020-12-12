package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.LoginPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.LoginPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.LoginView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.DoctorVO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() ,LoginView{

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var mLoginPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getFirebaseInstanceID()
        setUpPresenter()
        setUpActionListener()

    }

    private fun setUpActionListener() {
        btn_login.setOnClickListener {
            mLoginPresenter.onTapLogin(this,etEmail.text.toString(), etPassword.text.toString(),this)
        }

        btnRegister.setOnClickListener {
            mLoginPresenter.onTapRegister()
        }
    }

    private fun setUpPresenter() {
        mLoginPresenter = ViewModelProviders.of(this).get(LoginPresenterImpl::class.java)
        mLoginPresenter.initPresenter(this)
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newIntent(this))
    }

    override fun navigateToMainScreen(doctorVO: DoctorVO) {

        SessionManager.login_status =true
        SessionManager.doctor_name = doctorVO.name
        SessionManager.doctor_id = doctorVO.id
        SessionManager.doctor_device_id = doctorVO.deviceID
        SessionManager.doctor_email = doctorVO.email.toString()
        SessionManager.doctor_photo = doctorVO.photo.toString()
        SessionManager.doctor_speciality = doctorVO.speciality.toString()
        SessionManager.doctor_phone = doctorVO.phone
        SessionManager.doctor_degree = doctorVO.degree
        SessionManager.doctor_bigraphy = doctorVO.biography


        startActivity(MainActivity.newIntent(this,"null"))
        finish()
    }

    override fun showErrorMessage(errorMessage: String) {
      showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this




}