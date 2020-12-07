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
    fun insertDoctor(doctor: DoctorVO)

    @Query("SELECT * FROM Doctor")
    fun getAllDoctors(): LiveData<List<DoctorVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctorList(doctorList: List<DoctorVO>)
}