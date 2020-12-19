package com.padc.share.data.vos

import android.graphics.Picture

data class FacebookUser (
    val email: String,
    val first_name: String,
    val id: String,
    val last_name: String,
    val picture: Picture
)
