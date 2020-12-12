package com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.MainPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.PrescribeMedicinePresenterImpl
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.iv_patient
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientBirthDate
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientName
import kotlinx.android.synthetic.main.list_item_prescribe_medicine.*
import kotlinx.android.synthetic.main.prescribe_medicine_dialog.*


class PrescribeMedicineDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_PRESCRIBE_DIALOG = "TAG_ADD_PRESCRIBE_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_MEDICINE_ID = "BUNDLE_PATIENT_ID"
        const val BUNDLE_BIRTH_DATE = "BUNDLE_BIRTH_DATE"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"

        fun newFragment(): PrescribeMedicineDialogFragment {
            return PrescribeMedicineDialogFragment()
        }
    }

    private lateinit var mPresenter: PrescribeMedicinePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.prescribe_medicine_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpActionsListener()
        init()
    }

    private fun init() {
        tv_medicineName.text = arguments?.getString(BUNDLE_NAME)
    }

    private fun setUpActionsListener() {

        btn_insert_medicine.setOnClickListener {

        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PrescribeMedicinePresenterImpl::class.java)
    }


}