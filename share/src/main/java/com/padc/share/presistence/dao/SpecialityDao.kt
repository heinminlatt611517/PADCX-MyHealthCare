package com.padc.share.presistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.SpecialitiesVO

@Dao
interface SpecialityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialities(specialities: List<SpecialitiesVO>)

    @Query("select * from specialites")
    fun getAllSpecialitiesData(): LiveData<List<SpecialitiesVO>>

    @Query("select * from specialites WHERE sp_id = :id")
    fun getAllSpecialitiesBy(id: String): LiveData<SpecialitiesVO>

    @Query("DELETE FROM specialites")
    fun deleteSpecialities()
}