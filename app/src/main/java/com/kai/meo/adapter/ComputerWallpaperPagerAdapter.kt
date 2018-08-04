package com.kai.meo.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kai.meo.bean.ComputerWallpaperCategoryBean
import com.kai.meo.fragment.ComputerWallpaperFragment

class ComputerWallpaperPagerAdapter(fm: FragmentManager, val fragList: ArrayList<Fragment>, val wallpaperCategoryList: ArrayList<ComputerWallpaperCategoryBean.Res.Category>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle().apply {
            putString("id",wallpaperCategoryList[position].id)
        }
        return ComputerWallpaperFragment().apply { arguments = bundle }
    }

    override fun getCount() = fragList.size
    override fun getPageTitle(position: Int): CharSequence? {
        return wallpaperCategoryList[position].name
    }
}