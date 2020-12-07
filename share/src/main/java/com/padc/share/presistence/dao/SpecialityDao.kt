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
    fun insertSpecialities(specialities: SpecialitiesVO)

    @Query("select * from specialities")
    fun getAllSpecialitiesData(): LiveData<List<SpecialitiesVO>>

    @Query("select * from specialities WHERE sp_id = :id")
    fun getAllSpecialitiesBy(id: String): LiveData<SpecialitiesVO>

    @Query("DELETE FROM specialities")
    fun deleteSpecialities()
}