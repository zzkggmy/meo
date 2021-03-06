package com.kai.meo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kai.meo.bean.CommentBean
import com.kai.meo.utils.Common
import com.kai.meo.utils.options
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentAdapter(val list: ArrayList<CommentBean.Res.Comment>, val onClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CommentHolder(LayoutInflater.from(Common.context).inflate(R.layout.comment_item, parent, false))


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_content_comment_item.text = list[position].content
        holder.itemView.tv_user_name_item.text = list[position].user.name
        list.forEachIndexed { index, comment ->
            if (comment.user.isvip) {
                Glide.with(Common.context)
                        .load(R.drawable.ic_is_vip)
                        .into(holder.itemView.iv_vip_comment)
            } else {
                Glide.with(Common.context)
                        .load(R.drawable.ic_no_vip)
                        .into(holder.itemView.iv_vip_comment)
            }
        }
        Glide.with(com.kai.meo.utils.Common.context)
                .asBitmap()
                .load(list[position].user.avatar)
                .apply { options }
                .into(holder.itemView.iv_user_avatar_item)
    }

    class CommentHolder(view: View) : RecyclerView.ViewHolder(view)
    class EmptyCommentHolder(view: View) : RecyclerView.ViewHolder(view)
}