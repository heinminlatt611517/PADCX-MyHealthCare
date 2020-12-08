package com.padc.share.networks

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.padc.share.data.vos.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

object CloudFireStoreFirebaseApiImpl : FirebaseApi {

    private val database = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

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
                .document(doctors.dr_id)
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
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: "Please check connection")
                    } ?: run {

                        val specialityList: MutableList<SpecialitiesVO> = arrayListOf()

                        val result = value?.documents ?: arrayListOf()

                        for (document in result) {
                            val data = document.data
                            var speciality = SpecialitiesVO()
                            speciality.sp_id = data?.get("id") as String
                            speciality.name = data["name"] as String
                            speciality.photo = data["photo"] as String

                            specialityList.add(speciality)
                        }

                        onSuccess(specialityList)
                    }
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

    override fun getRecentDoctor(
            patientID: String,
            onSuccess: (doctors: List<DoctorVO>) -> Unit,
            onFailure: (String) -> Unit
    ) {
        database.collection("patients")
                .document(patientID).collection("recent_constulation_doctors")
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

    override fun addAndUpdateRecentDoctor(
            patientID: String,
            doctorVO: DoctorVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        val recentDoctorMap = hashMapOf(
                "id" to doctorVO.dr_id,
                "name" to doctorVO.name,
                "speciality_name" to doctorVO.speciality
        )
        database.collection("patients")
                .document(patientID)
                .collection("recent_constulation_doctors")
                .document(doctorVO.dr_id)
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


    override fun getChatMessage(
            messageId: String,
            onSuccess: (messages: List<ChatMessageVO>) -> Unit,
            onFailure: (String) -> Unit
    ) {
        database.collection("consultation_chat")
                .document(messageId)
                .collection("chat_message")
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
            messageId: String,
            chatMessageVO: ChatMessageVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        database.collection("consultation_chat")
                .document(messageId)
                .collection("chat_message")
                .document(chatMessageVO.id)
                .set(chatMessageVO)
                .addOnSuccessListener { Log.d("Success", "Successfully added patient") }
                .addOnFailureListener { Log.d("Failure", "Failed to add patient") }

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
                .addOnSuccessListener { Log.d("Success", "Successfully added patient") }
                .addOnFailureListener { Log.d("Failure", "Failed to add patient") }
    }


    override fun startConsultation(
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
                "patient_info" to patientVO,
                "doctor_info" to doctorVO
        )

        database.collection("consultation_chat")
                .document(id)
                .set(consultationChatMap)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }

        database.collection("patients")
                .document(doctorVO.dr_id)
                .collection("recent_constulation_doctors")
                .add(doctorVO)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
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
        val id = UUID.randomUUID().toString()
        val consultationRequestMap = hashMapOf(
                "case_summary" to questionAnswerList,
                "id" to id,
                "doctor_info" to doctorVO,
                "patient_info" to patientVO,
                "speciality" to speciality
        )

        database.collection("consultation_request")
                .document(id)
                .set(consultationRequestMap)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
    }


    override fun sendBroadCastConsultationRequest(
            speciality: String,
            questionAnswerList: List<QuestionAnswerVO>,
            patientVO: PatientVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        val id = UUID.randomUUID().toString()
        val consultationRequestMap = hashMapOf(
                "case_summary" to questionAnswerList,
                "id" to id,
                "patient_info" to patientVO,
                "speciality" to speciality
        )

        database.collection("consultation_request")
                .document(id)
                .set(consultationRequestMap)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
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

    override fun finishConsultation(
            patientVO: PatientVO,
            doctorVO: DoctorVO,
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

        getConsultationByPatientID(documentID,
                onSuccess = {
                    it.finish_flag = true
                    database.collection("consultation_chat")
                            .document(documentID)
                            .set(it)
                            .addOnSuccessListener { Log.d("Success", "Successfully ") }
                            .addOnFailureListener { Log.d("Failure", "Failed") }
                }, onFailure = {

        })

        addAndUpdateRecentDoctor(patientVO.id, doctorVO, onSuccess, onFailure)

    }

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

    override fun getFinishConsultationByPatientID(patientID: String,
                                                  onSuccess: (consultationVO: List<ConsultationChatVO>) -> Unit,
                                                  onFailure: (String) -> Unit) {

        database.collection("consultation_chat")
                .whereEqualTo("finish_flag",false)
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
                                    Gson().fromJson<ConsultationChatVO>(dataJson, ConsultationChatVO::class.java)
                            if (docsData.id == patientID)
                                finishConsultation.add(docsData)
                        }

                        onSuccess(finishConsultation)
                    }

                }


    }


    override fun acceptRequest() {
        TODO("Not yet implemented")
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