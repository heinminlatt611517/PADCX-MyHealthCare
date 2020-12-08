package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.SpecialQuestionAdapter
import com.padc.patient.mvp.presenter.CaseSummarySpecialQuestionPresenter
import com.padc.patient.mvp.presenter.impls.CaseSummarySpecialQuestionPresenterImpl
import com.padc.patient.mvp.view.CaseSummarySpecialQuestionView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.SpecialQuestionVO
import kotlinx.android.synthetic.main.activity_case_summary_special_question.*
import kotlinx.android.synthetic.main.fragment_home.*

class CaseSummarySpecialQuestionActivity : BaseActivity() ,CaseSummarySpecialQuestionView {

    companion object {

        private const val NAME_EXTRA = "NAME_EXTRA"
        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,specialityName : String,patientId : String) : Intent {
            val intent= Intent(context, CaseSummarySpecialQuestionActivity::class.java)
            intent.putExtra(NAME_EXTRA,specialityName)
            intent.putExtra(ID_EXTRA,patientId)
            return intent
        }
    }

    private lateinit var mSpecialQuestionAdapter : SpecialQuestionAdapter
    private lateinit var mSpecialQuestionPresenter : CaseSummarySpecialQuestionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary_special_question)
        setUpPresenter()
        setUpRecyclerView()

        Log.d("SpecialityName",intent.getStringExtra(NAME_EXTRA).toString())
        intent.getStringExtra(NAME_EXTRA)?.let {
            mSpecialQuestionPresenter.onUiReady(it,this)
        }


    }

    private fun setUpPresenter() {
        mSpecialQuestionPresenter = ViewModelProviders.of(this).get(CaseSummarySpecialQuestionPresenterImpl::class.java)
        mSpecialQuestionPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView() {
        rv_specialquestion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSpecialQuestionAdapter = SpecialQuestionAdapter ()
        rv_specialquestion.adapter = mSpecialQuestionAdapter
    }

    override fun navigateToConfirmCaseSummaryScreen() {

    }

    override fun displaySpecialQuestionLists(list: List<SpecialQuestionVO>) {
        Log.d("specialQuestionLists",list.size.toString())
        mSpecialQuestionAdapter.setNewData(list.toMutableList())
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}