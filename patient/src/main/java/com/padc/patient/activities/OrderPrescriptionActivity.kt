package com.padc.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.patient.R
import com.padc.patient.adapters.PatientAddressAdapter
import com.padc.patient.adapters.PrescribeMedicineAdapter
import com.padc.patient.dialogs.PaymentPrescriptionDialogFragment
import com.padc.patient.mvp.presenter.OrderPrescriptionPresenter
import com.padc.patient.mvp.presenter.impls.OrderPrescriptionPresenterImpl
import com.padc.patient.mvp.view.OrderPrescriptionView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.PrescriptionVO
import kotlinx.android.synthetic.main.activity_order_prescription.*

class OrderPrescriptionActivity : BaseActivity(), OrderPrescriptionView {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, OrderPrescriptionActivity::class.java)
        }
    }

    private var state: String? = null
    private var township: String? = null


    private lateinit var mPresenter: OrderPrescriptionPresenter
    private lateinit var mPatientAddressAdapter: PatientAddressAdapter
    private lateinit var mPrescribeMedicineAdapter: PrescribeMedicineAdapter
    private var patientFullAddress: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_prescription)

        setUpPresenter()
        setUpItemSelectedListener()
        setUpRecyclerView()
        setUpActionListener()

        mPresenter.onUiReady(
            this,
            SessionManager.patient_email.toString(),
            "1fb9d2a0-3d4d-11eb-b835-31c15ed724eb"
        )

    }

    private fun setUpItemSelectedListener() {

        state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                state = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        township_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                township = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun setUpActionListener() {
        btn_order.setOnClickListener {

            val addressLists : ArrayList<AddressVO> = arrayListOf()
            val patientAddress = AddressVO(state.toString(), "", township.toString(), ed_fullAddress.text.toString())
            addressLists.add(patientAddress)

            val patientVO = PatientVO(
                SessionManager.patient_id.toString(),
                SessionManager.patient_name.toString(),
                SessionManager.patient_email.toString(),
                SessionManager.patient_device_id,
                SessionManager.patient_photo,
                SessionManager.patient_bloodType,
                SessionManager.patient_bloodPressure,
                addressLists,
                SessionManager.patient_weight,
                SessionManager.patient_height,
                SessionManager.patient_dateOfBirth.toString(),
                SessionManager.patient_allegric,
                arrayListOf()

            )

            mPresenter.onTapMadePayment(patientVO)
        }
    }

    private fun setUpRecyclerView() {
        rv_fullAddress.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPatientAddressAdapter = PatientAddressAdapter(mPresenter)
        rv_fullAddress.adapter = mPatientAddressAdapter

        rv_prescribeMedicine.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPrescribeMedicineAdapter = PrescribeMedicineAdapter()
        rv_prescribeMedicine.adapter = mPrescribeMedicineAdapter
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(OrderPrescriptionPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun displayPatientAddress(addressLists: List<AddressVO>) {
        Log.d("addressLists", addressLists.size.toString())
        if (addressLists.isEmpty()) {
            layout_address.visibility = View.VISIBLE
        } else {
            recycler_address.visibility = View.VISIBLE
            layout_address.visibility = View.GONE
            mPatientAddressAdapter.setNewData(addressLists.toMutableList())
        }
    }

    override fun showPaymentDialog() {

        val paymentDialog = PaymentPrescriptionDialogFragment.newFragment()
        val bundle = Bundle()
        paymentDialog.arguments = bundle
        bundle.putString(
            PaymentPrescriptionDialogFragment.BITMAP_ADDRESS,
            ed_fullAddress.text.toString()
        )

        paymentDialog.arguments = bundle

        supportFragmentManager?.let {
            paymentDialog.show(
                it,
                PaymentPrescriptionDialogFragment.TAG_ADD_PAYMENT_PRESCRIPTION_DIALOG
            )
        }

    }

    override fun displayFullAddress(fullAddress: String) {
        Log.d("fullAddress", fullAddress)
        patientFullAddress = fullAddress

    }

    override fun displayPrescribeMedicineLists(medicineLists: List<PrescriptionVO>) {
        Log.d("medicineLists", medicineLists.size.toString())
        mPrescribeMedicineAdapter.setNewData(medicineLists.toMutableList())
    }

    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner {
        return this
    }
}