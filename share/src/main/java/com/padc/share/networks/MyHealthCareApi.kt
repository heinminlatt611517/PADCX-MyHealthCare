package com.padc.share.networks

import com.padc.share.networks.RequestFCMBody.RequestFCM
import com.padc.share.utils.FIREBASE_SERVER_KEY
import com.squareup.okhttp.Call
import com.squareup.okhttp.ResponseBody
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MyHealthCareApi {

 @Headers(*["Authorization: key=$FIREBASE_SERVER_KEY", "Content-Type:application/json"])
 @POST("fcm/send")
  fun sendNotificationToDoctorByDeviceID(@Body request: RequestFCM) : Observable<RequestFCM>

}