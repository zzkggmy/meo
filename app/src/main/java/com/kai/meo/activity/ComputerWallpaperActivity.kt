package com.kai.meo.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.kai.meo.adapter.ComputerWallpaperPagerAdapter
import com.kai.meo.bean.ComputerWallpaperCategoryBean
import com.kai.meo.http.Api
import com.kai.meo.utils.StatusBarUtil
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_computer_wallper.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class ComputerWallpaperActivity : AppCompatActivity() {
    private val wallpaperCategoryList: ArrayList<ComputerWallpaperCategoryBean.Res.Category> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_wallper)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        iv_back_computer_wallpaper.setOnClickListener { finish() }
        tl_computer_wallpaper.layoutMode = TabLayout.MODE_SCROLLABLE
        ViewCompat.setElevation(tl_computer_wallpaper, 10f)
        getComputerWallpaperCategory()
    }

    private fun getComputerWallpaperCategory() {
        async(UI) {
            val result = Api.retrofitService.getComputerWallpaperCategory().await()
            if (result.code == 0) {
                wallpaperCategoryList.clear()
                wallpaperCategoryList.addAll(result.res.category)
                vp_computer_wallpaper.adapter = ComputerWallpaperPagerAdapter(supportFragmentManager, wallpaperCategoryList)
                tl_computer_wallpaper.setupWithViewPager(vp_computer_wallpaper, true)
            }
        }
    }
}