package com.padc.patient.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide.init
import com.padc.patient.R
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.presenter.impls.HomePresenterImpl
import kotlinx.android.synthetic.main.confirm_dialog_layout.*
import kotlinx.android.synthetic.main.recent_doctor_confirm_dialog.*
import kotlinx.android.synthetic.main.recent_doctor_confirm_dialog.view.*


class RecentDoctorDialogFragment : DialogFragment() {

    companion object {

        const val TAG_ADD_RECENT_DIALOG = "TAG_ADD_RECENT_DIALOG"
        const val BUNDLE_RECENT_NAME = "BUNDLE_RECENT_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_PATIENT_ID = "BUNDLE_PATIENT_ID"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"
        const val BUNDLE_EMAIL = "BUNDLE_EMAIL"
        fun newFragment(): RecentDoctorDialogFragment {
            return RecentDoctorDialogFragment()
        }
    }


    private lateinit var mPresenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recent_doctor_confirm_dialog, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpActionsListener()

        init()
    }

    private fun init(){
        tv_doctorSpeciality?.text = arguments?.getString(BUNDLE_RECENT_NAME)+resources.getString(R.string.consultation_request_message)
    }

    private fun setUpActionsListener() {
        btn_cancel.setOnClickListener {
            dismiss()
        }

        btnConfirm.setOnClickListener {
            mPresenter.onTapConfirmDirectRequest(arguments?.getString(BUNDLE_RECENT_NAME).toString(),
                arguments?.getString(BUNDLE_EMAIL).toString(),this)
            dismiss()
        }
    }

    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(HomePresenterImpl::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


}