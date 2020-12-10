package com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.MainPresenterImpl
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.iv_patient
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientBirthDate
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientName


class PatientDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_PATIENT_DIALOG = "TAG_ADD_CONFIRM_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_PATIENT_ID = "BUNDLE_PATIENT_ID"
        const val BUNDLE_BIRTH_DATE = "BUNDLE_BIRTH_DATE"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"

        fun newFragment(): PatientDialogFragment {
            return PatientDialogFragment()
        }
    }


    private lateinit var mMainPresenter: MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_patient_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpActionsListener()
        init()
    }

    private fun init() {

        tv_patientName.text = arguments?.getString(BUNDLE_NAME)
        tv_patientBirthDate.text = arguments?.getString(BUNDLE_BIRTH_DATE)
        ImageUtils().showImage(
            iv_patient,
            arguments?.getString(BUNDLE_BIRTH_DATE).toString(),
            R.drawable.doctor_img
        )

    }

    private fun setUpActionsListener() {
        btn_skip.setOnClickListener {
            mMainPresenter.onTapSkip()
        }

        btn_accept.setOnClickListener {
            mMainPresenter.onTapAccept()
        }
    }

    private fun setUpPresenter() {
        mMainPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
    }


}