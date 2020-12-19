package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FieldValue
import com.padc.patient.R
import com.padc.patient.adapters.ChatMessageAdapter
import com.padc.patient.mvp.presenter.ChatPresenter
import com.padc.patient.mvp.presenter.impls.ChatPresenterImpl
import com.padc.patient.mvp.view.ChatView
import com.padc.patient.utils.SessionManager
import com.padc.patient.views.viewPods.RecommendMedicineViewPod
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.*
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : BaseActivity(), ChatView {


    companion object {
        const val PARAM_CONSULTATION_CHAT_ID = " chat id"
        fun newIntent(
            context: Context,
            consultation_chat_id: String
        ): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID, consultation_chat_id)
            return intent
        }

    }

    var isShow =false
    var finishConservationStatus =false
    private lateinit var mChatPresenter: ChatPresenter
    private lateinit var mChatMessageAdapter: ChatMessageAdapter
    private lateinit var mRecommendMedicineViewPod: RecommendMedicineViewPod


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpPresenter()
        setUpSwipeRefresh()
        setUpActionsListener()
        setUpRecyclerView()

        mChatPresenter.onUiReady(this, intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())
    }

    override fun onResume() {
        super.onResume()
        mChatPresenter.onUiReady(this, intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())
    }

    private fun setUpSwipeRefresh() {

    }

    private fun setUpRecyclerView() {
        rv_chatMessage?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mChatMessageAdapter = ChatMessageAdapter()
        rv_chatMessage?.adapter = mChatMessageAdapter

    }

    private fun setUpActionsListener() {
        iv_attach.setOnClickListener {
            mChatPresenter.onTapAttach()
        }

        iv_sendText.setOnClickListener {
            if (ed_text_message.text.toString() != "") {
                mChatPresenter.onTapSend(
                    intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString(),
                    message = ChatMessageVO(
                        UUID.randomUUID().toString(),
                        DateUtils().getCurrentDateTime(), ed_text_message.text.toString(), "",
                        SenderTypeVO(
                            UUID.randomUUID().toString(),
                            "patient", ""
                        )
                    )
                )
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

    }

    override fun displayPatientRequestData(data: ConsultationChatVO) {
        bindPatientData(data)
    }


    override fun navigateToOrderPrescriptionScreen() {
        startActivity(
            OrderPrescriptionActivity.newIntent(
                this, intent.getStringExtra(
                    PARAM_CONSULTATION_CHAT_ID
                ).toString()
            )
        )
    }

    override fun displayPrescriptionLists(lists: List<PrescriptionVO>) {
        if (lists.isNotEmpty()) {
            prescribe_medicine_view_pod.visibility = View.VISIBLE
            mRecommendMedicineViewPod = prescribe_medicine_view_pod as RecommendMedicineViewPod

            mRecommendMedicineViewPod.setDelegate(mChatPresenter)

                mRecommendMedicineViewPod.setPrescriptionData(
                    lists,
                    SessionManager.patient_photo.toString())

//            if(finishConservationStatus) {
//                prescribe_medicine_view_pod.visibility = View.VISIBLE
//            }else{
//                prescribe_medicine_view_pod.visibility = View.GONE
//            }
        }
    }

    override fun enableSwipeRefresh() {

    }

    override fun disableSwipeRefresh() {

    }

    private fun bindPatientData(data: ConsultationChatVO) {
        isShow= true
        pname.text = data.patient_info?.name
        pdateofBirth.text = data.patient_info?.dateOfBirth
        pheight.text = data.patient_info?.height
        pbloodtype.text = data.patient_info?.blood_type
        pallergic.text = data.patient_info?.allergic_reactions
        pweight.text = data.patient_info?.weight
        pbloodpressure.text = data.patient_info?.blood_pressure

        data?.let {
            txt_question1.text = data.case_summary?.get(0)?.question
            txt_answer1.text = data.case_summary?.get(0)?.answer

            txt_question2.text = data.case_summary?.get(1)?.question
            txt_answer2.text = data.case_summary?.get(1)?.answer
        }

//        finishConservationStatus = data.finish_flag
//        if(finishConservationStatus) {
//            prescribe_medicine_view_pod.visibility = View.VISIBLE
//        }else{
//            prescribe_medicine_view_pod.visibility = View.GONE
//        }


    }

    override fun showErrorMessage(errorMessage: String) {
        showErrorMessage(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}