package com.padc.patient.delegates

import com.padc.share.data.vos.DoctorVO

interface RecentDoctorItemDelegate {
    fun onTapRecentDoctorItem(doctorVO : DoctorVO)
}