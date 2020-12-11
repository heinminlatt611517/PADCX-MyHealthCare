package com.padc.share.data.models.impls

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.vos.*
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
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        mFirebaseApi.addNewDoctor(
            doctorVO,
            onSuccess = {
                onSuccess()
            },
            onFailure = { onFailure(it) })
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

    override fun getBroadConsultationRequest(
        consultation_request_id: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        PatientModelImpl.mFirebaseApi.getBroadConsultationRequest(consultation_request_id,onSuccess,onFailure)
    }

    override fun getDoctorByEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getDoctorByEmail(email,
            onSuccess = {
                mTheDB.doctorDao().deleteAllDoctorData()
                mTheDB.doctorDao().insertNewDoctor(it)
            }, onFailure = { onError(it) })
    }

    override fun getDoctorByEmailFromDB(email: String): LiveData<DoctorVO> {
        return mTheDB.doctorDao().getAllDoctorDataByEmail(email)
    }

    override fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        PatientModelImpl.mFirebaseApi.getAllChatMessage(consultationID,onSuccess,onFailure)
    }

    override fun sendMessage(
        consultationChatId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        PatientModelImpl.mFirebaseApi.sendMessage(consultationChatId,onSuccess = {
            onSuccess()
        },onFailure = {
            onFailure(it)
        },chatMessageVO = messageVO)
    }

    override fun startConsultation(
        consultationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.startConsultation(consultationId, dateTime, questionAnswerList, patientVO, doctorVO,
            onSuccess = {}, onFailure = { onFailure(it) })
    }

    override fun acceptRequest(
        status: String,
        consultationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.acceptRequest(status,consultationId,  questionAnswerList, patientVO, doctorVO,
            onSuccess = {}, onFailure = { onFailure(it) })
    }

    override fun getConsultedPatient(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultatedPatient(doctorId,onSuccess = {
            mTheDB.consultedPatientDao().deleteConsultedPatient()
            mTheDB.consultedPatientDao().insertConsultedPatient(it)
        }, onFailure= {})
    }

    override fun getConsultedPatientFromDB(doctorId: String): LiveData<List<ConsultedPatientVO>> {
        return mTheDB.consultedPatientDao().getConsultedPatient()
    }

    override fun getBrodcastConsultationRequestsFromDB(speciality: String): LiveData<List<ConsultationRequestVO>> {
        return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality(speciality)
    }

    override fun getBrodcastConsultationRequests(
        speciality: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getBroadcastConsultationRequestBySpeciality(speciality,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequestData(it)

            }, onFailure = { onError(it) })
    }

    override fun getConsultationByDoctorId(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsulationChatForDoctor(doctorId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)

            }, onFailure = { onError(it) })
    }

    override fun getConsultationByDoctorIdFromDB(doctorId: String): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByDoctorId(doctorId)
    }


}