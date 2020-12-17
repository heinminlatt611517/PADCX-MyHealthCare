package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.QuestionAnswerAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PatientCaseSummaryPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.PatientCaseSummaryPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PatientCaseSummaryView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.PatientVO
import kotlinx.android.synthetic.main.activity_patient_case_summary.*

class PatientCaseSummaryActivity : BaseActivity(),PatientCaseSummaryView {

    companion object {

        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context, requestID : String) : Intent {
            val intent = Intent(context,
                PatientCaseSummaryActivity::class.java)
            intent.putExtra(ID_EXTRA,requestID)
            return intent
        }

    }

    private lateinit var mAdapter : QuestionAnswerAdapter
    private lateinit var mPresenter : PatientCaseSummaryPresenter
    private lateinit var mConsultationRequestVO: ConsultationRequestVO

    private lateinit var consultation_request_id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_case_summary)

        consultation_request_id = intent.getStringExtra(ID_EXTRA).toString()

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

        mPresenter.onUiReady(this,consultation_request_id)

    }

    private fun setUpActionListener() {
        btn_start_consultation.setOnClickListener {
            if(mConsultationRequestVO != null) {
                mPresenter.onTapStartConsultation(mConsultationRequestVO)
            }
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PatientCaseSummaryPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView() {
        rc_question_answer?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = QuestionAnswerAdapter()
        rc_question_answer?.adapter = mAdapter
    }

    override fun displayPatientData(consultationRequestVO: ConsultationRequestVO) {
        Log.d("PatientData",consultationRequestVO.toString())
        bindRequestPatientData(consultationRequestVO)
        mConsultationRequestVO = consultationRequestVO
        mAdapter.setNewData(consultationRequestVO.case_summary.toMutableList())



    }

    override fun navigateToChatScreen(consultationID: String, consultationRequestID: String) {
        startActivity(ChatActivity.newIntent(this,consultationID))
    }




    private fun bindRequestPatientData(consultationRequestVO: ConsultationRequestVO) {
        pname?.text = consultationRequestVO.patient_info?.name
        pdateofBirth?.text = consultationRequestVO.patient_info?.dateOfBirth
        pheight?.text = consultationRequestVO.patient_info?.height + "ft"
        pbloodtype?.text = consultationRequestVO.patient_info?.blood_type
        pweight?.text = consultationRequestVO.patient_info?.weight + " lb"
        pbloodpressure?.text = consultationRequestVO.patient_info?.blood_pressure + " mmHg"
        p_allegir?.text = consultationRequestVO.patient_info?.allergic_reactions

    }

    override fun showErrorMessage(errorMessage: String) {
       showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this



}