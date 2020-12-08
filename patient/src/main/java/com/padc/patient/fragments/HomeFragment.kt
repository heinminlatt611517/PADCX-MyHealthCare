package com.padc.patient.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.RecentDoctorAdapter
import com.padc.patient.adapters.SpecialityDoctorAdapter
import com.padc.patient.dialogs.ConfirmDialogFragment
import com.padc.patient.dialogs.ConfirmDialogFragment.Companion.BUNDLE_NAME
import com.padc.patient.dialogs.ConfirmDialogFragment.Companion.BUNDLE_PATIENT_ID
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.presenter.impls.HomePresenterImpl
import com.padc.patient.mvp.view.HomeView
import com.padc.patient.views.viewPods.ConsultationRequestViewPod
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialitiesVO
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeView {



    private var patientID : String? = null

    private lateinit var mPresenter : HomePresenter
    private lateinit var mSpecialityAdapter : SpecialityDoctorAdapter
    private lateinit var mRecentDoctorAdapter : RecentDoctorAdapter
    private lateinit var mConsultationRequestViewPod : ConsultationRequestViewPod

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        patientID = arguments?.getString(ID)
        Log.d("PatientID",patientID.toString())
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpViewPods()
        setUpRecyclerView()

        mPresenter.onUiReady(this,patientID.toString())

    }

    private fun setUpRecyclerView() {
        rc_recent_doctor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecentDoctorAdapter = RecentDoctorAdapter (mPresenter)
        rc_recent_doctor.adapter = mRecentDoctorAdapter

        rc_speciality.layoutManager = GridLayoutManager(activity ,2)
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

    companion object {
        const val ID = "patient-id"
        fun newInstance(id: String) : HomeFragment {
            val bundle = Bundle()
            bundle.putString(ID, id)
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun showConsultationRequestDialogFragment() {

    }

    override fun displaySpecialistDoctorLists(specialistDoctorLists: List<SpecialitiesVO>) {
        Log.d("SpecialityLists", specialistDoctorLists.size.toString())
         mSpecialityAdapter.setNewData(specialistDoctorLists.toMutableList())
    }

    override fun displayRecentDoctorLists(recentDoctorLists: ArrayList<DoctorVO>) {
         mRecentDoctorAdapter.setNewData(recentDoctorLists)
    }

    override fun showConfirmDialog(specialityName : String) {

        Log.d("SpecialityName",specialityName)

        val confirmDialog = ConfirmDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, specialityName)
        bundle.putString(BUNDLE_PATIENT_ID,"")
        confirmDialog.arguments = bundle
        activity?.supportFragmentManager?.let { confirmDialog.show(it,ConfirmDialogFragment.TAG_ADD_CONFIRM_DIALOG) }
    }

    override fun navigateToEmptyCaseSummaryScreen(context: Context,speciality: String) {

    }


    override fun navigateToCaseSummaryScreen(patientVO: PatientVO, speciality: String) {

    }

    override fun displayPatientData(patientVO: PatientVO) {
        Log.d("PatientData",patientVO.name)
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this



}