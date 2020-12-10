package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BITMAP_IMAGE
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BUNDLE_BIRTH_DATE
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.dialog.PatientDialogFragment.Companion.BUNDLE_NAME
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MainPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.MainPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MainView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.PatientVO
import com.padc.share.services.FCMService
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.*
import kotlinx.android.synthetic.main.fragment_patient_dialog.iv_patient
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientBirthDate
import kotlinx.android.synthetic.main.fragment_patient_dialog.tv_patientName

class MainActivity : BaseActivity() ,MainView{

    companion object {

        private const val ID_EXTRA = "ID_EXTRA"
        fun newIntent(context: Context,patientID : String) : Intent {
            val intent = Intent(context,
                MainActivity::class.java)
            intent.putExtra(ID_EXTRA,patientID)
            return intent
        }
    }

    private lateinit var mMainPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFirebaseInstanceID()
        setUpPresenter()

        mMainPresenter.onUiReady(this,intent.getStringExtra(ID_EXTRA).toString())
    }

    private fun setUpPresenter() {
        mMainPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
        mMainPresenter.initPresenter(this)
    }

    override fun showPatientDialog(patientVO: PatientVO) {
        val patientDialog = PatientDialogFragment.newFragment()
        val bundle = Bundle()

        bundle.putString(BUNDLE_NAME, patientVO.name)
        bundle.putString(BITMAP_IMAGE, patientVO.photo)
        bundle.putString(BUNDLE_BIRTH_DATE, patientVO.dateOfBirth)

        patientDialog.arguments = bundle
        this?.supportFragmentManager?.let { patientDialog.show(it,PatientDialogFragment.TAG_ADD_PATIENT_DIALOG) }
    }

    override fun displayPatientData(patientVO: PatientVO) {

        card.visibility = View.VISIBLE

        tv_patientName.text = patientVO?.name
        tv_patientBirthDate.text = patientVO?.dateOfBirth
        ImageUtils().showImage(
            iv_patient,
            patientVO?.photo.toString(),
            R.drawable.doctor_img
        )
    }


    override fun showErrorMessage(errorMessage: String) {
      showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner  = this


}