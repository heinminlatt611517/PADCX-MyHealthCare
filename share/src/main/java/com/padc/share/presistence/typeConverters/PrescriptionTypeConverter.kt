package com.padc.share.presistence.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.share.data.vos.PrescriptionVO
import com.padc.share.data.vos.QuestionAnswerVO

class PrescriptionTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<PrescriptionVO>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr: String): ArrayList<PrescriptionVO> {
        val dataListType = object : TypeToken<ArrayList<PrescriptionVO>>(){}.type
        return Gson().fromJson(ListJsonStr, dataListType)
    }
}