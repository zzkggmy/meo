package com.kai.meo.http

class Api {
    companion object {
        val retrofitService: com.kai.meo.http.ApiService by lazy { RetrofitHelper.create("http://service.picasso.adesk.com/") }
    }
}