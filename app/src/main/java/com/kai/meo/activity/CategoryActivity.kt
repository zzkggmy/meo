package com.kai.meo.activity

import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.kai.meo.adapter.CategoriesAdapter
import com.kai.meo.bean.CategoriesBean
import com.kai.meo.http.Api
import com.kai.meo.utils.RecyclerItemDecoration
import com.kai.meo.view.BaseActivity
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoryActivity : BaseActivity() {

    private val list: ArrayList<CategoriesBean.Res.Category> = ArrayList()

    override fun initView() {
        showLoading()
        getCategories()
        rv_category.layoutManager = GridLayoutManager(this, 4)
        rv_category.itemAnimator = DefaultItemAnimator()
        rv_category.addItemDecoration(RecyclerItemDecoration(15,4))
        iv_back_category.setOnClickListener { finish() }
    }

    private fun getCategories() {
        async(UI) {
            val result = Api.retrofitService.getCategories().await()
            if (result.code == 0) {
                dismissLoading()
                list.addAll(result.res.category)
                rv_category.adapter = CategoriesAdapter(list) { view, position ->
                    startActivity(Intent(this@CategoryActivity, CategoryPicActivity::class.java).putExtra("id", list[position].id).putExtra("title", list[position].name))
                }
            }
        }
    }

    override fun bindLayout() = R.layout.activity_category

}