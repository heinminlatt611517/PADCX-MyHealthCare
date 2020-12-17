package com.padc.patient.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.padc.patient.R
import com.padc.patient.adapters.PrescribeMedicineAdapter
import com.padc.patient.adapters.PrescriptionAdapter
import com.padc.patient.mvp.presenter.HomePresenter
import com.padc.patient.mvp.presenter.OrderPrescriptionPresenter
import com.padc.patient.mvp.presenter.impls.ConsultationChatPresenterImpl
import com.padc.patient.mvp.presenter.impls.HomePresenterImpl
import com.padc.patient.mvp.presenter.impls.OrderPrescriptionPresenterImpl
import com.padc.share.data.vos.*
import kotlinx.android.synthetic.main.activity_order_prescription.*
import kotlinx.android.synthetic.main.activity_order_prescription.rv_prescribeMedicine
import kotlinx.android.synthetic.main.confirm_dialog_layout.*
import kotlinx.android.synthetic.main.payment_prescription_dialog.*
import kotlinx.android.synthetic.main.prescription_dialog.*
import java.util.*
import kotlin.collections.ArrayList


class PrescriptionDialogFragment : DialogFragment() {

    companion object {

        const val TAG_ADD_PRESCRIPTION_DIALOG = "TAG_ADD_PRESCRIPTION_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BITMAP_IMAGE = "BITMAP_IMAGE"
        const val BITMAP_ADDRESS = "BITMAP_ADDRESS"

        fun newFragment(): PrescriptionDialogFragment {
            return PrescriptionDialogFragment()
        }
    }

    private lateinit var mPresenter: ConsultationChatPresenterImpl
    private lateinit var mAdapter : PrescriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.prescription_dialog, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()

    }

    private fun setUpActionListener() {
        btn_close.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpRecyclerView() {
        rv_prescription.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = PrescriptionAdapter()
        rv_prescription.adapter = mAdapter

    }


    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(ConsultationChatPresenterImpl::class.java)
        }


        mPresenter.getPrescriptionList()
                .observe(this, Observer {
                    Log.e("pLists",it.size.toString())
                    if (it.isNotEmpty()){
                        mAdapter.setNewData(it.toMutableList())
                    }

                })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


}