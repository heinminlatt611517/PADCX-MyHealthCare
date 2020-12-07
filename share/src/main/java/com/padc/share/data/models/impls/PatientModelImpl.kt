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

    override fun registerNewPatient(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.register(email, password, userName, onSuccess = {
            mFirebaseApi.addNewPatient(PatientVO("",userName, email), onSuccess = {}, onFailure = {})
        }, onFailure = {

        })
    }


    override fun getSpecialities(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialities(onSuccess = {
            mTheDB.specialityDao().deleteSpecialities()
            mTheDB.specialityDao().insertSpecialities(it)
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
        mFirebaseApi.getSpecialQuestionBySpeciality(speciality,onSuccess,onFailure)
    }

}