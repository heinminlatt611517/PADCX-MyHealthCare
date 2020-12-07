package com.padc.patient.delegates

import android.content.Context
import com.padc.share.data.vos.SpecialitiesVO

interface SpecialityDoctorItemDelegate {
    fun onTapSpecialityDoctorItem(specialitiesVO: SpecialitiesVO)
}