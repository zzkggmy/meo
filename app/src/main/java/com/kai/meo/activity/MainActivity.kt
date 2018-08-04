package com.kai.meo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import com.kai.meo.adapter.RecommendAdapter
import com.kai.meo.bean.RecommendBean
import com.kai.meo.http.Api
import com.kai.meo.utils.RecyclerItemDecoration
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.view.LoadingProgress
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class MainActivity : AppCompatActivity() {
    private var loadingProgress: LoadingProgress? = null
    private val list: ArrayList<RecommendBean.Res.Vertical> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        loadingProgress = LoadingProgress(this)
        loadingProgress!!.showLoding()
        getFeed()
        rv_main.layoutManager = staggeredGridLayoutManager
        rv_main.addItemDecoration(RecyclerItemDecoration(15,2))
        iv_menu_main.setOnClickListener { dl_main.openDrawer(nv_menu) }
        nv_menu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_categories -> startActivity(Intent(this, CategoryActivity::class.java))
//                R.id.nav_setting -> startActivity(Intent())
                R.id.nav_computer -> startActivity(Intent(this, ComputerWallpaperActivity::class.java))
            }
            false
        }
    }

    private fun getFeed() {
        async(UI) {
            val result = Api.retrofitService.getMain().await()
            if (result.code == 0) {
                loadingProgress!!.dismissLoding()
                list.addAll(result.res.vertical)
                rv_main.adapter = RecommendAdapter(list) { view, position ->
                    startActivity(Intent(this@MainActivity, PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            } else {
                Toast.makeText(this@MainActivity, result.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
