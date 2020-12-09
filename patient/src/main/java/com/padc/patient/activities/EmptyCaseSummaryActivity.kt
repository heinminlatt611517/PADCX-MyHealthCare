package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.patient.R
import com.padc.patient.mvp.presenter.EmptyCaseSummaryPresenter
import com.padc.patient.mvp.presenter.impls.EmptyCaseSummaryPresenterImpl
import com.padc.patient.mvp.view.EmptyCaseSummaryView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_empty_case_summary.*

class EmptyCaseSummaryActivity : BaseActivity(), EmptyCaseSummaryView {

    companion object {
        private val NAME_EXTRA = "NAME_EXTRA"
        fun newIntent(context: Context, specialityName: String): Intent {
            val intent = Intent(context, EmptyCaseSummaryActivity::class.java)
            intent.putExtra(NAME_EXTRA, specialityName)
            return intent
        }
    }

    private var email: String? = null
    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var bloodType: String? = null


    private lateinit var mPresenter: EmptyCaseSummaryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_case_summary)

        setUpPresenter()
        setUpItemSelectedListener()
        setUpActionListener()
    }

    private fun setUpActionListener() {
        btn_next.setOnClickListener {
            mPresenter.onTapContinue(
                PatientVO(
                    SessionManager.patient_id.toString(),
                    SessionManager.patient_name.toString(),
                    SessionManager.patient_email.toString(),
                    SessionManager.patient_device_id,
                    SessionManager.patient_photo,
                    bloodType,
                    ed_bloodpressure.text.toString(),
                    arrayListOf(),
                    ed_weight.text.toString(),
                    ed_height.text.toString(),
                    "$day/$month/$year",
                    ed_allergic_medicine.text.toString(),
                    arrayListOf()
                ),
                this
            )


        }
    }

    private fun setUpItemSelectedListener() {
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
        bloodtype_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bloodType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(EmptyCaseSummaryPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    override fun savePatientDataAndNavigateToCaseSummaryScreen(
        patientVO: PatientVO,
        speciality: String
    ) {
        SessionManager.login_status = true
        SessionManager.patient_name = patientVO.name
        SessionManager.patient_id = patientVO.id
        SessionManager.patient_device_id = patientVO.deviceID
        SessionManager.patient_email = patientVO.email
        SessionManager.patient_photo = patientVO.photo.toString()
        SessionManager.patient_dateOfBirth = patientVO.dateOfBirth
        SessionManager.patient_height = patientVO.height
        SessionManager.patient_bloodType = patientVO.blood_type
        SessionManager.patient_allegric = patientVO.allergic_reactions
        SessionManager.patient_weight = patientVO.weight
        SessionManager.patient_bloodPressure = patientVO.blood_pressure

        startActivity(
            CaseSummaryActivity.newIntent(
                this,
                intent.getStringExtra(NAME_EXTRA).toString()
            )
        )

        this.finish()
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}