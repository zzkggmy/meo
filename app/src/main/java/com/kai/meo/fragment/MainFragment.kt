package com.kai.meo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.frag_main.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class MainFragment : Fragment() {
    private val list: ArrayList<com.kai.meo.bean.RecommendBean.Res.Vertical> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frag_main, null, false)
        getFeed(view)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        view.rv_main_frag.layoutManager = staggeredGridLayoutManager
        return view
    }

    private fun getFeed(view: View) {
        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getMain().await()

            if (result.code == 0) {
                list.addAll(result.res.vertical)
                view.rv_main_frag.adapter = com.kai.meo.adapter.RecommendAdapter(list) { view, position ->
                    startActivity(Intent(activity, com.kai.meo.activity.PicDetailsActivity::class.java).putExtra("id", list[position].id))
                }
            } else {
                Toast.makeText(activity, result.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}