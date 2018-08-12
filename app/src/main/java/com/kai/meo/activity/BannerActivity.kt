package com.kai.meo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kai.meo.http.Api
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_banner.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class BannerActivity : AppCompatActivity() {
    val list: ArrayList<Any> = ArrayList()
    val titleList: ArrayList<String> = ArrayList()
    val urls: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        getFeed()
        for (i in 0 until 4) {
            list.add(i)
            titleList.add(i.toString())
        }
        urls.add("http://www.ctsay.com/img/16/0321/56ef5ac94368c.jpeg");
        urls.add("http://www.ctsay.com/img/16/0331/56fc8af888536.jpg");
        urls.add("http://www.ctsay.com/img/16/0205/56b3f70240f6b.jpg");
        urls.add("http://www.ctsay.com/img/15/1215/566f81191b023.jpg");
        banner.setView(list,urls)
    }

    private fun getFeed() {
        async(UI) {
            val result = Api.retrofitService.getMain().await()
            if (result.code == 0) {
                for (i in 0 until list.size) {
//                    urls.add(result.res.vertical[i].thumb)
                }

            } else {
            }
        }
    }
}