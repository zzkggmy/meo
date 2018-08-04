package com.kai.meo.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.kai.meo.bean.CommentBean
import com.kai.meo.utils.*
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_pic_details.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import android.view.LayoutInflater
import com.kai.meo.adapter.CommentAdapter
import com.kai.meo.view.EmptyRecyclerView


class PicDetailsActivity : AppCompatActivity() {
    private var imgUrl = ""
    private var emptyRecyclerView: EmptyRecyclerView? = null
    private val list: ArrayList<CommentBean.Res.Comment> = ArrayList()
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_details)
        StatusBarUtil.setTranslucent(this, 0)
        emptyRecyclerView = EmptyRecyclerView(this)
//        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//            window.navigationBarColor = Color.TRANSPARENT
//        }nsv_comment
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0)
        emptyRecyclerView = rv_comment_pic_details as EmptyRecyclerView
        imgUrl = "http://img5.adesk.com/" + intent.getStringExtra("id")

        tv_download_pic.setOnClickListener { SavePicManager.savePhoto(imgUrl) }
        val behavior = BottomSheetBehavior.from(nsv_comment)
        Log.d("cd", rv_comment_pic_details.height.toString())
        behavior.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {

            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
            }
        })
        tv_comment.setOnClickListener { behavior.setState(BottomSheetBehavior.STATE_EXPANDED) }


        getComment()
        val simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                cl_pic_details.background = resource
            }
        }
        Glide.with(com.kai.meo.utils.Common.context)
                .load(imgUrl)
                .apply(picDetailsOptions)
                .into(simpleTarget)
        Log.d("sa", "" + ScreenUtil.getScreeWidth() + ScreenUtil.getScreeHeight())
        emptyRecyclerView!!.layoutManager = LinearLayoutManager(this)

    }

    private fun getComment() {
        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getComment(intent.getStringExtra("id")).await()
            if (result.code == 0) {
                Log.d("tag1", intent.getStringExtra("id") + result.res.toString())
                list.addAll(result.res.comment)
//                if (list.size >= 1) {
//                    rv_comment_pic_details.visibility = View.VISIBLE
//                    cv_empty_comment.visibility = View.GONE
//                } else {
//                    rv_comment_pic_details.visibility = View.GONE
//                    cv_empty_comment.visibility = View.VISIBLE
//                }
                emptyRecyclerView!!.setEmptyView(cv_empty_comment)
                emptyRecyclerView!!.adapter = CommentAdapter(list) { view, position ->

                }
            }
        }
    }

    fun setBackgroudAlpha(alpha: Float) {
        val lp = window.attributes
        lp.alpha = alpha
        window.attributes = lp
    }

}