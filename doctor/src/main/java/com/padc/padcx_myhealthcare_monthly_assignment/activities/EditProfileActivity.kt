package com.padc.padcx_myhealthcare_monthly_assignment.activities

import android.app.Activity
import android.app.ProgressDialog
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
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.padcx_myhealthcare_monthly_assignment.R
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.ProfilePresenter
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.presenter.impls.ProfilePresenterImpl
import com.padc.padcx_myhealthcare_monthly_assignment.mvp.view.ProfileView
import com.padc.padcx_myhealthcare_monthly_assignment.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.DoctorVO
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_doctor_register.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.day_spinner
import kotlinx.android.synthetic.main.activity_edit_profile.month_spinner
import kotlinx.android.synthetic.main.activity_edit_profile.year_spinner
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.list_item_consultation_request_item.view.*
import java.io.IOException

class EditProfileActivity : BaseActivity(), ProfileView {

    private var bitmap: Bitmap? = null
    private lateinit var mPresenter: ProfilePresenter
    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var gender: String? = null
    private lateinit var mProgreessDialog: ProgressDialog

    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        fun newIntent(context: Context) = Intent(context, EditProfileActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setUpPresenter()
        setUpActionListener()
        setUpItemSelectedListener()

        ImageUtils().showImage(
            iv_main_profile,
            SessionManager.doctor_photo.toString(),
            R.drawable.user
        )

        mPresenter.onUiReady(this)
    }

    private fun setUpActionListener() {
        gender_radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                gender = radio.text.toString()
            })

        btn_upload.setOnClickListener {
            openGallery()
        }

        tv_back.setOnClickListener {
            onBackPressed()
        }


        mProgreessDialog = ProgressDialog(this)
        mProgreessDialog.setTitle("Uploading......")
        mProgreessDialog.setMessage("Please Wait.....")

        btn_save.setOnClickListener {

            mProgreessDialog.show()
            var dateofbirth = "$day  $month $year"
            bitmap?.let { it1 ->
                mPresenter?.updateUserData(
                    it1,
                    SessionManager.doctor_speciality.toString(),
                    et_phone?.text.toString(),
                    et_degree.text.toString(),
                    et_biography.text.toString(),
                    et_address.text.toString(),
                    et_experience.text.toString(),
                    "$day $month $year",
                    gender.toString()
                )
            }

        }
    }


    private fun setUpItemSelectedListener() {

        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                day = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


    }


    private fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

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
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(this?.contentResolver!!, filePath)
                        bitmap = ImageDecoder.decodeBitmap(source)

                        ImageUtils().showImageProfile(
                            iv_main_profile.context,
                            iv_main_profile,
                            null,
                            filePath
                        )
                    } else {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this?.contentResolver, filePath)
                        ImageUtils().showImageProfile(
                            iv_main_profile.context,
                            iv_main_profile,
                            null,
                            filePath
                        )

                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    override fun displayDoctorData(doctorVO: DoctorVO) {
        et_phone.text = Editable.Factory.getInstance().newEditable(doctorVO.phone)
        et_degree.text = Editable.Factory.getInstance().newEditable(doctorVO.degree)
        et_biography.text = Editable.Factory.getInstance().newEditable(doctorVO.biography)
        et_address.text = Editable.Factory.getInstance().newEditable(doctorVO.address)
        et_experience.text = Editable.Factory.getInstance().newEditable(doctorVO.experience)
    }


    override fun navigateToEditProfileScreen() {

    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(this, ""))
        this.finish()
    }

    override fun showErrorMessage(errorMessage: String) {

    }

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun hideProgressDialog() {
        mProgreessDialog.dismiss()
    }


}