package com.padc.share.data.models.impls

import android.graphics.Bitmap
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.networks.CloudFireStoreFirebaseApiImpl
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager
import com.padc.share.networks.auth.FirebaseAuthManager

object DoctorModelImpl : DoctorModel, BaseModel() {

    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image ,onSuccess,onFailure)
    }

    override fun registerNewDoctor(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
       mAuthManager.register(email,password,userName,onSuccess= {
           mFirebaseApi.addNewDoctor(DoctorVO("",userName,email),onSuccess ={},onFailure = {})
       },onFailure = {})
    }


    override fun getDoctorFromFirebaseAndSaveToDatabase(
        onSuccess: (doctorList: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getDoctor(onSuccess = {
            mTheDB.doctorDao().insertDoctorList(it)
        },onFailure = {

        })
    }

    override fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPatientByID(patientID,onSuccess,onFailure)
    }


}