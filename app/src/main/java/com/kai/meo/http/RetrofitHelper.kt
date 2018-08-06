package com.kai.meo.http

import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.kai.meo.utils.getToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    fun create(url: String): ApiService {
        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val request: Request = if (!TextUtils.isEmpty(getToken)) {
                    chain.request().newBuilder().addHeader("Authorization", "Bearer $getToken").build()
                } else {
                    chain.request()
                }
                val response: Response = chain.proceed(request)
                response
            }
        }
        return Retrofit.Builder().baseUrl(url).client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(com.kai.meo.http.ApiService::class.java)
    }
}