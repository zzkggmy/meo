package com.kai.meo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meo.bean.ThemeBean
import com.kai.meo.utils.Common
import com.kai.meo.utils.findColor
import com.kai.meo.utils.setThemeColor
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.theme_item.view.*

class ThemeAdapter(val list: ArrayList<ThemeBean>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ThemeHolder(LayoutInflater.from(Common.context).inflate(R.layout.theme_item, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_theme_name.text = list[position].name
        holder.itemView.tv_color_show.setBackgroundColor(findColor(list[position].color))

        if (list[position].isSelect) {
            holder.itemView.iv_select_theme.visibility = View.VISIBLE
        } else {
            holder.itemView.iv_select_theme.visibility = View.GONE
        }
        if (position == list.size - 1) {
            holder.itemView.v_theme.visibility = View.GONE
        }
        holder.itemView.ll_theme_item.setOnClickListener {
            onClick(holder.itemView, holder.adapterPosition)
            list.forEachIndexed { index, themeBean ->
                themeBean.isSelect = index == position
            }
            setThemeColor(list[position].color)
            notifyDataSetChanged()
        }
    }

    class ThemeHolder(view: View) : RecyclerView.ViewHolder(view)
}