package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.RegisterPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.RegisterPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.RegisterView
import com.padc.share.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_doctor_register.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btnRegister
import kotlinx.android.synthetic.main.activity_register.etEmail
import kotlinx.android.synthetic.main.activity_register.etPassword
import kotlinx.android.synthetic.main.activity_register.etPhone
import kotlinx.android.synthetic.main.activity_register.etUserName
import kotlinx.android.synthetic.main.activity_register.et_degree
import kotlinx.android.synthetic.main.activity_register.spinner

class RegisterActivity : BaseActivity() ,RegisterView{

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }


    private lateinit var mPresenter : RegisterPresenter
    private lateinit var deviceToken : String
    private var specialityName: String? = null

    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var gender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)

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


        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                day = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun setUpActionListener() {

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TOKEN", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result?.token
                val msg = "token: $token"
                Log.d("TOKEN", msg)

                deviceToken = token.toString()
            })


        gradio_group.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    gender = radio.text.toString()
                })

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister(this,
                    edUserName.text.toString(),
                    edEmail.text.toString(),
                    edPassword.text.toString(),
                    deviceToken,
                    specialityName.toString(),
                    edPhone.text.toString(),
                    ed_degree.text.toString(),
                    ed_biography.text.toString(),
                    ed_address.text.toString(),
                    ed_experience.text.toString(),
                    "$day $month $year",
                    gender.toString()
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