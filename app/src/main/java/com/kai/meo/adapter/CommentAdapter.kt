package com.kai.meo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentAdapter(val list: ArrayList<com.kai.meo.bean.CommentBean.Res.Comment>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder(LayoutInflater.from(com.kai.meo.utils.Common.context).inflate(R.layout.comment_item, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_content_comment_item.text = list[position].content
    }

    class CommentHolder(view: View) : RecyclerView.ViewHolder(view)

}