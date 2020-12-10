package com.padc.share.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.vos.*
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.RequestFCMBody.RequestFCM
import com.padc.share.networks.auth.AuthManager

interface PatientModel {

    var mFirebaseApi: FirebaseApi
    var mAuthManager: AuthManager


    fun sendNotification(data: RequestFCM, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun registerNewPatient(
        patientVO: PatientVO,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSpecialities(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onError: (String) -> Unit
    )

    fun getPatientByEmail(
        patientId: String,
        onSuccess: (PatientVO) -> Unit,
        onError: (String) -> Unit
    )

    fun getPatientFromDatabase(patientID: String): LiveData<PatientVO>


    fun getSpecialitiesFromDB(): LiveData<List<SpecialitiesVO>>

    fun getRecentDoctorLists(
        id: String,
        onSuccess: (recentDoctorLists: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendBroadCastConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


    fun getSpecialQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getFinishConsultationByPatientID(
        patientID: String,
        onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getPatientByEmailFromDB(email: String): LiveData<PatientVO>

    fun getQuestionAnswerFromDB(): LiveData<List<QuestionAnswerVO>>


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

    fun getBroadConsultationRequest(
        consultation_request_id: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    )
}