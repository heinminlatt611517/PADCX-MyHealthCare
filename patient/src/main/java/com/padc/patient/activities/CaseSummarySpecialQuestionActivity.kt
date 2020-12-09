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
import com.padc.share.data.vos.QuestionAnswerVO
import com.padc.share.data.vos.SpecialQuestionVO
import com.padc.share.presistence.db.MyHealthCareDB
import kotlinx.android.synthetic.main.activity_case_summary_special_question.*
import kotlinx.android.synthetic.main.fragment_home.*

class CaseSummarySpecialQuestionActivity : BaseActivity() ,CaseSummarySpecialQuestionView {

    companion object {

        private const val NAME_EXTRA = "NAME_EXTRA"
        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,specialityName : String) : Intent {
            val intent= Intent(context, CaseSummarySpecialQuestionActivity::class.java)
            intent.putExtra(NAME_EXTRA,specialityName)

            return intent
        }
    }

    private lateinit var mTheDB : MyHealthCareDB

    private lateinit var mSpecialQuestionAdapter : SpecialQuestionAdapter
    private lateinit var mSpecialQuestionPresenter : CaseSummarySpecialQuestionPresenter

    private  var questionAnswerList : ArrayList<QuestionAnswerVO> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary_special_question)
        mTheDB = MyHealthCareDB.getDbInstance(this)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

        Log.d("SpecialityName",intent.getStringExtra(NAME_EXTRA).toString())
        intent.getStringExtra(NAME_EXTRA)?.let {
            mSpecialQuestionPresenter.onUiReady(it,this)
        }



    }

    private fun setUpActionListener() {
        btn_confirm.setOnClickListener {
            mSpecialQuestionPresenter.onTapStartConsultation()
        }
    }

    private fun setUpPresenter() {
        mSpecialQuestionPresenter = ViewModelProviders.of(this).get(CaseSummarySpecialQuestionPresenterImpl::class.java)
        mSpecialQuestionPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView() {
        rv_specialquestion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSpecialQuestionAdapter = SpecialQuestionAdapter (mSpecialQuestionPresenter)
        rv_specialquestion.adapter = mSpecialQuestionAdapter
    }

    override fun navigateToConfirmCaseSummaryScreen() {
        mTheDB.questionAnswerDao().deleteGeneralQuestion()
        mTheDB.questionAnswerDao().insertGeneralQuestion(questionAnswerList)
       startActivity(CaseSummaryConfirmActivity.newIntent(this,intent.getStringExtra("NAME_EXTRA").toString()))
        this.finish()
    }

    override fun displaySpecialQuestionLists(list: List<SpecialQuestionVO>) {
        Log.d("specialQuestionLists",list.size.toString())
        mSpecialQuestionAdapter.setNewData(list.toMutableList())

        for(item in list)
        {
            questionAnswerList.add(QuestionAnswerVO(item.id,item.name,""))
        }

        mSpecialQuestionAdapter.setQuestionAnswerList(questionAnswerList)
    }

    override fun replaceAnswerList(position: Int, questionAnswerVO: QuestionAnswerVO) {
        questionAnswerList[position] = questionAnswerVO

    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}