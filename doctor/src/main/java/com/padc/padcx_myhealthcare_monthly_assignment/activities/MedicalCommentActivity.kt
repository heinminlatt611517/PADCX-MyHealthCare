package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.MedicalCommentPresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.MedicalCommentPresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.MedicalCommentView
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.ConsultationChatVO
import kotlinx.android.synthetic.main.activity_medical_comment.*

class MedicalCommentActivity :BaseActivity(),MedicalCommentView {

    companion object {
        const val PARAM_CONSULTATION_CHAT_ID = " chat id"
        fun newIntent(
            context: Context,
            consultation_chat_id: String

        ): Intent {
            val intent = Intent(context, MedicalCommentActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID, consultation_chat_id)
            return intent
        }

    }

    private lateinit var mMedicalPresenter : MedicalCommentPresenter
    private lateinit var mConsultationChatVO: ConsultationChatVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_comment)

        setUpPresenter()
        setUpActionListener()


        mMedicalPresenter.onUiReady(this,intent.getStringExtra(ChatActivity.PARAM_CONSULTATION_CHAT_ID).toString())
    }

    private fun setUpActionListener() {
        btn_save.setOnClickListener {
            mConsultationChatVO.medical_record = medical_comment.text.toString()
            mMedicalPresenter.onTapSaveMedicalRecord(consultationChatVO = mConsultationChatVO)
        }
    }

    private fun setUpPresenter() {
        mMedicalPresenter = ViewModelProviders.of(this).get(MedicalCommentPresenterImpl::class.java)
        mMedicalPresenter.initPresenter(this)
    }

    override fun displayConsultationPatientData(consultationChatVO: ConsultationChatVO) {
        mConsultationChatVO = consultationChatVO
        parseData(consultationChatVO)
    }

    override fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(medical_record_layout, message, Snackbar.LENGTH_SHORT
        ).setAction(resources.getString(R.string.know), null)
        snackBar.setActionTextColor(Color.WHITE)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.BLACK)
        val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }


    private fun parseData(consultationChatVO: ConsultationChatVO) {
       pname.text = consultationChatVO.patient_info?.name
        pdateofBirth.text = consultationChatVO.patient_info?.dateOfBirth
        p_start_date.text = consultationChatVO.start_consultation_date

    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner  = this



}