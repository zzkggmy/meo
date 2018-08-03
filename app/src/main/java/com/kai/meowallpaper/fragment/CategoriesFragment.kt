package com.kai.meowallpaper.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kai.meowallpaper.R
import com.kai.meowallpaper.activity.CategoryPicActivity
import com.kai.meowallpaper.adapter.CategoriesAdapter
import com.kai.meowallpaper.bean.CategoriesBean
import com.kai.meowallpaper.http.Api
import kotlinx.android.synthetic.main.frag_categories.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CategoriesFragment : Fragment() {
    private val list: ArrayList<CategoriesBean.Res.Category> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frag_categories, null, false)
        getCategories(view)
        view.rv_categories_frag.layoutManager = GridLayoutManager(activity, 3)
        view.rv_categories_frag.itemAnimator = DefaultItemAnimator()
        return view
    }

    private fun getCategories(view: View) {
        async(UI) {
            val result = Api.retrofitService.getCategories().await()
            if (result.code == 0) {
                list.addAll(result.res.category)
                view.rv_categories_frag.adapter = CategoriesAdapter(list) { view, position ->
                    startActivity(Intent(activity, CategoryPicActivity::class.java).putExtra("id", list[position].id))
                }
            }
        }
    }
}