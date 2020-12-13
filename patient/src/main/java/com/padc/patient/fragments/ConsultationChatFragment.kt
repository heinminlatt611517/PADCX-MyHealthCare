package com.padc.patient.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.activities.OrderPrescriptionActivity
import com.padc.patient.adapters.ConsultationChatAdapter
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.dialogs.PaymentPrescriptionDialogFragment
import com.padc.patient.mvp.presenter.ConsultationChatPresenter
import com.padc.patient.mvp.presenter.impls.ConsultationChatPresenterImpl
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.vos.ConsultationChatVO
import kotlinx.android.synthetic.main.fragment_consultation_chat.*
import kotlinx.android.synthetic.main.fragment_home.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ConsultationChatFragment : Fragment() ,ConsultationChatView {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mPresenter : ConsultationChatPresenter
    private lateinit var mAdapter : ConsultationChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_consultation_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

        mPresenter.onUiReady(this,SessionManager.patient_id.toString())
    }

    private fun setUpActionListener() {
       btn_click.setOnClickListener {
           startActivity(context?.let { it1 -> OrderPrescriptionActivity.newIntent(it1) })
       }
    }

    private fun setUpRecyclerView() {
        rv_consultation_chat.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = ConsultationChatAdapter (mPresenter)
        rv_consultation_chat.adapter = mAdapter

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ConsultationChatPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsultationChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun navigateToMedicineCaseSummary() {

    }

    override fun navigateToChatScreen() {

    }

    override fun displayFinishConsultationChatLists(lists: List<ConsultationChatVO>) {
        Log.d("finishLists",lists.size.toString())
        mAdapter.setNewData(lists.toMutableList())
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}