package com.padc.patient.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.patient.R
import com.padc.patient.activities.EditProfileActivity
import com.padc.patient.activities.MainActivity.Companion.newIntent
import com.padc.patient.activities.SplashScreenActivity
import com.padc.patient.mvp.presenter.ProfilePresenter
import com.padc.patient.mvp.view.ProfileView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.vos.PatientVO
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.account_btngroup
import kotlinx.android.synthetic.main.fragment_patient_account.*
import mk.monthlytut.patient.mvp.presenters.ProfilePresenterImpl
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AccountFragment : Fragment(), ProfileView {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private var bitmap: Bitmap? = null

    private lateinit var mPresenter: ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_patient_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpActionListener()


        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpActionListener() {

        btnLogout.setOnClickListener {
            mPresenter.onTapLogOut()
        }

        ivEdit.setOnClickListener {
            mPresenter.onTapEdit()
        }


    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1111

        @JvmStatic
        fun newInstance() = AccountFragment().apply {}
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            AccountFragment.PICK_IMAGE_REQUEST
        )
    }

    private fun savePatientDataToPrefrence(patientVO: PatientVO) {
        SessionManager.patient_name = patientVO.name
        SessionManager.patient_id = patientVO.id
        SessionManager.patient_device_id = patientVO.deviceID
        SessionManager.patient_email = patientVO.email
        SessionManager.patient_photo = patientVO.photo
        SessionManager.patient_dateOfBirth = patientVO.dateOfBirth
        SessionManager.patient_height = patientVO.height
        SessionManager.patient_bloodType = patientVO.blood_type
        SessionManager.patient_weight = patientVO.weight
        SessionManager.patient_bloodPressure = patientVO.blood_pressure
        SessionManager.patient_allegric = patientVO.allergic_reactions
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AccountFragment.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            val filePath = data.data
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(context?.contentResolver!!, filePath)
                        bitmap = ImageDecoder.decodeBitmap(source)
                        account_btngroup.visibility = View.VISIBLE
                        ImageUtils().showImageProfile(
                            iv_profile.context,
                            iv_profile,
                            null,
                            filePath
                        )
                    } else {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                        ImageUtils().showImageProfile(
                            iv_profile.context,
                            iv_profile,
                            null,
                            filePath
                        )
                        account_btngroup.visibility = View.GONE
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun displayPatientData(patientData: PatientVO) {
        patientData?.let {
            savePatientDataToPrefrence(it)
        }
        ImageUtils().showImage(img_profile, patientData.photo.toString(), R.drawable.user)
        tv_patientName.text = SessionManager.patient_name

        if (SessionManager.patient_phone.toString().isNotEmpty()) {
            tv_patientPhone.text = SessionManager.patient_phone
        } else {
            tv_patientPhone.text = "09-xxxxxx"
        }

        if (SessionManager.patient_dateOfBirth.toString().isNotEmpty()) {
            et_dateofbirth.text = SessionManager.patient_dateOfBirth
        } else {
            et_dateofbirth.text = "Empty"
        }

        if (SessionManager.patient_email.toString().isNotEmpty()) {
            tv_email.text = SessionManager.patient_email
        } else {
            et_dateofbirth.text = "Empty"
        }



        if (SessionManager.patient_bloodType.toString().isNotEmpty()) {
            et_bloodtype.text = SessionManager.patient_bloodType
        } else {
            et_bloodtype.text = "Empty"
        }

        if (SessionManager.patient_height.toString().isNotEmpty()) {
            et_height.text = SessionManager.patient_height
        } else {
            et_height.text = "Empty"
        }

        if (SessionManager.patient_allegric.toString().isNotEmpty()) {
            et_comment.text = SessionManager.patient_allegric
        } else {
            et_comment.text = "Empty"
        }

        if (patientData.address.size > 0) {
            ets_address.text = patientData.address[0].fullAddress
        } else {
            ets_address.text = "Empty"
        }
    }


    override fun hideProgressDialog() {

    }

    override fun navigateToSplashScreen() {
        SessionManager.login_status = false
        startActivity(activity?.let { SplashScreenActivity.newIntent(it) })
        activity?.finish()
    }

    override fun navigateToEditProfileScreen() {
        startActivity(activity?.let { EditProfileActivity.newIntent(it) })
    }

    override fun navigateToMainScreen() {

    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}