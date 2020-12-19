package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ProfilePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.ProfilePresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ProfileView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager.doctor_degree
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.DoctorVO
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.account_btngroup
import java.io.IOException

class ProfileActivity : BaseActivity() ,ProfileView {

    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        fun newIntent(context: Context) : Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }


    private lateinit var mPresenter : ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)

        setUpPresenter()
        setUpActionsListener()

        mPresenter.onUiReady(this)

    }

    private fun setUpActionsListener() {
        btnLogout.setOnClickListener {
            SessionManager.login_status=false
            startActivity(SplashScreenActivity.newIntent(this))
            finish()
        }

        iv_edit.setOnClickListener {
            mPresenter.onTapEditProfile()
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    override fun displayDoctorData(doctorVO: DoctorVO) {
        doctorVO?.let {
            SessionManager.doctor_name = doctorVO.name
            SessionManager.doctor_id = doctorVO.id
            SessionManager.doctor_device_id = doctorVO.deviceID
            SessionManager.doctor_email = doctorVO.email.toString()
            SessionManager.doctor_photo = doctorVO.photo.toString()
            SessionManager.doctor_speciality = doctorVO.speciality.toString()
            SessionManager.doctor_phone = doctorVO.phone
            SessionManager.doctor_degree = doctorVO.degree
            SessionManager.doctor_bigraphy = doctorVO.biography
            SessionManager.doctor_dateofBirth = doctorVO.dateofBirth
            SessionManager.doctor_experience = doctorVO.experience
            SessionManager.doctor_gender = doctorVO.gender
            SessionManager.doctor_address = doctorVO.address
        }

        ImageUtils().showImage(img_profile, doctorVO.photo.toString(),R.drawable.user)

        tv_doctorName.text = Editable.Factory.getInstance().newEditable( SessionManager.doctor_name)
        tv_doctorPhone.text = Editable.Factory.getInstance().newEditable(SessionManager.doctor_phone)
        tv_doctorSpeciality.text = Editable.Factory.getInstance().newEditable(SessionManager.doctor_speciality)
        tv_doctor_dateOfBirth.text =  " : " +Editable.Factory.getInstance().newEditable(SessionManager.doctor_dateofBirth)
        tv_doctor_gender.text = " : " + Editable.Factory.getInstance().newEditable(SessionManager.doctor_gender)
        tv_doctor_address.text = " : " +Editable.Factory.getInstance().newEditable(SessionManager.doctor_address)
        tv_doctor_degree.text = Editable.Factory.getInstance().newEditable(SessionManager.doctor_degree)
        tv_doctor_biography.text = Editable.Factory.getInstance().newEditable(SessionManager.doctor_bigraphy)
        tv_doctor_experience.text = " : " + Editable.Factory.getInstance().newEditable(SessionManager.doctor_experience)
    }

    override fun hideProgressDialog() {

    }

    override fun navigateToEditProfileScreen() {
       startActivity(EditProfileActivity.newIntent(this))
    }


    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}