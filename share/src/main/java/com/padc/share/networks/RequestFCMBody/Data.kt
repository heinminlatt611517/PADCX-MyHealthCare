package com.padc.share.networks.RequestFCMBody

import com.padc.share.data.vos.ConsultationRequestVO

data class Data(
    val action_url: String,
    val body: String,
    val created: String,
    val title: String,
    val type: Int,
    val consultationRequestID : String,
    val type_name: String
)