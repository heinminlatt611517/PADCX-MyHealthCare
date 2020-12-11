package com.padc.patient.activities

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.ChatMessageAdapter
import com.padc.patient.mvp.presenter.ChatPresenter
import com.padc.patient.mvp.presenter.impls.ChatPresenterImpl
import com.padc.patient.mvp.view.ChatView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.SenderTypeVO
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.util.*

class ChatActivity : BaseActivity(), ChatView {


    companion object {
        const val PARAM_CONSULTATION_CHAT_ID = " chat id"
        fun newIntent(
            context: Context,
            consultation_chat_id : String
        ) : Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID, consultation_chat_id)
            return intent
        }

    }

    private lateinit var mChatPresenter: ChatPresenter
    private lateinit var mChatMessageAdapter : ChatMessageAdapter



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpPresenter()
        setUpActionsListener()
        setUpRecyclerView()

        mChatPresenter.onUiReady(this, intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())
    }

    private fun setUpRecyclerView() {
        rv_chatMessage?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mChatMessageAdapter =  ChatMessageAdapter()
        rv_chatMessage?.adapter = mChatMessageAdapter


    }

    private fun setUpActionsListener() {
        iv_attach.setOnClickListener {
            mChatPresenter.onTapAttach()
        }

        iv_sendText.setOnClickListener {
            if (!ed_text_message.text?.equals("")!!) {
                mChatPresenter.onTapSend( intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString(),
                        message = ChatMessageVO(UUID.randomUUID().toString(),
                                "", ed_text_message.text.toString(), "",
                                SenderTypeVO(UUID.randomUUID().toString(),
                                        "patient","")))
                ed_text_message.text = Editable.Factory.getInstance().newEditable("")

            }
        }

        tv_seeMore.setOnClickListener {
            mChatPresenter.onTapSeeMore()
        }


    }

    private fun setUpPresenter() {
        mChatPresenter = ViewModelProviders.of(this).get(ChatPresenterImpl::class.java)
        mChatPresenter.initPresenter(this)
    }


    override fun displayChatMessage(messageLists: List<ChatMessageVO>) {
        Log.d("message", messageLists.size.toString())
        for (i in messageLists) {
            Log.d("messageName", i.messageText)
        }

       mChatMessageAdapter.setTypeListList(messageLists)

        mChatMessageAdapter.setNewData(messageLists.toMutableList())

    }

    override fun navigateToRequestPatientDataScreen() {
       startActivity(RequestPatientDataActivity.newIntent(this))
        this.finish()
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

        if (data.case_summary.size > 0) {
            txt_question1.text = data.case_summary[0].question
            txt_answer1.text = data.case_summary[0].answer

            txt_question2.text = data.case_summary[1].question
            txt_answer2.text = data.case_summary[1].answer
        }


    }

    override fun showErrorMessage(errorMessage: String) {
       showErrorMessage(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}