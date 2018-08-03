package com.kai.meo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.frag_categories.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoriesFragment : Fragment() {
    private val list: ArrayList<com.kai.meo.bean.CategoriesBean.Res.Category> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frag_categories, null, false)
        getCategories(view)
        view.rv_categories_frag.layoutManager = GridLayoutManager(activity, 3)
        view.rv_categories_frag.itemAnimator = DefaultItemAnimator()
        return view
    }

    private fun getCategories(view: View) {
        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getCategories().await()
            if (result.code == 0) {
                list.addAll(result.res.category)
                view.rv_categories_frag.adapter = com.kai.meo.adapter.CategoriesAdapter(list) { view, position ->
                    startActivity(Intent(activity, com.kai.meo.activity.CategoryPicActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }
}