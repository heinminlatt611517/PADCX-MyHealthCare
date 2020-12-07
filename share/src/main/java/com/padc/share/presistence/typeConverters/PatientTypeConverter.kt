package com.padc.share.presistence.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.share.data.vos.PatientVO


class PatientTypeConverter {
    @TypeConverter
    fun toString(dataList: PatientVO): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr: String): PatientVO {
        val dataListType = object : TypeToken<PatientVO>() {}.type
        return Gson().fromJson(ListJsonStr, dataListType)
    }
}