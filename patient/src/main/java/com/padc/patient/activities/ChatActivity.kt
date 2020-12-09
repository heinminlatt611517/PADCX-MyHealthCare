package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.patient.R
import com.padc.patient.mvp.presenter.ChatPresenter
import com.padc.patient.mvp.presenter.impls.ChatPresenterImpl
import com.padc.patient.mvp.view.ChatView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationRequestVO
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseActivity() , ChatView {


    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }


    private lateinit var mChatPresenter : ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpPresenter()
        setUpActionsListener()

        mChatPresenter.onUiReady(this,"1deb148d-0eed-411c-a63f-9d500808cd93")
    }

    private fun setUpActionsListener() {
        iv_sendText.setOnClickListener {
            mChatPresenter.onTapSend("", ChatMessageVO())
        }

        iv_sendText.setOnClickListener {

        }

        tv_seeMore.setOnClickListener {
            mChatPresenter.onTapSeeMore()
        }

        mChatPresenter.onUiReady(this,"")

    }

    private fun setUpPresenter() {
        mChatPresenter = ViewModelProviders.of(this).get(ChatPresenterImpl::class.java)
        mChatPresenter.initPresenter(this)
    }


    override fun displayChatMessage(messageLists: List<ChatMessageVO>) {
        Log.d("message",messageLists.size.toString())
    }

    override fun navigateToRequestPatientDataScreen() {

    }

    override fun displayPatientRequestData(data: ConsultationRequestVO) {
        bindPatientData(data)
    }

    private fun bindPatientData(data: ConsultationRequestVO) {
       pname.text = data.patient_info?.name
        pdateofBirth.text = data.patient_info?.dateOfBirth
        pheight.text = data.patient_info?.height
        pbloodtype.text = data.patient_info?.blood_type
        pallergic.text = data.patient_info?.allergic_reactions
        pweight.text = data.patient_info?.weight
        pbloodpressure.text = data.patient_info?.blood_pressure

        if (data.case_summary.size > 0){
            txt_question1.text = data.case_summary[0].question
            txt_answer1.text = data.case_summary[0].answer

            txt_question2.text = data.case_summary[1].question
            txt_answer2.text = data.case_summary[1].answer
        }


    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}