package com.kai.meowallpaper.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kai.meowallpaper.R
import com.kai.meowallpaper.adapter.CategoryPicAdapter
import com.kai.meowallpaper.bean.CategoryPicBean
import com.kai.meowallpaper.http.Api
import com.kai.meowallpaper.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_category_pic.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoryPicActivity : AppCompatActivity() {
    private val list: ArrayList<CategoryPicBean.Res.Vertical> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_pic)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        rv_category_pic.layoutManager = GridLayoutManager(this, 3)
        getCategoryPic()
    }

    private fun getCategoryPic() {
        async(UI) {
            val result = Api.retrofitService.getCategoryPic(intent.getStringExtra("id")).await()
            if (result.code == 0) {
                list.addAll(result.res.vertical)
                rv_category_pic.adapter = CategoryPicAdapter(list) { view, position ->
                    startActivity(Intent(this@CategoryPicActivity, PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }
}