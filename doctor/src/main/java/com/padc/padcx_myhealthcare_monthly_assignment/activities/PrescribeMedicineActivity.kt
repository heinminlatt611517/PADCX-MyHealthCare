package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.PrescribeMedicineAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.adapter.QuestionAnswerAdapter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment.Companion.BUNDLE_MEDICINE_ID
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PrescribeMedicineDialogFragment.Companion.BUNDLE_NAME
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.PrescribeMedicinePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.PrescribeMedicinePresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.PrescribeMedicineView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.MedicineVO
import kotlinx.android.synthetic.main.activity_patient_case_summary.*
import kotlinx.android.synthetic.main.activity_prescribe_medicine.*

class PrescribeMedicineActivity : BaseActivity(),PrescribeMedicineView {

    companion object {

        fun newIntent(context: Context) : Intent {
            return  Intent(context, PrescribeMedicineActivity::class.java)
        }
    }

    private lateinit var mPresenter : PrescribeMedicinePresenter
    private lateinit var mPrescribeMedicineAdapter: PrescribeMedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescribe_medicine)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()


        mPresenter.onUiReady(this,SessionManager.doctor_speciality.toString())
    }

    private fun setUpActionListener() {
        btn_prescribeAndStop.setOnClickListener {
           mPresenter.onTapStopConsultation()
        }
    }

    private fun setUpRecyclerView() {

        rv_prescribeMedicine?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPrescribeMedicineAdapter = PrescribeMedicineAdapter(mPresenter)
        rv_prescribeMedicine?.adapter = mPrescribeMedicineAdapter

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PrescribeMedicinePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun displayMedicineLists(lists: List<MedicineVO>) {
        Log.d("medicineLists",lists.size.toString())
        mPrescribeMedicineAdapter.setNewData(lists.toMutableList())
    }

    override fun showPrescribeMedicineDialog(medicineVO: MedicineVO) {
        Log.d("medicine", medicineVO.name.toString())

        val prescribeMedicineDialog = PrescribeMedicineDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, medicineVO.name)
        bundle.putString(BUNDLE_MEDICINE_ID, medicineVO.id)

        prescribeMedicineDialog.arguments = bundle
        supportFragmentManager?.let { prescribeMedicineDialog.show(it,PrescribeMedicineDialogFragment.TAG_ADD_PRESCRIBE_DIALOG) }
    }

    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}