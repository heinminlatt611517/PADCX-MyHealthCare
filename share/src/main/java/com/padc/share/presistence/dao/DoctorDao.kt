package com.padc.share.presistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.DoctorVO

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewDoctor(doctorVO: DoctorVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctorList(doctorList: List<DoctorVO>)

    @Query("select * from Doctor")
    fun getAllDoctorData(): LiveData<List<DoctorVO>>

    @Query("select * from Doctor WHERE email = :email")
    fun getAllDoctorDataByEmail(email: String): LiveData<DoctorVO>

    @Query("DELETE FROM Doctor")
    fun deleteAllDoctorData()
}