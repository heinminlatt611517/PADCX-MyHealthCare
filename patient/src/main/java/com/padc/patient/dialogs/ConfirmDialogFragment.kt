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


class ConfirmDialogFragment : DialogFragment() {

    companion object {

        const val TAG_ADD_CONFIRM_DIALOG = "TAG_ADD_CONFIRM_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_PATIENT_ID = "BUNDLE_PATIENT_ID"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"

        fun newFragment(): ConfirmDialogFragment {
            return ConfirmDialogFragment()
        }
    }


    private lateinit var mPresenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.confirm_dialog_layout, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpActionsListener()

        init()
    }

    private fun init(){
        consultation_request_name_id.text = arguments?.getString(BUNDLE_NAME)+resources.getString(R.string.consultation_request_message)
    }

    private fun setUpActionsListener() {
        btn_cancle.setOnClickListener { dismiss() }
        val specialityName = arguments?.getString(BUNDLE_NAME)
        val patientID = arguments?.getString(BUNDLE_PATIENT_ID)

        Log.d("specialityName",specialityName.toString())

        btn_confirm.setOnClickListener {
            specialityName?.let { it1 ->
                mPresenter.onTapConfirm(it1,this)
            }

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
    }


}