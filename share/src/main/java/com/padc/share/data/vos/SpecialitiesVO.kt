package com.padc.share.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties



@Entity(tableName = "specialites")
@IgnoreExtraProperties
class SpecialitiesVO(
    @PrimaryKey
    var sp_id: String= "",
    var name: String = "",
    var photo: String = ""

)
