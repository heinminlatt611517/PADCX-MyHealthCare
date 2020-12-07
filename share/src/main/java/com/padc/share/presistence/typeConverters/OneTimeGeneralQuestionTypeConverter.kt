package com.padc.share.presistence.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.share.data.vos.DoctorVO
import com.padc.share.data.vos.OneTimeGeneralQuestionVO


class OneTimeGeneralQuestionTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<OneTimeGeneralQuestionVO>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr: String): ArrayList<OneTimeGeneralQuestionVO> {
        val dataListType = object : TypeToken<ArrayList<OneTimeGeneralQuestionVO>>() {}.type
        return Gson().fromJson(ListJsonStr, dataListType)
    }
}