package com.padc.share.networks

import com.padc.share.networks.RequestFCMBody.RequestFCM
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MyHealthCareApi {
 @POST
 fun sendNotificationToDoctorByDeviceID(@Body request: RequestFCM, @Header("Authorization") value: String?)

}