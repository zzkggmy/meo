package com.kai.meo.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kai.meo.adapter.ComputerWallpaperPagerAdapter
import com.kai.meo.bean.ComputerWallpaperCategoryBean
import com.kai.meo.fragment.ComputerWallpaperFragment
import com.kai.meo.http.Api
import com.kai.meo.utils.StatusBarUtil
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_computer_wallper.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ComputerWallpaperActivity : AppCompatActivity() {
    private val wallpaperCategoryList: ArrayList<ComputerWallpaperCategoryBean.Res.Category> = ArrayList()
    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val computerWallpaperFragment = ComputerWallpaperFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_wallper)
        tl_computer_wallpaper.layoutMode = TabLayout.MODE_SCROLLABLE
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.zhihu_primary))
        getComputerWallpaperCategory()
    }

    private fun getComputerWallpaperCategory() {
        async (UI) {
            val result = Api.retrofitService.getComputerWallpaperCategory().await()
            Toast.makeText(this@ComputerWallpaperActivity,result.code.toString(),Toast.LENGTH_SHORT).show()
            if (result.code == 0) {
                wallpaperCategoryList.addAll(result.res.category)
                for (i in 0 until result.res.category.size) {
                    fragmentList.add(computerWallpaperFragment)
                }
                vp_computer_wallpaper.adapter = ComputerWallpaperPagerAdapter(supportFragmentManager, fragmentList,wallpaperCategoryList)
            }
        }
    }
}