package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.patient.R
import com.padc.patient.fragments.HomeFragment
import com.padc.patient.mvp.presenter.LoginPresenter
import com.padc.patient.mvp.presenter.impls.LoginPresenterImpl
import com.padc.patient.mvp.view.LoginView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(),LoginView {

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var mPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpPresenter()
        setUpActionListener()

    }

    private fun setUpActionListener() {
        btn_login.setOnClickListener {
            mPresenter.onTapLogin(etEmail.text.toString(), etPassword.text.toString(),this)
        }

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister()
        }
    }

    private fun setUpPresenter() {
         mPresenter= ViewModelProviders.of(this).get(LoginPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newIntent(this))

    }

    override fun navigateToMainScreen(patientVO: PatientVO) {

        SessionManager.login_status =true
        SessionManager.patient_name = patientVO.name
        SessionManager.patient_id = patientVO.id
        SessionManager.patient_device_id = patientVO.deviceID
        SessionManager.patient_email = patientVO.email
        SessionManager.patient_photo = patientVO.photo.toString()
        SessionManager.patient_dateOfBirth =patientVO.dateOfBirth
        SessionManager.patient_height = patientVO.height
        SessionManager.patient_bloodType = patientVO.blood_type

        SessionManager.patient_weight = patientVO.weight
        SessionManager.patient_bloodPressure = patientVO.blood_pressure
        startActivity(MainActivity.newIntent(this))
        this.finish()
    }



    override fun showErrorMessage(errorMessage: String) {
      showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}