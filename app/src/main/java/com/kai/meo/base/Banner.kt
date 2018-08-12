package com.kai.meo.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Message
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kai.meowallpaper.R


class Banner(context: Context, attr: AttributeSet,defStyleAttr: Int) : RelativeLayout(context, attr,defStyleAttr) {
    private var selectedIndicatorHeight = 20
    private var selectedIndecatorWidth = 20
    private var unSelectedIndicatorHeight = 20
    private var unSelectedIndecatorWidth = 20
    private var selectedIndicatorColor = -0x10000
    private var unSelectedIndicatorColor = -0x77777778
    private var titleBGColor = 0X55000000
    private var titleColor = -0x1
    private val dotsList: ArrayList<TextView> = ArrayList()
    private var vp: ViewPager = ViewPager(context)
    private var linearLayout: LinearLayout = LinearLayout(context)
    private lateinit var dots: TextView


    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.BannerLayoutStyle, defStyleAttr, 0)
        selectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_selectedIndicatorColor, selectedIndicatorColor)
        unSelectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_unSelectedIndicatorColor, unSelectedIndicatorColor)

        titleBGColor = array.getColor(R.styleable.BannerLayoutStyle_titleBGColor, titleBGColor)
        titleColor = array.getColor(R.styleable.BannerLayoutStyle_titleColor, titleColor)

        selectedIndecatorWidth = array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorWidth, selectedIndecatorWidth.toFloat()).toInt()
        selectedIndicatorHeight = array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorHeight, selectedIndicatorHeight.toFloat()).toInt()

        unSelectedIndecatorWidth = array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorWidth, unSelectedIndecatorWidth.toFloat()).toInt()
        unSelectedIndicatorHeight = array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorHeight, unSelectedIndicatorHeight.toFloat()).toInt()
        array.recycle()
        val unSelectedGradientDrawable = GradientDrawable()
        val selectedGradientDtawbale = GradientDrawable()

        unSelectedGradientDrawable.setColor(unSelectedIndicatorColor)
        unSelectedGradientDrawable.setSize(unSelectedIndecatorWidth, unSelectedIndicatorHeight)
        selectedGradientDtawbale.setColor(selectedIndicatorColor)
        selectedGradientDtawbale.setSize(selectedIndecatorWidth, selectedIndicatorHeight)

        val ivParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {

            }
        }
    }

    private fun addDots(list: ArrayList<Any>) {
        val linearLayoutParams = LinearLayout.LayoutParams(20, 20)
        linearLayoutParams.setMargins(0, 0, 40, 0)
        dots.layoutParams = linearLayoutParams
        for (i in 0 until list.size) {
            dots = TextView(context)
            linearLayout.addView(dots, layoutParams)
            dotsList.add(dots)
        }
    }

    fun setView(list: ArrayList<Any>, urls: ArrayList<String>) {
        addDots(list)
        val views: ArrayList<View> = ArrayList()
        for (i in 0 until list.size) {
            views.add(frameLayout(urls[i]))
        }
        val bannerAdapter = BannerAdapter(views)
        addView(vp)
        vp.adapter = bannerAdapter
    }

    private fun frameLayout(url: String): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 0, 0, 0)
        frameLayout.layoutParams = layoutParams
        val iv = ImageView(context)
        val ivParams = ViewGroup.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        iv.layoutParams = ivParams
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        frameLayout.addView(iv)
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply {
                    RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_fail_img)
                            .error(R.drawable.ic_fail_img)
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                .into(iv)
        return frameLayout
    }


    inner class BannerAdapter(val views: List<View>) : PagerAdapter() {
        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view == any
        }

        override fun getCount() = views.size
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (views.isNotEmpty()) {
                val view = views[position % views.size]
                if (container == view.parent) {
                    container.removeView(view)
                }
                container.addView(view)
            }
            return super.instantiateItem(container, position)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }
    }
}