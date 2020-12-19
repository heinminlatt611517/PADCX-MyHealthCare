package mk.monthlytut.patient.mvp.presenters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.padc.patient.mvp.presenter.ProfilePresenter
import com.padc.patient.mvp.view.ProfileView
import com.padc.patient.utils.SessionManager
import com.padc.share.data.models.AuthenticationModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.AuthenticationModelImpl
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.mvp.presenter.AbstractBasePresenter

class ProfilePresenterImpl : ProfilePresenter, AbstractBasePresenter<ProfileView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val patientModel : PatientModel = PatientModelImpl


    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        patientModel.getPatientByEmail(SessionManager.patient_email.toString(),onSuccess = {}, onError = {})
        patientModel.getPatientByEmailFromDB(SessionManager.patient_email.toString())
            .observe(lifecycleOwner, Observer { patientVO ->

                patientVO?.let {
                    savePatientDataToPrefrence(it)
                    mView?.displayPatientData(patientVO)
                }
            })
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

    override fun updateUserData(
        bitmap: Bitmap,
        blood_type: String,
        dateofbirth: String,
        height: String,
        comment: String,
        phone: String,
        address: String,
        lifecycleOwner: LifecycleOwner
    ) {
        val mAddressLists : ArrayList<AddressVO> = arrayListOf()
        val addressLists : ArrayList<AddressVO> = arrayListOf()
        patientModel.getPatientByID(SessionManager.patient_id.toString(),onSuccess = {
            Log.d("AddressLists",it.address.size.toString())
            mAddressLists.addAll(it.address)
        },onFailure = {})
        val addressData = AddressVO("","","",address)
        addressLists.add(addressData)

        mAddressLists.addAll(addressLists)


        patientModel.uploadPhotoToFirebaseStorage(bitmap,
            onSuccess = {
                mAuthenticationModel.updateProfile(it,onSuccess = {}, onFailure = {})

                mView?.hideProgressDialog()
                var patientVo = PatientVO(
                    id= SessionManager.patient_id.toString(),
                    deviceID = SessionManager.patient_device_id.toString(),
                    name = SessionManager.patient_name.toString(),
                    email = SessionManager.patient_email.toString(),
                    photo = it,
                    blood_type = blood_type,
                    blood_pressure =SessionManager.patient_bloodPressure.toString(),
                    dateOfBirth = dateofbirth,
                    weight = SessionManager.patient_weight.toString(),
                    height = height,
                    phone = phone,
                    allergic_reactions = comment,
                    address = mAddressLists
                )
                patientModel.updatePatientData(patientVo,onSuccess = {
                    savePatientDataToPrefrence(patientVo)
                    mView?.navigateToMainScreen()

                }, onError = {})
            },
            onFailure = {
                mView?.showErrorMessage("Profile Update Failed")
            })
    }

    override fun onTapLogOut() {
       mView?.navigateToSplashScreen()
    }

    override fun onTapEdit() {
       mView?.navigateToEditProfileScreen()
    }

}

