package com.kai.meo.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.kai.meo.bean.CategoryPicBean
import com.kai.meo.view.BaseActivity
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_category_pic.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoryPicActivity : BaseActivity() {

    private val list: ArrayList<CategoryPicBean.Res.Vertical> = ArrayList()

    override fun initView() {
        tv_title_category_pic.text = intent.getStringExtra("title")
        rv_category_pic.layoutManager = GridLayoutManager(this, 3)
        showLoading()
        getCategoryPic()
        iv_back_category_pic.setOnClickListener { finish() }
    }

    private fun getCategoryPic() {
        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getCategoryPic(intent.getStringExtra("id")).await()
            if (result.code == 0) {
                dismissLoading()
                list.addAll(result.res.vertical)
                rv_category_pic.adapter = com.kai.meo.adapter.CategoryPicAdapter(list) { view, position ->
                    startActivity(Intent(this@CategoryPicActivity, com.kai.meo.activity.PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }

    override fun bindLayout() = R.layout.activity_category_pic

}