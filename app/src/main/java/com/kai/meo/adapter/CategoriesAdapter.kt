package com.kai.meo.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kai.meo.utils.Common
import com.kai.meo.utils.options
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.categories_item.view.*
import kotlinx.android.synthetic.main.main_item.view.*

class CategoriesAdapter(val list: ArrayList<com.kai.meo.bean.CategoriesBean.Res.Category>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesHolder(LayoutInflater.from(Common.context).inflate(R.layout.categories_item, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_categories_item.text = list[position].name
        holder.itemView.cv_categories_item.setOnClickListener {
            onClick(holder.itemView,holder.adapterPosition)
        }
        Glide.with(com.kai.meo.utils.Common.context)
                .asBitmap()
                .load(list[position].cover)
                .apply { options }
                .into(holder.itemView.iv_categories_item)
    }

    class CategoriesHolder(view: View) : RecyclerView.ViewHolder(view)
}