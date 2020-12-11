package com.padc.share.presistence.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.ConsultationRequestVO


@Dao
interface ConsultationRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultationRequest(consultationRequestVO: ConsultationRequestVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultationRequestData(consultationRequestList: List<ConsultationRequestVO>)

    @Query("select * from consultation_request")
    fun getAllConsultationRequestData(): LiveData<List<ConsultationRequestVO>>

    @Query("select * from consultation_request WHERE speciality = :speciality")
    fun getAllConsultationRequestDataBySpeciality(speciality: String): LiveData<List<ConsultationRequestVO>>

    @Query("DELETE FROM consultation_request")
    fun deleteAllConsultationRequestData()

    @Query("DELETE FROM consultation_request where id =  :id")
    fun deleteAllConsultationRequestDataById(id : String)

    @Query("UPDATE consultation_request SET patient_type_status=:status WHERE id = :consultation_request_id")
    fun updatePatientTypeStatus(status: String, consultation_request_id : String)

    @Query("select * from consultation_request where status = :accept")
    fun getConsultationAcceptData(accept : String): LiveData<List<ConsultationRequestVO>>

    @Query("select * from consultation_request WHERE consultation_id = :consultation_request_id")
    fun getConsultationRequestByConsultationRequestId(consultation_request_id: String): LiveData<ConsultationRequestVO>
}