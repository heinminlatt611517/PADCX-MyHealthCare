package com.padc.share.networks

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.padc.share.data.models.PatientModel
import com.padc.share.data.models.impls.PatientModelImpl
import com.padc.share.data.vos.*
import com.padc.share.networks.RequestFCMBody.Data
import com.padc.share.networks.RequestFCMBody.RequestFCM
import com.padc.share.utils.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

object CloudFireStoreFirebaseApiImpl : FirebaseApi {

    private val database = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    private val mPatientModel: PatientModel = PatientModelImpl

    override fun getDoctor(
        onSuccess: (doctors: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("doctors")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val doctorLists: MutableList<DoctorVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<DoctorVO>(dataJson, DoctorVO::class.java)
                        doctorLists.add(docsData)
                    }

                    onSuccess(doctorLists)
                }
            }

    }

    override fun getDoctorByEmail(
        email: String,
        onSuccess: (doctorVO: DoctorVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("doctors")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<DoctorVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<DoctorVO>(Data, DoctorVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list[0])
            }
    }

    override fun getPatient(
        email: String,
        onSuccess: (patients: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("patients")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<PatientVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<PatientVO>(Data, PatientVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list[0])
            }
    }

    override fun addNewPatient(
        patients: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("patients")
            .document(patients.id)
            .set(patients)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully added patient")

            }
            .addOnFailureListener {
                onFailure("Failed to add patient")
                Log.d("Failure", "Failed to add patient")
            }

    }

    override fun addNewDoctor(
        doctors: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("doctors")
            .document(doctors.id)
            .set(doctors)
            .addOnSuccessListener {
                Log.d("Success", "Successfully added grocery")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("Failure", "Failed to add grocery")
                onFailure(it.localizedMessage)
            }

    }

    override fun getSpecialities(
        onSuccess: (specialities: List<SpecialitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("specialities")
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<SpecialitiesVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<SpecialitiesVO>(Data, SpecialitiesVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list)
            }


    }

    override fun getMedicineBySpeciality(
        speciality: String,
        onSuccess: (medicine: List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("specialities")
            .document(speciality)
            .collection("medicines")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val medicineList: MutableList<MedicineVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<MedicineVO>(dataJson, MedicineVO::class.java)
                        medicineList.add(docsData)
                    }

                    onSuccess(medicineList)
                }
            }
    }

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            onFailure("Update Profile Failed")
        }.addOnSuccessListener { taskSnapshot ->
            Log.d(ContentValues.TAG, "User profile updated.")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            imageUrl?.let { onSuccess(it) }
        }
    }

    override fun updatePatientData(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(patients)
            .document(patientVO.id)
            .set(patientVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully") }
            .addOnFailureListener {
                Log.d("Failure", "Failed ") }
    }

    override fun getRecentDoctor(
        patientID: String,
        onSuccess: (doctors: List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("patients")
            .document(patientID).collection("recent_consultation_doctors")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val recentDoctorLists: MutableList<DoctorVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<DoctorVO>(dataJson, DoctorVO::class.java)
                        recentDoctorLists.add(docsData)
                    }

                    onSuccess(recentDoctorLists)
                }
            }

    }

    override fun getPrescriptionByID(
        consulationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("consultation_chat")
            .document(consulationId)
            .collection("prescription")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val dataLists: MutableList<PrescriptionVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<PrescriptionVO>(
                            dataJson,
                            PrescriptionVO::class.java
                        )
                        dataLists.add(docsData)
                    }

                    onSuccess(dataLists)
                }
            }
    }

    override fun getPatientByID(
        patientID: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("patients")
            .whereEqualTo("id", patientID)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<PatientVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<PatientVO>(Data, PatientVO::class.java)
                    list.add(docsData)
                }

                onSuccess(list[0])


            }
    }

    override fun addAndUpdateRecentDoctor(
        patientID: String,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val recentDoctorMap = hashMapOf(
            "id" to doctorVO.id,
            "name" to doctorVO.name,
            "speciality_name" to doctorVO.speciality
        )
        database.collection("patients")
            .document(patientID)
            .collection("recent_consultation_doctors")
            .document(doctorVO.id)
            .set(recentDoctorMap)
            .addOnSuccessListener { Log.d("Success", "Successfully added patient") }
            .addOnFailureListener { Log.d("Failure", "Failed to add patient") }
    }


    override fun getSpecialQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("specialities")
            .document(speciality)
            .collection("special_questions")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val specialQuestionLists: MutableList<SpecialQuestionVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<SpecialQuestionVO>(
                            dataJson,
                            SpecialQuestionVO::class.java
                        )
                        specialQuestionLists.add(docsData)
                    }

                    onSuccess(specialQuestionLists)
                }
            }
    }

    override fun getRelatedQuestionBySpeciality(
        speciality: String,
        onSuccess: (List<RelatedQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("specialities")
            .document(speciality)
            .collection("related_questions")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val relatedQuestionLists: MutableList<RelatedQuestionVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<RelatedQuestionVO>(
                            dataJson,
                            RelatedQuestionVO::class.java
                        )
                        relatedQuestionLists.add(docsData)
                    }

                    onSuccess(relatedQuestionLists)
                }
            }
    }


    override fun getAllChatMessage(
        consultationID: String,
        onSuccess: (messages: List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("consultation_chat")
            .document(consultationID)
            .collection("chat_message")
            .orderBy("sendAt")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val chatLists: MutableList<ChatMessageVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData =
                            Gson().fromJson<ChatMessageVO>(dataJson, ChatMessageVO::class.java)
                        chatLists.add(docsData)
                    }

                    onSuccess(chatLists)
                }
            }
    }

    override fun sendMessage(
        consultationID: String,
        chatMessageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val messasgeMap = hashMapOf(
            "sendAt" to chatMessageVO.sendAt,
            "messageText" to chatMessageVO.messageText,
            "senderType" to chatMessageVO.senderType,
            "timestamp" to FieldValue.serverTimestamp()
        )


        database.collection("consultation_chat")
            .document(consultationID)
            .collection("chat_message")
            .document(chatMessageVO.id)
            .set(chatMessageVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully added patient")
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
                Log.d("Failure", "Failed to add patient")
            }

    }

    override fun getConsultationChat(
        patientId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("consultation_chat")
            .whereEqualTo("patient_id", patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }

    }

    override fun checkoutMedicine(
        checkOutVO: CheckOutVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val id = UUID.randomUUID().toString()
        database.collection("checkout")
            .document(id)
            .set(checkOutVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully added patient") }
            .addOnFailureListener { Log.d("Failure", "Failed to add patient") }
    }


    override fun startConsultation(
        consulationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val id = UUID.randomUUID().toString()
        val consultationChatMap = hashMapOf(
            "case_summary" to questionAnswerList,
            "id" to id,
            "finish_flag" to false,
            "doctor_id" to doctorVO.id,
            "patient_info" to patientVO,
            "doctor_info" to doctorVO,
            "start_consultation_date" to dateTime
        )

        database.collection("consultation_chat")
            .document(id)
            .set(consultationChatMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

//        database.collection("patients")
//                .document(patientVO.id)
//                .collection("recent_consultation_doctors")
//                .add(doctorVO)
//                .addOnSuccessListener { Log.d("Success", "Successfully ") }
//                .addOnFailureListener { Log.d("Failure", "Failed") }

        val consultationRequestMap = hashMapOf(
            "status" to "accept",
            "doctor_id" to doctorVO.id,
            "patient_id" to patientVO.id,
            "doctor_info" to doctorVO,
            "speciality" to doctorVO.speciality,
            "patient_info" to patientVO,
            "case_summary" to questionAnswerList,
            "consultation_id" to id
        )
        database.collection(consultation_request)
            .document(consulationId)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }


        val consulted_patient_id = UUID.randomUUID().toString()
        val consultedPatientMap = hashMapOf(
            "id" to consulted_patient_id,
            "patient_id" to patientVO.id
        )

        database.collection("$doctors/${doctorVO.id}/$consulted_patient")
            .document(consulted_patient_id)
            .set(consultedPatientMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

    }

    override fun sendBroadCastConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val id = UUID.randomUUID().toString()
        val consultationRequestMap = hashMapOf(
            "case_summary" to questionAnswerList,
            "id" to id,
            "patient_info" to patientVO,
            "doctor_info" to DoctorVO(),
            "speciality" to speciality,
            "status" to "none"
        )

        database.collection("consultation_request")
            .document(id)
            .set(consultationRequestMap)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully ")
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
                Log.d("Failure", "Failed")
            }

        database.collection("patients")
            .document(patientVO.id)
            .set(patientVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully")
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
                Log.d("Failure", "Failed ")
            }


        if (doctorVO.id.isNotEmpty()){

            var dataRequest = RequestFCM(
                data = (
                        Data("", "${patientVO.name} မှ \u200Bရောဂါဆိုင်ရာအတွက် \u200Bဆွေး\u200Bနွေးရန် \u200Bတောင်းဆိုထားပါသည်", "", "အ\u200Bကြောင်းကြားစာ", 0, id, "")
                        ),
                to = doctorVO.deviceID.toString()
            )

            mPatientModel.sendNotification(dataRequest)
        }
        else{
            var dataRequest = RequestFCM(
                data = (
                        Data("", "${patientVO.name} မှ \u200Bရောဂါဆိုင်ရာအတွက် \u200Bဆွေး\u200Bနွေးရန် \u200Bတောင်းဆိုထားပါသည်", "", "အ\u200Bကြောင်းကြားစာ", 0, id, "")
                        ),
                to = "fqOlIX8vSR-hCop6QJgb2y:APA91bFWmyfaLSmEVJkbKuJoJW4dAZD3jUJFSGkr9Dbh2UXPymYJvh2PSaBiASx2pXZon9NR4a2N08GovRUmTSBC418zhfgflHtBZUnF1xCzXAllaHFHu242FIcy8a46wYs6S8tFrB8B"
            )

            mPatientModel.sendNotification(dataRequest)
        }



    }

    override fun sendDirectRequest(
        speciality: String,
        dateTime: String,
        questionAnswerList: ArrayList<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val id = UUID.randomUUID().toString()
        val consultationRequestMap = hashMapOf(
            "case_summary" to questionAnswerList,
            "id" to id,
            "patient_info" to patientVO,
            "doctor_info" to doctorVO,
            "speciality" to speciality,
            "status" to "none"
        )

        database.collection("consultation_request")
            .document(id)
            .set(consultationRequestMap)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

        val consulted_patient_id = UUID.randomUUID().toString()
        val consultedPatientMap = hashMapOf(
            "id" to consulted_patient_id,
            "patient_id" to patientVO.id
        )

        database.collection("$doctors/${doctorVO.id}/$consulted_patient")
            .document(consulted_patient_id)
            .set(consultedPatientMap)
            .addOnSuccessListener {
                Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

        var dataRequest = RequestFCM(
            data = (
                    Data("", "${patientVO.name} မှ \u200Bရောဂါဆိုင်ရာအတွက် \u200Bဆွေး\u200Bနွေးရန် \u200Bတောင်းဆိုထားပါသည်", "", "အ\u200Bကြောင်းကြားစာ", 0, id, "")
                    ),
            to = doctorVO.deviceID.toString()
        )



        mPatientModel.sendNotification(dataRequest)
    }


    override fun addToPreScribeMedicine(
        documentID: String,
        prescriptionListVO: ArrayList<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        for (item in prescriptionListVO) {
            val id = UUID.randomUUID().toString()
            database.collection("consultation_chat")
                .document(documentID)
                .collection("prescription")
                .document(id)
                .set(item)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
        }


    }

//    override fun finishConsultation(
//        patientVO: PatientVO,
//        doctorVO: DoctorVO,
//        documentID: String,
//        prescriptionListVO: ArrayList<PrescriptionVO>,
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        for (item in prescriptionListVO) {
//            val id = UUID.randomUUID().toString()
//            database.collection("consultation_chat")
//                .document(documentID)
//                .collection("prescription")
//                .document(id)
//                .set(item)
//                .addOnSuccessListener { Log.d("Success", "Successfully ") }
//                .addOnFailureListener { Log.d("Failure", "Failed") }
//        }
//
////        getConsultationByPatientID(documentID,
////            onSuccess = {
////                it.finish_flag = true
////                database.collection("consultation_chat")
////                    .document(documentID)
////                    .set(it)
////                    .addOnSuccessListener { Log.d("Success", "Successfully ") }
////                    .addOnFailureListener { Log.d("Failure", "Failed") }
////            }, onFailure = {
////
////            })
//
//
//
//
//
//        database.collection("patients")
//            .document(patientVO.id)
//            .collection("recent_constulation_doctors")
//            .add(doctorVO)
//            .addOnSuccessListener { Log.d("Success", "Successfully ") }
//            .addOnFailureListener { Log.d("Failure", "Failed") }
//
//
//        val consulted_patient_id = UUID.randomUUID().toString()
//        val consultedPatientMap = hashMapOf(
//            "id" to consulted_patient_id,
//            "patient_id" to patientVO.id
//        )
//        database.collection("$doctors/${doctorVO.id}/$consulted_patient")
//            .document(consulted_patient_id)
//            .set(consultedPatientMap)
//            .addOnSuccessListener { Log.d("Success", "Successfully ") }
//            .addOnFailureListener { Log.d("Failure", "Failed") }
//
//
//
//    }

    override fun getConsultationByPatientID(
        patientId: String,
        onSuccess: (consultationVO: ConsultationChatVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("consultation_chat")
            .document(patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val hashmap = value?.data
                    hashmap?.put("id", value.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<ConsultationChatVO>(
                        Data,
                        ConsultationChatVO::class.java
                    )
                    onSuccess(docsData)

                }
            }

    }

    override fun getFinishConsultationByPatientID(
        patientID: String,
        onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection("consultation_chat")
            .whereEqualTo("finish_flag", true)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val finishConsultation: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData =
                            Gson().fromJson<ConsultationChatVO>(
                                dataJson,
                                ConsultationChatVO::class.java
                            )
                        if (docsData.patient_info?.id == patientID) {
                            finishConsultation.add(docsData)
                        }

                    }


                    onSuccess(finishConsultation)
                }

            }


    }

    override fun getBroadcastConsultationRequestByPatient(
        patientId: String,
        onSuccess: (consultationRequest: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        database.collection(consultation_request)
            .whereEqualTo("patient_id", patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }

    }

    override fun getBroadcastConsultationRequestBySpeciality(
        speciality: String,
        onSuccess: (list: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(consultation_request)
            .whereEqualTo("speciality", speciality)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun acceptRequest(
        status: String,
        consulationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationRequestMap = hashMapOf(
            "id" to consulationId,
            "status" to "accept",
            "doctor_id" to doctorVO.id,
            "patient_id" to patientVO.id,
            "doctor_info" to doctorVO,
            "speciality" to doctorVO.speciality,
            "patient_info" to patientVO,
            "case_summary" to questionAnswerList,
            "consultation_id" to ""
        )
        database.collection(consultation_request)
            .document(consulationId)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }


    override fun getBroadConsultationRequest(
        consulation_request_id: String,
        onSuccess: (consulationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(consultation_request)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )

                        if (docsData.id == consulation_request_id) {
                            list.add(docsData)
                        }
                    }
                    onSuccess(list[0])

                }
            }
    }

    override fun getBroadConsultationRequestByDoctorSpeciality(
        speciality: String,
        onSuccess: (consulationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(consultation_request)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )

                        if (docsData.speciality== speciality) {
                            list.add(docsData)
                        }
                    }
                    onSuccess(list[0])

                }
            }
    }

    override fun getConsultationPatient(
        doctorId: String,
        onSuccess: (List<ConsultedPatientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("$doctors/$doctorId/$consulted_patient")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultedPatientVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultedPatientVO>(
                            Data,
                            ConsultedPatientVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun addConsultationPatient(
        doctorId: String,
        patientId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val id = UUID.randomUUID().toString()
        val consulatedPatientMap = hashMapOf(
            "id" to id,
            "patient_id" to patientId
        )
//        database.collection("$doctors/$doctorId/$consulted_patient")
//                .document(id)
//                .set(consulatedPatientMap)
//                .addOnSuccessListener { Log.d("Success", "Successfully ") }
//                .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun startConsultationChatPatient(
        consulationChatId: String,
        consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationRequestMap = hashMapOf(
            "status" to "complete",
            "doctor_id" to consultationRequestVO.doctor_info?.id,
            "patient_id" to consultationRequestVO.patient_info?.id,
            "doctor_info" to consultationRequestVO.doctor_info,
            "speciality" to consultationRequestVO.doctor_info?.speciality,
            "patient_info" to consultationRequestVO.patient_info,
            "case_summary" to consultationRequestVO.case_summary,
            "consultation_id" to consulationChatId
        )
        database.collection(consultation_request)
            .document(consultationRequestVO.id)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getConsulationChatForDoctor(
        doctorId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("consultation_chat")
            .whereEqualTo("doctor_id", doctorId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun preScribeMedicine(
        consulationId: String,
        prescriptionVO: PrescriptionVO,
        onSuccess: () -> Unit,
        onFailure: String
    ) {
        val id = UUID.randomUUID().toString()
        val prescriptionVOMap = hashMapOf(
            "medicine_name" to prescriptionVO.medicine,
            "id" to id
        )

        database.collection("$consultation_chat/$consulationId/$prescription")
            .document(id)
            .set(prescriptionVOMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        //add recent doctor
        consultationChatVO.doctor_info?.let {


            consultationChatVO.patient_info?.id?.let { it1 ->
                database.collection("patients")
                    .document(it1)
                    .collection("recent_consultation_doctors")
                    .add(it)
                    .addOnSuccessListener { Log.d("Success", "Successfully ") }
                    .addOnFailureListener { Log.d("Failure", "Failed") }
            }
        }


        // update finish conservation status
        val consultationChatMap = hashMapOf(
            "finish_flag" to true,
            "id" to consultationChatVO.id,
            "patient_id" to consultationChatVO.patient_id,
            "doctor_id" to consultationChatVO.doctor_id,
            "case_summary" to consultationChatVO.case_summary,
            "patient_info" to consultationChatVO.patient_info,
            "medical_record" to consultationChatVO.medical_record,
            "doctor_info" to consultationChatVO.doctor_info,
            "start_consultation_date" to consultationChatVO.start_consultation_date
        )

        database.collection("$consultation_chat")
            .document(consultationChatVO.id)
            .set(consultationChatMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }


        // add prescription

        for (item in prescriptionList) {
            database.collection("$consultation_chat/${consultationChatVO.id}/$prescription")
                .document(item.id)
                .set(item)
                .addOnSuccessListener {
                    onSuccess()
                    Log.d("Success", "Successfully ")
                }
                .addOnFailureListener { Log.d("Failure", "Failed") }
        }


    }

    override fun getPrescription(
        consultationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("$consultation_chat/$consultationId/$prescription")
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<PrescriptionVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<PrescriptionVO>(Data, PrescriptionVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list)
            }
    }

    override fun updatePatientAddress(
        patients: PatientVO,
        addressLists: List<AddressVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationRequestMap = hashMapOf(
            "id" to patients.id,
            "name" to patients.name,
            "deviceID" to patients.deviceID,
            "photo" to patients.photo,
            "blood_type" to patients.blood_type,
            "blood_pressure" to patients.blood_pressure,
            "address" to addressLists,
            "weight" to patients.weight,
            "height" to patients.height,
            "dateOfBirth" to patients.dateOfBirth,
            "allergic_reactions" to patients.allergic_reactions,
            "one_time_general_question" to patients.one_time_general_question
        )


    }

    override fun getBroadcastConsultationRequest(
        consulation_request_id: String,
        onSuccess: (consulationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(consultation_request)
            .whereEqualTo("id", consulation_request_id)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    list?.let {
                        if (list.size > 0) {
                            onSuccess(list[0])
                        }
                    }

                }
            }
    }

    override fun getConsulationChatById(
        consulationId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("$consultation_chat")
            .whereEqualTo("id", consulationId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun updateDoctorData(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        database.collection(doctors)
                .document(doctorVO.id)
                .set(doctorVO)
                .addOnSuccessListener {
                    onSuccess()
                    Log.d("Success", "Successfully") }
                .addOnFailureListener {
                    Log.d("Failure", "Failed ") }


    }

    override fun saveMedicalRecord(
        consultationChatVO: ConsultationChatVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(consultation_chat)
            .document(consultationChatVO.id)
            .set(consultationChatVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getGeneralQuestion(
        onSuccess: (List<QuestionAnswerVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getGeneralQuestionTemplate(
        onSuccess: (List<GeneralQuestionTemplateVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("general_question_template")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val generalQuestionTemplateLists: MutableList<GeneralQuestionTemplateVO> =
                        arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        data?.put("id", document.id)
                        val dataJson = Gson().toJson(data)
                        val docsData = Gson().fromJson<GeneralQuestionTemplateVO>(
                            dataJson,
                            GeneralQuestionTemplateVO::class.java
                        )
                        generalQuestionTemplateLists.add(docsData)
                    }

                    onSuccess(generalQuestionTemplateLists)
                }
            }
    }

}