package com.kai.meo.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.kai.meo.bean.ComputerWallpaperCategoryBean
import com.kai.meo.fragment.ComputerFragment

class ComputerWallpaperPagerAdapter(fm: FragmentManager, private val wallpaperCategoryList: ArrayList<ComputerWallpaperCategoryBean.Res.Category>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle().apply {
            putString("id", wallpaperCategoryList[position].id)
        }
        return ComputerFragment().apply { arguments = bundle }
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount() = wallpaperCategoryList.size
    override fun getPageTitle(position: Int) = wallpaperCategoryList[position].name
}