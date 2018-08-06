package com.kai.meo.activity

import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.kai.meo.adapter.CommentAdapter
import com.kai.meo.bean.CommentBean
import com.kai.meo.http.Api
import com.kai.meo.utils.Common
import com.kai.meo.utils.SavePicManager
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.utils.picDetailsOptions
import com.kai.meo.view.BaseActivity
import com.kai.meo.view.EmptyRecyclerView
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_pic_details.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class PicDetailsActivity : BaseActivity() {

    private var imgUrl = ""
    private lateinit var emptyRecyclerView: EmptyRecyclerView
    private val list: ArrayList<CommentBean.Res.Comment> = ArrayList()
    override fun initView() {
        StatusBarUtil.setTranslucent(this, 0)
        emptyRecyclerView = EmptyRecyclerView(this)
        emptyRecyclerView = rv_comment_pic_details as EmptyRecyclerView
        imgUrl = "http://img5.adesk.com/" + intent.getStringExtra("id")
        iv_back_pic_details.setOnClickListener { finish() }
        showLoading()

        tv_download_pic.setOnClickListener { SavePicManager.savePhoto(imgUrl) }
        getComment()
        val behavior = BottomSheetBehavior.from(nsv_comment)
        behavior.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
            }
        })
        tv_comment.setOnClickListener { behavior.setState(BottomSheetBehavior.STATE_EXPANDED) }
        val simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                cl_pic_details.background = resource
                if (resource.isVisible) {
                    dismissLoading()
                }
            }
        }
        Glide.with(Common.context)
                .load(imgUrl)
                .apply(picDetailsOptions)
                .into(simpleTarget)

        emptyRecyclerView.layoutManager = LinearLayoutManager(this)
        if (list.size >= 1) {
            tv_comment.setOnClickListener { behavior.setState(BottomSheetBehavior.STATE_EXPANDED) }
            behavior.peekHeight = ll_pic_details.height

        }

    }

    private fun getComment() {
        async(UI) {
            val result = Api.retrofitService.getComment(intent.getStringExtra("id")).await()
            if (result.code == 0) {
                Log.d("de",result.res.comment.toString())
                list.addAll(result.res.comment)
                emptyRecyclerView.setEmptyView(cv_empty_comment)
                emptyRecyclerView.adapter = CommentAdapter(list) { view, position ->

                }
            }
        }
    }

    fun setBackgroudAlpha(alpha: Float) {
        val lp = window.attributes
        lp.alpha = alpha
        window.attributes = lp
    }

    override fun bindLayout() = R.layout.activity_pic_details

}