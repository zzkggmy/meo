package com.kai.meo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kai.meo.bean.ComputerWallpaperBean
import com.kai.meo.utils.Common
import com.kai.meo.utils.options
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.computer_wallpaper_item.view.*
import kotlinx.android.synthetic.main.main_item.view.*

class ComputerWallpaperAdapter(val list: ArrayList<ComputerWallpaperBean.Res.Wallpaper>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ComputerWallpaperHolder(LayoutInflater.from(Common.context).inflate(R.layout.computer_wallpaper_item, null, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Glide.with(com.kai.meo.utils.Common.context)
                .asBitmap()
                .load(list[position].preview)
                .apply(options)
                .into(holder.itemView.iv_computer_wallpaper)
        holder.itemView.cv_computer_wallpaper.setOnClickListener {
            onClick(holder.itemView,holder.adapterPosition)
        }
    }

    class ComputerWallpaperHolder(view: View) : RecyclerView.ViewHolder(view)

}