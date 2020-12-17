package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kofigyan.stateprogressbar.StateProgressBar
import com.padc.patient.R
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.adapters.SpecialQuestionAdapter
import com.padc.patient.adapters.SpecialityDoctorAdapter
import com.padc.patient.mvp.presenter.CaseSummaryPresenter
import com.padc.patient.mvp.presenter.impls.CaseSummaryPresenterImpl
import com.padc.patient.mvp.view.CaseSummaryView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_case_summary.*
import kotlinx.android.synthetic.main.activity_case_summary_header.*
import kotlinx.android.synthetic.main.activity_case_summary_special_question.*
import kotlinx.android.synthetic.main.fragment_home.*

class CaseSummaryActivity : BaseActivity(), CaseSummaryView {

    companion object {

        private const val NAME_EXTRA = "NAME_EXTRA"
        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context, specialityName: String): Intent {
            val intent = Intent(context, CaseSummaryActivity::class.java)
            intent.putExtra(NAME_EXTRA, specialityName)
            return intent

        }
    }
    var descriptionData =
        arrayOf("အထွေထွေမေးခွန်းများ", "ရောဂါဆိုင်ရာမေးခွန်းများ")
    private lateinit var mPresenter: CaseSummaryPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary)

        setUpPresenter()

        setUpActionsListener()
        //intent.getStringExtra(ID_EXTRA)?.let { mPresenter.onUIReady(this, it) }

        mPresenter.onUIReady(this,SessionManager.patient_id.toString())
        val stateProgressBar =
            findViewById<View>(R.id.state_progress_bar) as StateProgressBar
        stateProgressBar.setStateDescriptionData(descriptionData)

    }

    private fun setUpActionsListener() {
        Log.d("SpecialityName", intent.getStringExtra(NAME_EXTRA).toString())

        Log.d("PatientCaseSummaryID", intent.getStringExtra(ID_EXTRA).toString())
        btn_continue.setOnClickListener {
            intent.getStringExtra(NAME_EXTRA)?.let { it1 ->
                mPresenter.onTapContinue(it1)
            }
        }
    }


    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(CaseSummaryPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    override fun navigateToSpecialQuestionCaseSummaryScreen(specialityName: String) {
        startActivity(CaseSummarySpecialQuestionActivity.newIntent(this, specialityName))
    }

    override fun displayPatientInfo(patientVO: PatientVO) {
        Log.d("PatientData", patientVO.toString())
        bindData(patientVO)
    }

    private fun bindData(patientVO: PatientVO) {
       tv_patientName.text = patientVO.name
        tv_patientBirthDate.text = patientVO.dateOfBirth
        tv_patientHeight.text = patientVO.height
        tv_patientBloodType.text = patientVO.blood_type
        tv_patient_allergic_reactions.text = patientVO.allergic_reactions

        ed_weight.text = Editable.Factory.getInstance().newEditable(patientVO.weight)
        ed_bloodpressure.text = Editable.Factory.getInstance().newEditable(patientVO.blood_pressure)

    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}