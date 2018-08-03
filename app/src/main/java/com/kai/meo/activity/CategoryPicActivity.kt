package com.kai.meo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kai.meo.utils.StatusBarUtil
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_category_pic.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoryPicActivity : AppCompatActivity() {
    private val list: ArrayList<com.kai.meo.bean.CategoryPicBean.Res.Vertical> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_pic)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        rv_category_pic.layoutManager = GridLayoutManager(this, 3)
        getCategoryPic()
    }

    private fun getCategoryPic() {
        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getCategoryPic(intent.getStringExtra("id")).await()
            if (result.code == 0) {
                list.addAll(result.res.vertical)
                rv_category_pic.adapter = com.kai.meo.adapter.CategoryPicAdapter(list) { view, position ->
                    startActivity(Intent(this@CategoryPicActivity, com.kai.meo.activity.PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }
}