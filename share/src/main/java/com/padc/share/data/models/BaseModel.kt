package com.padc.share.data.models

import android.content.Context
import com.padc.share.BuildConfig
import com.padc.share.networks.MyHealthCareApi
import com.padc.share.presistence.db.MyHealthCareDB
import com.padc.share.utils.FIREBASE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseModel {
    protected lateinit var mTheDB: MyHealthCareDB
    protected var myHealthCareApi : MyHealthCareApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(FIREBASE_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

            .build()


        myHealthCareApi = retrofit.create(MyHealthCareApi::class.java)
    }


    fun initDatabase(context: Context) {
        mTheDB = MyHealthCareDB.getDbInstance(context)
    }

}