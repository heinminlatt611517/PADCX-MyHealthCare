package com.padc.patient.activities

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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.padc.patient.R
import com.padc.patient.fragments.AccountFragment
import com.padc.patient.mvp.presenter.ProfilePresenter
import com.padc.patient.mvp.view.ProfileView
import com.padc.patient.utils.SessionManager
import com.padc.share.activities.BaseActivity
import com.padc.share.data.vos.PatientVO
import com.padc.share.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_patient_edit_profile.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_patient_account.*
import mk.monthlytut.patient.mvp.presenters.ProfilePresenterImpl
import java.io.IOException

class EditProfileActivity :BaseActivity(),ProfileView {

    companion object {
        const val PICK_IMAGE_REQUEST = 1111
        fun newIntent(context: Context) = Intent(context, EditProfileActivity::class.java)
    }

    private  var bitmap : Bitmap? = null

    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var bloodType: String? = null
    private lateinit var  mProgressDialog : ProgressDialog

    private lateinit var mProfilePresenter : ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_edit_profile)
        ImageUtils().showImage(iv_main_profile, SessionManager.patient_photo.toString(), R.drawable.user)
        setUpPresenter()
        setUpActionListener()
        setUpItemSelectedListener()


    }

    private fun setUpActionListener() {

        btn_upload.setOnClickListener {
            openGallery()
        }

        tv_back.setOnClickListener {
            onBackPressed()
        }

        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Uploading......")
        mProgressDialog.setMessage("Please Wait.....")

        btn_save.setOnClickListener {
            if(ptphone.text.toString().isNotEmpty() && pt_height.text.toString().isNotEmpty() && pt_comment.text.toString().isNotEmpty()&& et_address.text.toString().isNotEmpty()) {
                mProgressDialog.show()
                var dateofbirth = "$day  $month $year"
                bitmap?.let { it1 ->
                    mProfilePresenter?.updateUserData(it1,
                        bloodType.toString(), dateofbirth,
                        pt_height.text.toString(), pt_comment.text.toString(), ptphone.text.toString(),
                        et_address.text.toString(),this
                    )
                }
            }else
            {
                Toast.makeText(this,"Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST)
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
        bloodtype_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bloodType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }



    private fun setUpPresenter() {
        mProfilePresenter = ViewModelProviders.of(this).get(ProfilePresenterImpl::class.java)
        mProfilePresenter.initPresenter(this)
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
                        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver!!, filePath)
                        bitmap = ImageDecoder.decodeBitmap(source)
                        ImageUtils().showImageProfile(iv_main_profile.context,iv_main_profile,null,filePath)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                        ImageUtils().showImageProfile(iv_main_profile.context,iv_main_profile,null,filePath)

                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun displayPatientData(patientData: PatientVO) {
        ptphone.text =    Editable.Factory.getInstance().newEditable(patientData.phone)
        pt_height.text =    Editable.Factory.getInstance().newEditable(patientData.height)
        pt_comment.text =    Editable.Factory.getInstance().newEditable(patientData.allergic_reactions)
        et_address.text =    Editable.Factory.getInstance().newEditable(patientData.address[0].fullAddress)

    }

    override fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    override fun navigateToSplashScreen() {

    }

    override fun navigateToEditProfileScreen() {

    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(this))
        this.finish()
    }

    override fun showErrorMessage(errorMessage: String) {
       showSnackbar(errorMessage)
    }

    override fun getLifeCycleOwner(): LifecycleOwner  =this


}