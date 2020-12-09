package com.padc.share.presistence.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.share.data.vos.QuestionAnswerVO

class GeneralQuestionTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<QuestionAnswerVO>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr: String): ArrayList<QuestionAnswerVO> {
        val dataListType = object : TypeToken<ArrayList<QuestionAnswerVO>>(){}.type
        return Gson().fromJson(ListJsonStr, dataListType)
    }
}