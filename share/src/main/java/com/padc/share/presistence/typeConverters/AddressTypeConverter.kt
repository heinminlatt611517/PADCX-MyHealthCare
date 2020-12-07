package com.padc.share.presistence.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.share.data.vos.AddressVO
import com.padc.share.data.vos.DoctorVO


class AddressTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<AddressVO>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr: String): ArrayList<AddressVO> {
        val dataListType = object : TypeToken<ArrayList<AddressVO>>() {}.type
        return Gson().fromJson(ListJsonStr, dataListType)
    }
}