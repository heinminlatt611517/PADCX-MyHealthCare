package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.ChatMessageAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ChatPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.ChatPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ChatView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.padcx_myhealthcare_monthly_assignment.views.viewPods.RecommendMedicineViewPod
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.*
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

    private lateinit var mChatPresenter: ChatPresenter
    private lateinit var mChatMessageAdapter: ChatMessageAdapter
    private lateinit var mRecommendMedicineViewPod: RecommendMedicineViewPod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpPresenter()
        setUpSwipeRefresh()
        setUpRecyclerView()
        setUpActionsListener()

        mChatPresenter.onUiReady(this, intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())

    }

    private fun setUpSwipeRefresh() {
//        swipeRefreshLayout.setOnRefreshListener {
//            mChatPresenter.onSwipeRefresh(this,intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString())
//        }
    }

    private fun setUpActionsListener() {

        iv_attach.setOnClickListener {
            mChatPresenter.onTapAttach()
        }


        iv_sendText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

        iv_sendText.setOnClickListener {
            if (!ed_text_message.text?.equals("")!!) {
                mChatPresenter.onTapSend(
                        intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString(),
                        message = ChatMessageVO(
                                UUID.randomUUID().toString(),
                                com.padc.share.utils.DateUtils().getCurrentDateTime(), ed_text_message.text.toString(), "",
                                SenderTypeVO(
                                        UUID.randomUUID().toString(),
                                        "doctor", ""
                                )
                        )
                )
                ed_text_message.text = Editable.Factory.getInstance().newEditable("")

            }
        }

        tv_seeMore.setOnClickListener {
            mChatPresenter.onTapSeeMore()
        }



        btn_question.setOnClickListener {
            mChatPresenter.onTapQuestion()
        }


        btn_PrescribeMedicine.setOnClickListener {
            mChatPresenter.onTapPrescribeMedicine()
        }

        btn_medicineHistory.setOnClickListener {
           mChatPresenter.onTapMedicineHistory()
        }


    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setUpRecyclerView() {
        rv_chatMessage?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mChatMessageAdapter = ChatMessageAdapter()
        rv_chatMessage?.adapter = mChatMessageAdapter


    }

    private fun setUpPresenter() {
        mChatPresenter = ViewModelProviders.of(this).get(ChatPresenterImpl::class.java)
        mChatPresenter.initPresenter(this)
    }

    override fun displayChatMessage(messageLists: List<ChatMessageVO>) {
        Log.d("messageLists", messageLists.size.toString())
        mChatMessageAdapter.setTypeListList(messageLists)

        mChatMessageAdapter.setNewData(messageLists.toMutableList())
    }

    override fun displayPatientRequestData(data: ConsultationChatVO) {
        bindData(data)
    }

    override fun navigateToPrescribeMedicineScreen() {
        startActivity(PrescribeMedicineActivity.newIntent(this, intent.getStringExtra(
                PARAM_CONSULTATION_CHAT_ID).toString()))
    }

    override fun navigateToGeneralQuestionScreen() {
        startActivity(GeneralQuestionActivity.newIntent(this, intent.getStringExtra(
                PARAM_CONSULTATION_CHAT_ID).toString()))
    }

    override fun displayPrescriptionLists(lists: List<PrescriptionVO>) {
        Log.d("prescriptionLists", lists.size.toString())
        if (lists.isNotEmpty()) {
            recommend_medicine_view_pod.visibility = View.VISIBLE
            mRecommendMedicineViewPod = recommend_medicine_view_pod as RecommendMedicineViewPod

            mRecommendMedicineViewPod.setPrescriptionData(lists, SessionManager.doctor_photo.toString())
        }
    }

    override fun navigateToMedicalCommentScreen() {
        startActivity(MedicalCommentActivity.newIntent(this,intent.getStringExtra(PARAM_CONSULTATION_CHAT_ID).toString()))
    }


    private fun bindData(data: ConsultationChatVO) {
        tv_patientName.text = data.patient_info?.name
        pname.text = data.patient_info?.name
        pdateofBirth.text = data.patient_info?.dateOfBirth
        pheight.text = data.patient_info?.height
        pbloodtype.text = data.patient_info?.blood_type
        pallergic.text = data.patient_info?.allergic_reactions
        pweight.text = data.patient_info?.weight
        pbloodpressure.text = data.patient_info?.blood_pressure

        data.case_summary?.let {
            txt_question1.text = data.case_summary?.get(0)?.question
            txt_answer1.text = data.case_summary?.get(0)?.answer

            txt_question2.text = data.case_summary?.get(1)?.question
            txt_answer2.text = data.case_summary?.get(1)?.answer
        }

        tv_patientName.text = data.patient_info?.name
    }

    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}