package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.GsonBuilder
import com.padc.patient.R
import com.padc.patient.fragments.HomeFragment
import com.padc.patient.mvp.presenter.LoginPresenter
import com.padc.patient.mvp.presenter.impls.LoginPresenterImpl
import com.padc.patient.mvp.view.LoginView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.FacebookUser
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_patient_login.*
import java.util.*

class LoginActivity : BaseActivity(), LoginView {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var mPresenter: LoginPresenter
    private var callbackManager: CallbackManager? = null
    private var profileTracker: ProfileTracker? = null
    lateinit var user: FacebookUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_login)

        printHashKey(this)
        setUpPresenter()
        setUpActionListener()
        checkAlreadyLogin()
    }

    private fun setUpActionListener() {
        btn_login.setOnClickListener {
            mPresenter.onTapLogin(etEmail.text.toString(), etPassword.text.toString(), this)
        }

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister()
        }

        btSignInWithFacebook.setOnClickListener(View.OnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                listOf<String>("public_profile", "email")
            )
        })

    }

    private fun checkAlreadyLogin() {
        if (isConnected()) {
            FacebookSdk.sdkInitialize(applicationContext)
            if (isFacebookLoggedIn()) {
                // navigateToMain
                //  requestUserData()
            } else {

                callbackManager = CallbackManager.Factory.create()

                assignListener()

                profileTracker = object : ProfileTracker() {
                    override fun onCurrentProfileChanged(
                        oldProfile: Profile?,
                        newProfile: Profile?
                    ) {
                        profileTracker?.stopTracking()
                        Profile.setCurrentProfile(newProfile)
                    }
                }
                (profileTracker as ProfileTracker).startTracking()

            }
        }
    }

    private fun assignListener() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    requestUserData()
                    Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {

                    Toast.makeText(applicationContext, "Login fail", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun requestUserData() {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { `object`, response ->
            val resp = response.jsonObject
            val gson = GsonBuilder().create()
            user = gson.fromJson<FacebookUser>(resp.toString(), FacebookUser::class.java)

            Log.d("User data", user.toString())

        }
        val parameters = Bundle()
        parameters.putString("fields", "id,first_name,last_name,email,picture")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun isFacebookLoggedIn(): Boolean {
        return AccessToken.getCurrentAccessToken() != null
    }

    private fun isConnected(): Boolean {

        var connected = false
        try {
            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            e.message?.let { Log.e("Connectivity Exception", it) }
        }

        return connected
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(LoginPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newIntent(this))

    }

    override fun navigateToMainScreen(patientVO: PatientVO) {

        SessionManager.login_status = true
        SessionManager.patient_name = patientVO.name
        SessionManager.patient_id = patientVO.id
        SessionManager.patient_device_id = patientVO.deviceID
        SessionManager.patient_email = patientVO.email
        SessionManager.patient_photo = patientVO.photo
        SessionManager.patient_dateOfBirth = patientVO.dateOfBirth
        SessionManager.patient_height = patientVO.height
        SessionManager.patient_bloodType = patientVO.blood_type
        SessionManager.patient_phone = patientVO.phone
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