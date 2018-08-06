package com.kai.meo.activity

import android.content.Intent
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import com.kai.meo.adapter.RecommendAdapter
import com.kai.meo.bean.RecommendBean
import com.kai.meo.http.Api
import com.kai.meo.utils.RecyclerItemDecoration
import com.kai.meo.view.BaseActivity
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class MainActivity : BaseActivity() {


    private val list: ArrayList<RecommendBean.Res.Vertical> = ArrayList()

    override fun initView() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        showLoading()
        getFeed()
        rv_main.layoutManager = staggeredGridLayoutManager
        rv_main.addItemDecoration(RecyclerItemDecoration(15,2))
        tv.setOnClickListener { showSnackBar("") }
        iv_menu_main.setOnClickListener { dl_main.openDrawer(nv_menu) }
        nv_menu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_categories -> startActivity(Intent(this, CategoryActivity::class.java))
                R.id.nav_computer -> startActivity(Intent(this, ComputerWallpaperActivity::class.java))
                R.id.nav_setting -> startActivity(Intent(this, ThemeActivity::class.java))
            }
            false
        }
    }

    private fun getFeed() {
        async(UI) {
            val result = Api.retrofitService.getMain().await()
            if (result.code == 0) {
                dismissLoading()
                list.addAll(result.res.vertical)
                rv_main.adapter = RecommendAdapter(list) { view, position ->
                    startActivity(Intent(this@MainActivity, PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            } else {
                Toast.makeText(this@MainActivity, result.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun bindLayout() = R.layout.activity_main

}
