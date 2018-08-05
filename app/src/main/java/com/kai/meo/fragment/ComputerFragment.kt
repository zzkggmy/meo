package com.kai.meo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meo.activity.PicDetailsActivity
import com.kai.meo.adapter.ComputerWallpaperAdapter
import com.kai.meo.bean.ComputerWallpaperBean
import com.kai.meo.http.Api
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.fragment_computer_wallpaper.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ComputerFragment : Fragment() {
    private val list: ArrayList<ComputerWallpaperBean.Res.Wallpaper> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(activity, R.layout.fragment_computer_wallpaper, null)
        view.rv_computer_wallpaper.layoutManager = GridLayoutManager(activity,2)
        this.getComputerWallpaper(arguments!!.getString("id"), view)
        return view
    }

    private fun getComputerWallpaper(id: String, view: View) {
        async(UI) {
            val result = Api.retrofitService.getComputerWallpaper(id).await()
            if (result.code == 0) {
                list.addAll(result.res.wallpaper)
                view.rv_computer_wallpaper.adapter = ComputerWallpaperAdapter(list) { view, position ->
                    startActivity(Intent(activity, PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }
}