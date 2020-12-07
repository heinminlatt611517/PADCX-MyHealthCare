package com.padc.share.networks.RequestFCMBody

data class Data(
    val action_url: String,
    val body: String,
    val created: String,
    val title: String,
    val type: Int,
    val type_name: String
)