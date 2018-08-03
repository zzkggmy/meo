package com.kai.meowallpaper.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kai.meowallpaper.R
import com.kai.meowallpaper.bean.RecommendBean
import com.kai.meowallpaper.utils.Common
import com.kai.meowallpaper.utils.options
import kotlinx.android.synthetic.main.main_item.view.*

class RecommendAdapter(val list: ArrayList<RecommendBean.Res.Vertical>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FeedHolder(LayoutInflater.from(Common.context).inflate(R.layout.main_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(Common.context)
                .asBitmap()
                .load(list[position].thumb)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        val width = resource!!.width
                        val height = resource.height
                        if (width > height) {
                            holder.itemView.iv_main_frag_item.scaleType = ImageView.ScaleType.FIT_XY;
                        } else if (width <= height) {
                            holder.itemView.iv_main_frag_item.scaleType = ImageView.ScaleType.FIT_CENTER;
                        }
                        Log.d("tag", "" + width + "adsada" + height)
                        return false
                    }
                })
                .apply(options)
                .into(holder.itemView.iv_main_frag_item)
        holder.itemView.cv_main_frag_item.setOnClickListener {
            onClick(holder.itemView, holder.adapterPosition)
        }
    }

    class FeedHolder(view: View) : RecyclerView.ViewHolder(view)
}