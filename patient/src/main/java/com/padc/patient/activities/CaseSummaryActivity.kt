package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.adapters.SpecialQuestionAdapter
import com.padc.patient.adapters.SpecialityDoctorAdapter
import com.padc.patient.mvp.presenter.CaseSummaryPresenter
import com.padc.patient.mvp.presenter.impls.CaseSummaryPresenterImpl
import com.padc.patient.mvp.view.CaseSummaryView
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_case_summary.*
import kotlinx.android.synthetic.main.activity_case_summary_special_question.*
import kotlinx.android.synthetic.main.fragment_home.*

class CaseSummaryActivity : AppCompatActivity(),CaseSummaryView {

    companion object {

        private const val NAME_EXTRA = "NAME_EXTRA"
        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,specialityName : String,patientID : String) : Intent {
            val intent = Intent(context, CaseSummaryActivity::class.java)
            intent.putExtra(NAME_EXTRA,specialityName)
            intent.putExtra(ID_EXTRA,patientID)
            return intent

        }
    }

    private lateinit var mPresenter : CaseSummaryPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary)

        setUpPresenter()

        setUpActionsListener()
        intent.getStringExtra(ID_EXTRA)?.let { mPresenter.onUIReady(this, it) }
    }

    private fun setUpActionsListener() {
        Log.d("SpecialityName",intent.getStringExtra(NAME_EXTRA).toString())
       btn_continue.setOnClickListener {
           intent.getStringExtra(NAME_EXTRA)?.let { it1 ->
               intent.getStringExtra(ID_EXTRA)?.let { mPresenter.onTapContinue(it1,it) } }
       }
    }



    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(CaseSummaryPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }




    override fun navigateToSpecialQuestionCaseSummaryScreen(specialityName: String, patientID: String) {
       startActivity(CaseSummarySpecialQuestionActivity.newIntent(this,specialityName,patientID))
    }

    override fun displayPatientInfo(patientVO: PatientVO) {

    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}