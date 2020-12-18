package com.padc.patient.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.activities.ChatActivity
import com.padc.patient.adapters.ConsultationAcceptAdapter
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.adapters.SpecialityDoctorAdapter
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.dialogs.ConfirmDialogFragment.Companion.BUNDLE_NAME
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.presenter.impls.HomePresenterImpl
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.utils.SessionManager
import com.padc.patient.views.viewPods.ConsultationRequestViewPod
import com.padc.share.data.vos.*
import com.padc.share.utils.DateUtils
import kotlinx.android.synthetic.main.confirm_dialog_layout.view.btn_cancle
import kotlinx.android.synthetic.main.confirm_dialog_layout.view.btn_confirm
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recent_doctor_confirm_dialog.view.*


private const val PARAM_ID = "PARAM_ID"

class HomeFragment : Fragment(), HomeView {

    companion object {
        fun newInstance(patientID: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_ID, patientID)
                }
            }


    }

    private lateinit var mPresenter: HomePresenter
    private lateinit var mSpecialityAdapter: SpecialityDoctorAdapter
    private lateinit var mRecentDoctorAdapter: RecentDoctorAdapter
    private lateinit var mConsultationRequestViewPod: ConsultationRequestViewPod

    private lateinit var mConsultationAcceptAdapter: ConsultationAcceptAdapter

   private var dialog : Dialog? = null
    private var patientID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            patientID = it.getString(PARAM_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpPresenter()
        setUpViewPods()
        setUpRecyclerView()

        mPresenter.onUiReady(this)

    }

    private fun setUpRecyclerView() {


        rv_consultation.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mConsultationAcceptAdapter = ConsultationAcceptAdapter(mPresenter)
        rv_consultation.adapter = mConsultationAcceptAdapter


        rc_recent_doctor.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecentDoctorAdapter = RecentDoctorAdapter(mPresenter)
        rc_recent_doctor.adapter = mRecentDoctorAdapter


        rc_speciality.layoutManager = GridLayoutManager(activity, 2)
        mSpecialityAdapter = SpecialityDoctorAdapter(mPresenter)
        rc_speciality.adapter = mSpecialityAdapter
    }

    private fun setUpViewPods() {
        mConsultationRequestViewPod = vp_consulation_request as ConsultationRequestViewPod

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(HomePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    override fun showConsultationRequestDialogFragment() {

    }

    override fun displaySpecialistDoctorLists(specialistDoctorLists: List<SpecialitiesVO>) {
        Log.d("SpecialityLists", specialistDoctorLists.size.toString())
        mSpecialityAdapter.setNewData(specialistDoctorLists.toMutableList())
    }

    override fun displayRecentDoctorLists(recentDoctorLists: ArrayList<DoctorVO>) {

        if (recentDoctorLists.isNotEmpty()) {
            layout_recentDoctor.visibility = View.VISIBLE
            mRecentDoctorAdapter.setNewData(recentDoctorLists)
        } else {
            layout_recentDoctor.visibility = View.GONE
        }

    }

    override fun showConfirmDialog(specialityName: String) {

        Log.d("SpecialityName", specialityName)

        val confirmDialog = ConfirmDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, specialityName)
        confirmDialog.arguments = bundle
        activity?.supportFragmentManager?.let {
            confirmDialog.show(
                it,
                ConfirmDialogFragment.TAG_ADD_CONFIRM_DIALOG
            )
        }
    }

    override fun navigateToEmptyCaseSummaryScreen(context: Context, speciality: String) {

    }

    override fun navigateToCaseSummaryScreen(speciality: String) {

    }


    override fun displayPatientData(patientVO: PatientVO) {
        Log.d("PatientData", patientVO.name)
    }

    override fun navigateToChatScreen(
        consultation_chat_id: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        activity?.let {
            mPresenter.onCompleteStatus(it, consultation_chat_id, consultationRequestVO)
            it.startActivity(ChatActivity.newIntent(it, consultation_chat_id))
        }
    }

    override fun displayConsultationRequestList(consultationRequestVO: List<ConsultationRequestVO>) {
        Log.d("RequestListSize", consultationRequestVO.size.toString())
        mConsultationAcceptAdapter.setNewData(consultationRequestVO.toMutableList())
    }



    override fun showRecentDoctorDialog(doctorVO: DoctorVO,consultationRequestVO: ConsultationRequestVO) {

        val view = layoutInflater.inflate(R.layout.recent_doctor_confirm_dialog, null)
        dialog = context?.let { Dialog(it) }

        view.tv_doctorSpeciality?.text = doctorVO.speciality + resources.getString(R.string.consultation_request_message)

        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.btn_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        view.btnConfirm.setOnClickListener {

                mPresenter.onTapConfirmDirectRequest(
                    doctorVO.speciality.toString(), DateUtils().getCurrentDate(),
                    consultationRequestVO.case_summary, consultationRequestVO.patient_info, consultationRequestVO.doctor_info
                )
            dialog?.dismiss()
        }

        dialog?.show()
    }


    override fun showErrorMessage(errorMessage: String) {
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun onResume() {
        super.onResume()

    }

}