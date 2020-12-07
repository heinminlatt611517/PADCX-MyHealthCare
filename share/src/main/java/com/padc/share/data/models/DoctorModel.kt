package com.padc.share.data.models

import android.graphics.Bitmap
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager

interface DoctorModel {
    var mAuthManager: AuthManager
    var mFirebaseApi : FirebaseApi

    fun uploadPhotoToFirebaseStorage(image : Bitmap, onSuccess: (photoUrl : String) -> Unit, onFailure: (String) -> Unit)

    fun registerNewDoctor(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getDoctorFromFirebaseAndSaveToDatabase(onSuccess: (doctorList : List<DoctorVO>) -> Unit,onFailure: (String) -> Unit)
}