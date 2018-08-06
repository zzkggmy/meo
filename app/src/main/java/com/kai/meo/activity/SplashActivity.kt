package com.kai.meo.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.kai.meo.http.ApiService
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.utils.getToken
import com.kai.meo.utils.picDetailsOptions
import com.kai.meo.view.BaseActivity
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {


    override fun initView() {
        StatusBarUtil.setTranslucent(this,0)
        tv_skip_splash.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                ll_splash.background = resource
            }
        }
        Glide.with(com.kai.meo.utils.Common.context)
                .load("https://cn.bing.com/az/hprichbg/rb/PortAntonio_ZH-CN10325538004_1920x1080.jpg")
                .apply(picDetailsOptions)
                .into(simpleTarget)
        Handler().postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }


    private fun getEveryPic() {
        async(UI) {
            val result = create("http://www.bing.com/HPImageArchive.aspx?").getEverydayPic().await()
            Log.d("as", result.images.toString())

        }

    }

    private fun create(url: String): ApiService {
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

    override fun bindLayout() = R.layout.activity_splash

}