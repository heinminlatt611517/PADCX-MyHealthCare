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
import com.padc.share.activities.BaseActivity
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.IOException

class ProfileActivity : BaseActivity() ,ProfileView {

    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        fun newIntent(context: Context) : Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }


    private lateinit var mPresenter : ProfilePresenter
    private  var bitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setUpPresenter()
        setUpActionsListener()


        etUserName.text = Editable.Factory.getInstance().newEditable( SessionManager.doctor_name)
        etEmail.text = Editable.Factory.getInstance().newEditable(SessionManager.doctor_email)

    }

    private fun setUpActionsListener() {
        btn_logOut.setOnClickListener {
            SessionManager.login_status=false
            startActivity(SplashScreenActivity.newIntent(this))
            finish()
        }

        iv_profile.setOnClickListener{
            mPresenter?.onTapEditProfileImage()
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun editProfileImage() {

    }

    override fun saveUserData() {

    }


    override fun showErrorMessage(errorMessage: String) {
        showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner = this


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            val filePath = data.data
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver!!, filePath)
                        bitmap = ImageDecoder.decodeBitmap(source)
                        account_btngroup.visibility = View.VISIBLE
                        ImageUtils().showImageProfile(iv_profile.context,iv_profile,null,filePath)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                        ImageUtils().showImageProfile(iv_profile.context,iv_profile,null,filePath)
                        account_btngroup.visibility = View.GONE
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}