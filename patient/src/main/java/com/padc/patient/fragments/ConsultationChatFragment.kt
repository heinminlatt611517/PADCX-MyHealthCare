package com.padc.patient.fragments

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.activities.ChatActivity
import com.padc.patient.activities.OrderPrescriptionActivity
import com.padc.patient.adapters.ConsultationChatAdapter
import com.padc.patient.adapters.PrescriptionAdapter
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.dialogs.PaymentPrescriptionDialogFragment
import com.padc.patient.dialogs.PrescriptionDialogFragment
import com.padc.patient.mvp.presenter.ConsultationChatPresenter
import com.padc.patient.mvp.presenter.impls.ConsultationChatPresenterImpl
import com.padc.patient.mvp.view.ConsultationChatView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.vos.ConsultationChatVO
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_consultation_chat.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.prescription_dialog.*
import kotlinx.android.synthetic.main.prescription_dialog.view.*
import kotlinx.android.synthetic.main.recent_doctor_confirm_dialog.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ConsultationChatFragment : Fragment() ,ConsultationChatView {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mPresenter : ConsultationChatPresenter
    private lateinit var mAdapter : ConsultationChatAdapter
    private var dialog : Dialog? = null


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

    override fun navigateToChatScreen(consultationChatID: String) {
        startActivity(context?.let { ChatActivity.newIntent(it,consultationChatID) })
    }



    override fun displayFinishConsultationChatLists(lists: List<ConsultationChatVO>) {
        Log.d("finishLists",lists.size.toString())
        mAdapter.setNewData(lists.toMutableList())
    }

    override fun showPrescriptionDialog(lists : List<PrescriptionVO>) {
        val view = layoutInflater.inflate(R.layout.prescription_dialog, null)
        dialog = context?.let { Dialog(it) }

        dialog?.apply {
            setCancelable(false)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


        view.rv_prescription.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var mPrescriptionAdapter : PrescriptionAdapter = PrescriptionAdapter()
        view.rv_prescription.adapter = mPrescriptionAdapter

        if (lists.isNotEmpty()){
            mPrescriptionAdapter.setNewData(lists.toMutableList())
        }

        view.btn_close.setOnClickListener {
            dialog?.dismiss()
        }


        dialog?.show()

    }

    override fun displayList(list: List<PrescriptionVO>) {
        mPresenter.getPrescriptionList().observe(
                this, Observer {

        }
        )
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}