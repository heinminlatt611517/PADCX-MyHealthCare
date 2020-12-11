package com.padc.share.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.vos.ChatMessageVO
import com.padc.share.data.vos.ConsultationRequestVO
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager

interface DoctorModel {
    var mAuthManager: AuthManager
    var mFirebaseApi : FirebaseApi

    fun uploadPhotoToFirebaseStorage(image : Bitmap, onSuccess: (photoUrl : String) -> Unit, onFailure: (String) -> Unit)

    fun registerNewDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun getDoctorFromFirebaseAndSaveToDatabase(onSuccess: (doctorList : List<DoctorVO>) -> Unit,onFailure: (String) -> Unit)

    fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getBroadConsultationRequest(
        consultation_request_id: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getDoctorByEmail( email : String,
                          onSuccess: () -> Unit,
                          onError: (String) -> Unit)

    fun getDoctorByEmailFromDB(email: String) : LiveData<DoctorVO>

    fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessage(
        consultationChatId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


}