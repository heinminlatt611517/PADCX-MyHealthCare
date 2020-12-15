package com.padc.share.networks

import android.graphics.Bitmap
import com.padc.share.data.vos.*

interface FirebaseApi {

    fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getRecentDoctor(
        patientId: String,
        onSuccess: (doctors: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPrescriptionByID(
        consulationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addAndUpdateRecentDoctor(
        patientID: String,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPatient(
        email: String,
        onSuccess: (patients: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getDoctor(onSuccess: (doctors: List<DoctorVO>) -> Unit, onFailure: (String) -> Unit)

    fun getDoctorByEmail(
        email: String,
        onSuccess: (doctorVO: DoctorVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addNewPatient(patients: PatientVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun addNewDoctor(doctors: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)


    fun getSpecialities(
        onSuccess: (specialities: List<SpecialitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMedicineBySpeciality(
        speciality: String,
        onSuccess: (medicine: List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSpecialQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getRelatedQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<RelatedQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getGeneralQuestion(onSuccess: (List<QuestionAnswerVO>) -> Unit, onFailure: (String) -> Unit)
    fun getGeneralQuestionTemplate(
        onSuccess: (List<GeneralQuestionTemplateVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessage(
        consultationID: String,
        chatMessageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConsultationChat(
        patientId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkoutMedicine(checkOutVO: CheckOutVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun startConsultation(
        consulationId: String,
        dateTime: String, questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendDirectRequest(
        speciality: String,
        dateTime: String, questionAnswerList: QuestionAnswerVO,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendBroadCastConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        dateTime: String,
        onSuccess: () -> Unit, onFailure: (String) -> Unit
    )


    fun addToPreScribeMedicine(
        documentID: String,
        prescriptionListVO: ArrayList<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

//    fun finishConsultation(
//        patientVO: PatientVO,
//        doctorVO: DoctorVO,
//        documentID: String,
//        prescriptionListVO: ArrayList<PrescriptionVO>,
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//
//    )

    fun getConsultationByPatientID(
        patientId: String,
        onSuccess: (consultationVO: ConsultationChatVO) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getFinishConsultationByPatientID(
        patientID: String,
        onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getBroadcastConsultationRequestByPatient(
        patientId: String,
        onSuccess: (consulationRequest: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getBroadcastConsultationRequestBySpeciality(
        speciality: String,
        onSuccess: (list: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun acceptRequest(
        status: String,
        consulationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


    fun getBroadConsultationRequest(
        consulation_request_id: String,
        onSuccess: (consulationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConsultationPatient(
        doctorId: String,
        onSuccess: (List<ConsultedPatientVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addConsultationPatient(
        doctorId: String,
        patientId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun startConsultationChatPatient(
        consulationChatId: String,
        consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConsulationChatForDoctor(
        doctorId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun preScribeMedicine(
        consulationId: String,
        prescriptionVO: PrescriptionVO,
        onSuccess: () -> Unit,
        onFailure: (String)
    )

    fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

}