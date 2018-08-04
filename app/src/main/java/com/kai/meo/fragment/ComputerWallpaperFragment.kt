package com.kai.meo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meo.adapter.ComputerWallpaperAdapter
import com.kai.meo.bean.ComputerWallpaperBean
import com.kai.meo.http.Api
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.fragment_computer_wallpaper.*
import kotlinx.android.synthetic.main.fragment_computer_wallpaper.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ComputerWallpaperFragment : Fragment() {
    private val list: ArrayList<ComputerWallpaperBean.Res.Wallpaper> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_computer_wallpaper, null)
        getComputerWallpaper(view)
        rv_computer_wallpaper.layoutManager = LinearLayoutManager(activity)
        return view
    }

    private fun getComputerWallpaper( view: View) {
        async(UI) {
            val result = Api.retrofitService.getComputerWallpaper(arguments!!.getString("id")).await()
            if (result.code == 0) {
                list.addAll(result.res.wallpaper)
                view.rv_computer_wallpaper.adapter = ComputerWallpaperAdapter(list) { view, position ->

                }
            }
        }
    }
}