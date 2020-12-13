package com.padc.share.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class AddressVO (
    var state: String= "",
    var street: String = "",
    var township : String = "",
    var fullAddress : String = ""
)
