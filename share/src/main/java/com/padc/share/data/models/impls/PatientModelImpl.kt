package com.padc.share.data.models.impls

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.vos.*
import com.padc.share.networks.CloudFireStoreFirebaseApiImpl
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.RequestFCMBody.RequestFCM
import com.padc.share.networks.auth.AuthManager
import com.padc.share.networks.auth.FirebaseAuthManager
import com.padc.share.utils.FIREBASE_SERVER_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

object PatientModelImpl : PatientModel, BaseModel() {
    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun sendNotification(
        data: RequestFCM,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        myHealthCareApi.sendNotificationToDoctorByDeviceID(data).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("Notification", it.toString())
            }
    }

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image, onSuccess, onFailure)
    }

    override fun registerNewPatient(
        patientVO: PatientVO,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.addNewPatient(patientVO,
            onSuccess = {
                onSuccess(patientVO)
            },
            onFailure = { onFailure(it) })
    }

//    override fun registerNewPatient(
//            email: String,
//            password: String,
//            userName: String,
//            onSuccess: () -> Unit,
//            onFailure: (String) -> Unit
//    ) {
//        mAuthManager.register(email, password, userName, onSuccess = {
//            mFirebaseApi.addNewPatient(PatientVO("", userName, email), onSuccess = {
//                mTheDB.patientDao().insertPatient(PatientVO("", userName, email))
//            }, onFailure = {})
//        }, onFailure = {
//
//        })
//    }


    override fun getSpecialities(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialities(onSuccess = {
            mTheDB.specialityDao().deleteSpecialities()
            mTheDB.specialityDao().insertAllSpecialities(it)
        }, onFailure = { onError(it) })

    }


    override fun getPatientByEmail(
        email: String,
        onSuccess: (PatientVO) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getPatient(email,
            onSuccess = {
                onSuccess(it)
                mTheDB.patientDao().deleteAllPatientData()
                mTheDB.patientDao().insertPatient(it)
            }, onFailure = { onError(it) })
    }

    override fun getPatientFromDatabase(patientID: String): LiveData<PatientVO> {
        return mTheDB.patientDao().getPatientByID(patientID)
    }


    override fun getSpecialitiesFromDB(): LiveData<List<SpecialitiesVO>> {
        return mTheDB.specialityDao().getAllSpecialitiesData()
    }

    override fun getRecentDoctorLists(
        id: String,
        onSuccess: (recentDoctorLists: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getRecentDoctor(id, onSuccess, onFailure)
    }


    override fun sendBroadCastConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendBroadCastConsultationRequest(speciality = speciality,
            questionAnswerList = questionAnswerList,
            patientVO = patientVO,
            dateTime = dateTime,
            onSuccess = {
                onSuccess()
            },
            onFailure = {
            })
    }

    override fun getSpecialQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialQuestionBySpeciality(speciality, onSuccess, onFailure)
    }

    override fun getFinishConsultationByPatientID(
        patientID: String,
        onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getFinishConsultationByPatientID(patientID, onSuccess, onFailure)
    }

    override fun getPatientByEmailFromDB(email: String): LiveData<PatientVO> {
        return mTheDB.patientDao().getPatientByEmail(email)
    }

    override fun getQuestionAnswerFromDB(): LiveData<List<QuestionAnswerVO>> {
        return mTheDB.questionAnswerDao().getAllGeneralQuestion()
    }

    override fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getAllChatMessage(consultationID, onSuccess, onFailure)
    }

    override fun sendMessage(
        consultationChatId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendMessage(consultationChatId, onSuccess = {
            onSuccess()
        }, onFailure = {
            onFailure(it)
        }, chatMessageVO = messageVO)
    }

    override fun getBroadConsultationRequest(
        consultation_request_id: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getBroadConsultationRequest(consultation_request_id, onSuccess, onFailure)
    }

    override fun navigateToChatRoom(
        consultation_chat_id: String,
        consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.startConsultationChatPatient(
            consultation_chat_id,
            consultationRequestVO,
            onSuccess = {}, onFailure =
            { onError(it) })
    }

    override fun getConsultationAccepts(
        patientId: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getBroadcastConsultationRequestByPatient(
            patientId,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequestData(it)
            }, onFailure =
            { onError(it) })
    }

    override fun getConsultationAcceptsFromDB(): LiveData<List<ConsultationRequestVO>> {
        return mTheDB.consultationRequestDao().getConsultationAcceptData("accept")
    }

    override fun sendDirectRequest(
        speciality: String,
        dateTime: String,
        questionAnswerList: QuestionAnswerVO,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendDirectRequest(
            speciality,
            dateTime,
            questionAnswerList,
            patientVO,
            doctorVO,
            onSuccess,
            onFailure
        )
    }

    override fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPatientByID(patientID,onSuccess,onFailure)
    }

    override fun getPrescriptionByID(
        consulationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPrescriptionByID(consulationId,onSuccess,onFailure)
    }

    override fun checkoutMedicine(
        checkOutVO: CheckOutVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.checkoutMedicine(checkOutVO,onSuccess,onFailure)
    }

}