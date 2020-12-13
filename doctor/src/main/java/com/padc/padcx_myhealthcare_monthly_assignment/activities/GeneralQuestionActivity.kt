package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.ChatMessageAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.GeneralQuestionAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.GeneralQuestionPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.GeneralQuestionPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.GeneralQuestionView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.RelatedQuestionVO
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_general_question.*

class GeneralQuestionActivity : BaseActivity(),GeneralQuestionView {

    companion object {
        const val PARAM_CONSULTATION_CHAT_ID = " chat id"
        fun newIntent(context: Context,consultationID : String) : Intent {
            val intent = Intent(context, GeneralQuestionActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID,consultationID)
            return intent
        }
    }


    private lateinit var mPresenter : GeneralQuestionPresenter
    private lateinit var mAdapter : GeneralQuestionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_question)

        setUpPresenter()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        rv_question?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = GeneralQuestionAdapter(mPresenter)
        rv_question?.adapter = mAdapter
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(GeneralQuestionPresenterImpl::class.java)
        mPresenter.initPresenter(this)
        mPresenter.onUiReady(this,SessionManager.doctor_speciality.toString(),
        intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())
    }

    override fun displayGeneralQuestionLists(lists: List<RelatedQuestionVO>) {
        Log.d("RelatedQuestionLists",lists.size.toString())
        if (lists.isNotEmpty()){
            mAdapter.setNewData(lists.toMutableList())
        }
    }

    override fun showQuestion(question: String) {
       Toast.makeText(this,question,Toast.LENGTH_LONG).show()
    }

    override fun navigateToChatScreen() {
        startActivity(ChatActivity.newIntent(this,intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString()))
        finish()
    }

    override fun showErrorMessage(errorMessage: String) {
       showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

}