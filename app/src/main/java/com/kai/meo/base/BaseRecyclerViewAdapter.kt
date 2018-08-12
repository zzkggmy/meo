package com.kai.meo.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class BaseRecyclerViewAdapter(val list: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class BaseHolder(view: View) : RecyclerView.ViewHolder(view)
}