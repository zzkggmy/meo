package com.kai.meo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.kai.meo.utils.SavePicManager
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.utils.options
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_pic_details.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class PicDetailsActivity : AppCompatActivity() {
    private var imgUrl = ""
    private val list: ArrayList<com.kai.meo.bean.CommentBean.Res.Comment> = ArrayList()
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_details)
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
        StatusBarUtil.setTransparentForImageView(this, iv_pic_details)
        imgUrl = "http://img5.adesk.com/" + intent.getStringExtra("id")
        tv_download_pic.setOnClickListener { SavePicManager.savePhoto(imgUrl) }

        val behavior = BottomSheetBehavior.from(nsv_comment)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {

            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {

            }
        })
        tv_comment.setOnClickListener { behavior.setState(BottomSheetBehavior.STATE_EXPANDED) }


        Glide.with(com.kai.meo.utils.Common.context)
                .asBitmap()
                .load(imgUrl)
                .apply(options)
                .into(iv_pic_details)
        iv_pic_details.setOnLongClickListener {

            true
        }
        getComment()
        rv_comment_pic_details.layoutManager = LinearLayoutManager(this)
    }

    private fun getComment() {

        async(UI) {
            val result = com.kai.meo.http.Api.retrofitService.getComment(intent.getStringExtra("id")).await()

            if (result.code == 0) {
                Log.d("tag1", intent.getStringExtra("id") + result.res.toString())
                list.addAll(result.res.comment)
                if (list.size == 0) {
                    ll_empty_comment.visibility = View.VISIBLE
                    rv_comment_pic_details.visibility = View.GONE
                } else {
                    ll_empty_comment.visibility = View.GONE
                    rv_comment_pic_details.visibility = View.VISIBLE
                }
                rv_comment_pic_details.adapter = com.kai.meo.adapter.CommentAdapter(list) { view, position ->

                }
            }
        }
    }
}