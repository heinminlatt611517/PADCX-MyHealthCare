package com.padc.share.data.models.impls

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padc.share.data.models.BaseModel
import com.padc.share.data.models.DoctorModel
import com.padc.share.data.vos.ConsultationRequestVO
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


}