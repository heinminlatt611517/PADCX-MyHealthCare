package com.padc.share.presistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.share.data.vos.SpecialQuestionVO



@Dao
interface SpecialQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialQuestions(special_questions: SpecialQuestionVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialQuestions(special_questions: List<SpecialQuestionVO>)

    @Query("select * from special_question")
    fun getAllSpecialQuestionsData(): LiveData<List<SpecialQuestionVO>>

    @Query("select * from special_question WHERE id = :id")
    fun getAllSpecialQuestionsBy(id: String): LiveData<SpecialQuestionVO>

    @Query("DELETE FROM special_question")
    fun deleteSpecialQuestions()


}