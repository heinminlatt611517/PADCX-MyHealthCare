package com.padc.share.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.vos.*
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager

interface DoctorModel {
    var mAuthManager: AuthManager
    var mFirebaseApi: FirebaseApi

    fun uploadPhotoToFirebaseStorage(
            image: Bitmap,
            onSuccess: (photoUrl: String) -> Unit,
            onFailure: (String) -> Unit
    )

    fun registerNewDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun getDoctorFromFirebaseAndSaveToDatabase(
            onSuccess: (doctorList: List<DoctorVO>) -> Unit,
            onFailure: (String) -> Unit
    )

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

    fun getDoctorByEmail(
            email: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    )

    fun getDoctorByEmailFromDB(email: String): LiveData<DoctorVO>

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


    fun startConsultation(
            consultationId: String,
            dateTime: String,
            questionAnswerList: List<QuestionAnswerVO>,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    )

    fun acceptRequest(
            status: String,
            consultationId: String,
            questionAnswerList: List<QuestionAnswerVO>,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    )

    fun getConsultedPatient(
            doctorId: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    )

    fun getConsultationPatient(
            doctorId: String,
            onSuccess: (List<ConsultedPatientVO>) -> Unit,
            onFailure: (String) -> Unit
    )

    fun getConsultedPatientFromDB(doctorId: String): LiveData<List<ConsultedPatientVO>>

    fun getBrodcastConsultationRequestsFromDB(speciality: String): LiveData<List<ConsultationRequestVO>>

    fun getBrodcastConsultationRequests(
            speciality: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    )

    fun getConsultationByDoctorId(
            doctorId: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    )

    fun getConsultationByDoctorIdFromDB(doctorId: String): LiveData<List<ConsultationChatVO>>


    fun getMedicineBySpeciality(
            speciality: String,
            onSuccess: (medicine: List<MedicineVO>) -> Unit,
            onFailure: (String) -> Unit
    )

    fun getRelatedQuestionBySpeciality(
            speciality: String,
            onSuccess: (List<RelatedQuestionVO>) -> Unit,
            onFailure: (String) -> Unit
    )

    fun finishConsultation(
            consultationChatVO: ConsultationChatVO,
            prescriptionList: List<PrescriptionVO>,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    )


    fun getPrescription(
            consultationId: String,
            onSuccess: (List<PrescriptionVO>) -> Unit,
            onFailure: (String) -> Unit
    )

    fun getConsultationChatForDoctor(
            doctorId: String,
            onSuccess: (List<ConsultationChatVO>) -> Unit,
            onFailure: (String) -> Unit
    )

    fun getConsultationByConsulationRequestId(consultation_request_id: String, onSuccess: (consultationRequestVO: ConsultationRequestVO) -> Unit, onError: (String) -> Unit)

    fun getConsultationByConsulationRequestIdFromDB(consultation_request_id: String): LiveData<ConsultationRequestVO>

    fun getBroadcastConsultationRequest(
            consulation_request_id : String,
            onSuccess: (consulationRequest : ConsultationRequestVO) -> Unit,
            onFailure: (String) -> Unit
    )

    fun getConsultationChat(consulationId:  String,  onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getConsultationChatFromDB(consulationId : String) : LiveData<ConsultationChatVO>

    fun deleteConsultationRequestById(consulationId : String)  : LiveData<List<ConsultationRequestVO>>
}