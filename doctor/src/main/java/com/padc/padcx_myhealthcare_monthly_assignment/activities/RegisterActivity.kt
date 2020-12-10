package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.iid.FirebaseInstanceId
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.RegisterPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.RegisterPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.RegisterView
import com.padc.share.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() ,RegisterView{

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }


    private lateinit var mPresenter : RegisterPresenter
    private lateinit var token : String
    private var specialityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpPresenter()
        setUpSpinnerItemSelectedListeners()
        setUpActionListener()

    }

    private fun setUpSpinnerItemSelectedListeners() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                specialityName = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setUpActionListener() {

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d("fbToken", it.token)
            token =it.token
        }

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister(
                etUserName.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                token,
                specialityName.toString(),
                etPhone.text.toString(),
                et_degree.text.toString()
            )
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(RegisterPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun navigateToLoginScreen() {
        startActivity(LoginActivity.newIntent(this))
        this.finish()
    }

    override fun showErrorMessage(errorMessage: String) {
       showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner  = this


}