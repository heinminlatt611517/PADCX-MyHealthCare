package com.padc.share.presistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.PatientVO
import com.padc.share.data.vos.SpecialitiesVO

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatient(patient: PatientVO)

    @Query("SELECT * FROM Patient")
    fun getAllPatients(): LiveData<List<PatientVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatientList(patientList: List<PatientVO>)

    @Query("select * from Patient WHERE name = :patientName")
    fun getPatientByName(patientName: String): LiveData<PatientVO>

    @Query("select * from Patient WHERE patientID = :id")
    fun getPatientByID(id: String): LiveData<PatientVO>





}