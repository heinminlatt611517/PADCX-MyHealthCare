package com.padc.share.data.models.impls

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.PatientModel
import com.padc.share.data.vos.*
import com.padc.share.networks.CloudFireStoreFirebaseApiImpl
import com.padc.share.networks.FirebaseApi
import com.padc.share.networks.auth.AuthManager
import com.padc.share.networks.auth.FirebaseAuthManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

object PatientModelImpl : PatientModel, BaseModel() {
    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: AuthManager = FirebaseAuthManager


    override fun uploadPhotoToFirebaseStorage(
            image: Bitmap,
            onSuccess: (photoUrl: String) -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image, onSuccess, onFailure)
    }

    override fun registerNewPatient(patientVO: PatientVO, onSuccess: (patientVO: PatientVO) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.addNewPatient( patientVO,
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
            it.toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        mTheDB.specialityDao().insertSpecialities(it)
                    }

        }, onFailure = { onError(it) })
    }

    override fun getPatientByEmail(email: String, onSuccess: (PatientVO) -> Unit, onError: (String) -> Unit) {
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

    override fun getFinishConsultationByPatientID(patientID: String,
                                                  onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
                                                  onFailure: (String) -> Unit) {
        mFirebaseApi.getFinishConsultationByPatientID(patientID, onSuccess, onFailure)
    }

}