package com.kai.meo.adapter

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
import com.kai.meo.utils.options
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.main_item.view.*

class RecommendAdapter(val list: ArrayList<com.kai.meo.bean.RecommendBean.Res.Vertical>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FeedHolder(LayoutInflater.from(com.kai.meo.utils.Common.context).inflate(R.layout.main_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(com.kai.meo.utils.Common.context)
                .asBitmap()
                .load(list[position].thumb)
                .apply(options)
                .into(holder.itemView.iv_main_frag_item)
        holder.itemView.cv_main_frag_item.setOnClickListener {
            onClick(holder.itemView, holder.adapterPosition)
        }
    }

    class FeedHolder(view: View) : RecyclerView.ViewHolder(view)
}