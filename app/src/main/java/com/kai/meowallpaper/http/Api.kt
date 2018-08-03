package com.kai.meowallpaper.http

class Api {
    companion object {
        val retrofitService: ApiService by lazy { RetrofitHelper.create("http://service.picasso.adesk.com/") }
    }
}