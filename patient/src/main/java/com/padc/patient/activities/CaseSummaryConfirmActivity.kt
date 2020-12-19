package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.QuestionAnswerAdapter
import com.padc.patient.mvp.presenter.CaseSummaryConfirmPresenter
import com.padc.patient.mvp.presenter.impls.CaseSummaryConfirmPresenterImpl
import com.padc.patient.mvp.view.CaseSummaryConfirmView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.QuestionAnswerVO
import kotlinx.android.synthetic.main.activity_case_summary_confirm_layout.*

class CaseSummaryConfirmActivity : BaseActivity(), CaseSummaryConfirmView {

    companion object {
        private val NAME_EXTRA = "NAME_EXTRA"
        private const val DOCTOR_EMAIL_EXTRA = "DOCTOR_EMAIL_EXTRA"
        fun newIntent(context: Context, specialityName: String,doctorEmail : String): Intent {
            val intent = Intent(context, CaseSummaryConfirmActivity::class.java)
            intent.putExtra(NAME_EXTRA, specialityName)
            intent.putExtra(DOCTOR_EMAIL_EXTRA, doctorEmail)
            return intent
        }
    }

    private var questionAnswerList: ArrayList<QuestionAnswerVO> = arrayListOf()
    private lateinit var mPresenter: CaseSummaryConfirmPresenter
    private lateinit var mAdapter: QuestionAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary_confirm_layout)

        setUpPresenter()
        setUpActionListener()
        setUpRecyclerView()

        mPresenter.onUiReady(this)

        init()
    }

    private fun init() {
        pname?.text = SessionManager.patient_name.toString()
        pdateofBirth?.text = SessionManager.patient_dateOfBirth.toString()
        pheight?.text = SessionManager.patient_height.toString() + "ft"
        pbloodtype?.text = SessionManager.patient_bloodType.toString()
        pweight?.text = SessionManager.patient_weight.toString() + " lb"
        pbloodpressure?.text = SessionManager.patient_bloodPressure.toString() + " mmHg"
        p_allegir?.text = SessionManager.patient_allegric.toString()

    }

    private fun setUpRecyclerView() {
        rc_question_answer?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = QuestionAnswerAdapter()
        rc_question_answer?.adapter = mAdapter
    }

    private fun setUpActionListener() {



        val doctorVO = DoctorVO()

        cs_btn_confirm.setOnClickListener {
            mPresenter.onTapStartConsultationButton(
                intent.getStringExtra(NAME_EXTRA).toString(),
                intent.getStringExtra(DOCTOR_EMAIL_EXTRA).toString(),
                questionAnswerList
            )
        }


    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(CaseSummaryConfirmPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun displayQuestionAnswerLists(lists: List<QuestionAnswerVO>) {
        Log.d("QuestionAnswerLists", lists.size.toString())
        questionAnswerList.addAll(lists)
        mAdapter.setNewData(lists.toMutableList())
    }

    override fun navigateToHomeScreen() {
        startActivity(MainActivity.newIntent(this))
        this.finish()
    }

    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}