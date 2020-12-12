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
import com.padc.patient.activities.MainActivity.Companion.newIntent
import com.padc.patient.activities.SplashScreenActivity
import com.padc.patient.mvp.presenter.ProfilePresenter
import com.padc.patient.mvp.view.ProfileView
import com.padc.patient.utils.SessionManager
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.fragment_account.*
import mk.monthlytut.patient.mvp.presenters.ProfilePresenterImpl
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AccountFragment : Fragment() ,ProfileView{

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private  var bitmap : Bitmap? = null

    private lateinit var mPresenter : ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpActionListener()

        etUserName.text = Editable.Factory.getInstance().newEditable( SessionManager.patient_name)
        etEmail.text = Editable.Factory.getInstance().newEditable(SessionManager.patient_email)
        et_dateOfBirth.text = Editable.Factory.getInstance().newEditable(SessionManager.patient_dateOfBirth)


        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpActionListener() {

        btn_logOut.setOnClickListener {
            SessionManager.login_status=false
            startActivity(activity?.let { it -> SplashScreenActivity.newIntent(it) })
            activity?.finish()
        }
        iv_profile.setOnClickListener{
            mPresenter?.onTapEditProfileImage()
        }
        tv_save.setOnClickListener{
            bitmap?.let { }
        }
        tv_cancel.setOnClickListener{
            bitmap?.let {
                mPresenter.onTapCancelUserData()
            }
        }

    }
    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        @JvmStatic
        fun newInstance() = AccountFragment().apply {}
    }

    override fun saveUserData() {

    }

    override fun cancelUserData() {

    }

    override fun editProfileImage() {
        openGallery()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), AccountFragment.PICK_IMAGE_REQUEST)
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
                        val source: ImageDecoder.Source = ImageDecoder.createSource(context?.contentResolver!!, filePath)
                        bitmap = ImageDecoder.decodeBitmap(source)
                        account_btngroup.visibility = View.VISIBLE
                        ImageUtils().showImageProfile(iv_profile.context,iv_profile,null,filePath)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                        ImageUtils().showImageProfile(iv_profile.context,iv_profile,null,filePath)
                        account_btngroup.visibility = View.GONE
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


}