package com.kai.meo.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.os.Message
import android.support.annotation.NonNull
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kai.meowallpaper.R


class BannerLayout : RelativeLayout {

    private var mViewPager: ViewPager? = null // 轮播容器

    // 指示器（圆点）容器
    private var indicatorContainer: LinearLayout? = null

    private var unSelectedDrawable: Drawable? = null
    private var selectedDrawable: Drawable? = null


    private val WHAT_AUTO_PLAY = 1000

    private var isAutoPlay = true // 自动轮播

    private var itemCount: Int = 0

    private var selectedIndicatorColor = -0x10000
    private var unSelectedIndicatorColor = -0x77777778
    private var titleBGColor = 0X55000000
    private var titleColor = -0x1

    private var indicatorShape = Shape.oval
    private var selectedIndicatorHeight = 20
    private var selectedIndecatorWidth = 20
    private var unSelectedIndicatorHeight = 20
    private var unSelectedIndecatorWidth = 20

    private var autoPlayDuration = 4000
    private var scrollDuration = 900
    //  指示器中点和点之间的距离
    private var indicatorSpace = 15
    //  指示器整体的设置
    private var indicatorMargin = 20

    private var defaultImage: Int = 0

    private var mOnBannerItemClickListener: OnBannerItemClickListener? = null

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what === WHAT_AUTO_PLAY) {
                if (mViewPager != null) {
                    mViewPager!!.setCurrentItem(mViewPager!!.currentItem + 1, true)
                    sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration.toLong())
                }
            }
        }
    }

    private enum class Shape {
        // 矩形 或 圆形的指示器
        rect,
        oval
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {

        val array = context.obtainStyledAttributes(attrs, R.styleable.BannerLayoutStyle, defStyleAttr, 0)
        selectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_selectedIndicatorColor, selectedIndicatorColor)
        unSelectedIndicatorColor = array.getColor(R.styleable.BannerLayoutStyle_unSelectedIndicatorColor, unSelectedIndicatorColor)

        titleBGColor = array.getColor(R.styleable.BannerLayoutStyle_titleBGColor, titleBGColor)
        titleColor = array.getColor(R.styleable.BannerLayoutStyle_titleColor, titleColor)

        val shape = array.getInt(R.styleable.BannerLayoutStyle_indicatorShape, Shape.oval.ordinal)

        for (shape1 in Shape.values()) {
            if (shape1.ordinal == shape) {
                indicatorShape = shape1
                break
            }
        }

        selectedIndecatorWidth = array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorWidth, selectedIndecatorWidth.toFloat()).toInt()
        selectedIndicatorHeight = array.getDimension(R.styleable.BannerLayoutStyle_selectedIndicatorHeight, selectedIndicatorHeight.toFloat()).toInt()

        unSelectedIndecatorWidth = array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorWidth, unSelectedIndecatorWidth.toFloat()).toInt()
        unSelectedIndicatorHeight = array.getDimension(R.styleable.BannerLayoutStyle_unSelectedIndicatorHeight, unSelectedIndicatorHeight.toFloat()).toInt()


        indicatorSpace = array.getDimension(R.styleable.BannerLayoutStyle_indicatorSpace, indicatorSpace.toFloat()).toInt()
        indicatorMargin = array.getDimension(R.styleable.BannerLayoutStyle_indicatorMargin, indicatorMargin.toFloat()).toInt()

        autoPlayDuration = array.getInt(R.styleable.BannerLayoutStyle_autoPlayDuration, autoPlayDuration)
        scrollDuration = array.getInt(R.styleable.BannerLayoutStyle_scrollDuration, scrollDuration)

        isAutoPlay = array.getBoolean(R.styleable.BannerLayoutStyle_isAutoPlay, isAutoPlay)

        defaultImage = array.getResourceId(R.styleable.BannerLayoutStyle_defaultImage, defaultImage)

        array.recycle()

        val unSelectedLayerDrawable: LayerDrawable
        val selectedLayerDrawable: LayerDrawable

        val unSelectedGradientDrawable = GradientDrawable()
        val selectedGradientDtawbale = GradientDrawable()

        when (indicatorShape) {
            BannerLayout.Shape.rect -> {
                unSelectedGradientDrawable.shape = GradientDrawable.RECTANGLE
                selectedGradientDtawbale.shape = GradientDrawable.RECTANGLE
            }
            BannerLayout.Shape.oval -> {
                unSelectedGradientDrawable.shape = GradientDrawable.OVAL
                selectedGradientDtawbale.shape = GradientDrawable.OVAL
            }
        }

        unSelectedGradientDrawable.setColor(unSelectedIndicatorColor)
        unSelectedGradientDrawable.setSize(unSelectedIndecatorWidth, unSelectedIndicatorHeight)
        unSelectedLayerDrawable = LayerDrawable(arrayOf<Drawable>(unSelectedGradientDrawable))
        unSelectedDrawable = unSelectedLayerDrawable

        selectedGradientDtawbale.setColor(selectedIndicatorColor)
        selectedGradientDtawbale.setSize(selectedIndecatorWidth, selectedIndicatorHeight)
        selectedLayerDrawable = LayerDrawable(arrayOf<Drawable>(selectedGradientDtawbale))

        selectedDrawable = selectedLayerDrawable

    }

    /**
     * 添加本地图片
     *
     * @param viewRes 图片id集合
     * @param titles  标题集合，可空
     */
    fun setViewRes(viewRes: List<Int>, titles: List<String>) {
        val views: ArrayList<View> = ArrayList()
        itemCount = viewRes.size
        if (titles.size != titles.size) {
            throw IllegalStateException("views.size() != titles.size()")
        }
        // 把数量拼凑到三个以上
        if (itemCount < 1) {
            throw IllegalStateException("item count not equal zero")
        } else if (itemCount < 2) {
            views.add(getFrameLayoutView(viewRes[0], titles[0], 0))
            views.add(getFrameLayoutView(viewRes[0], titles[0], 0))
            views.add(getFrameLayoutView(viewRes[0], titles[0], 0))

        } else if (itemCount < 3) {
            views.add(getFrameLayoutView(viewRes[0], titles[0], 0))
            views.add(getFrameLayoutView(viewRes[1], titles[1], 1))
            views.add(getFrameLayoutView(viewRes[0], titles[0], 0))
            views.add(getFrameLayoutView(viewRes[1], titles[1], 1))
        } else {
            for (i in 0 until viewRes.size) {
                views.add(getFrameLayoutView(viewRes[i], titles[i], i))
            }
        }
        setViews(views)
    }

    //添加网络图片路径
    fun setViewUrls(context: Context, urls: List<String>, titles: List<String>?) {
        val views: ArrayList<View> = ArrayList()
        itemCount = urls.size
//        if (titles != null && titles.size != itemCount) {
//             throw IllegalStateException("views.size() != titles.size()")
//        }

        //主要是解决当item为小于3个的时候滑动有问题，这里将其拼凑成3个以上
//        if (itemCount < 1) {//当item个数0
//            throw IllegalStateException("item count not equal zero")
//        } else if (itemCount < 2) { //当item个数为1
//            views.add(getFrameLayoutView(context, urls[0], if (titles != null) titles[0] else null, 0))
//            views.add(getFrameLayoutView(context, urls[0], if (titles != null) titles[0] else null, 0))
//            views.add(getFrameLayoutView(context, urls[0], if (titles != null) titles[0] else null, 0))
//        } else if (itemCount < 3) {//当item个数为2
//            views.add(getFrameLayoutView(context, urls[0], if (titles != null) titles[0] else null, 0))
//            views.add(getFrameLayoutView(context, urls[1], if (titles != null) titles[1] else null, 1))
//            views.add(getFrameLayoutView(context, urls[0], if (titles != null) titles[0] else null, 0))
//            views.add(getFrameLayoutView(context, urls[1], if (titles != null) titles[1] else null, 1))
//
//        } else {
            for (i in urls.indices) {
                views.add(getFrameLayoutView(context, urls[i], if (titles != null) titles[i] else null, i))
            }
//        }
        setViews(views)
    }

    private fun setViews(views: List<View>) {

        mViewPager = ViewPager(context)

        addView(mViewPager)
        setSliderTransformDuration(scrollDuration)
        //初始化indicatorContainer
        indicatorContainer = LinearLayout(context)
        indicatorContainer!!.gravity = Gravity.CENTER_VERTICAL
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        // 设置 指示点的位置
        params.addRule(RelativeLayout.CENTER_HORIZONTAL)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

        //设置margin
        params.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin)
        //添加指示器容器布局到SliderLayout
        addView(indicatorContainer, params)

        //初始化指示器，并添加到指示器容器布局
        for (i in 0 until itemCount) {
            val indicator = ImageView(context)
            indicator.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            indicator.setPadding(indicatorSpace, indicatorSpace, indicatorSpace, indicatorSpace)
            indicator.setImageDrawable(unSelectedDrawable)
            indicatorContainer!!.addView(indicator)
        }
        val pagerAdapter = LoopPagerAdapter(views)
        mViewPager!!.adapter = pagerAdapter
        //设置当前item到Integer.MAX_VALUE中间的一个值，看起来像无论是往前滑还是往后滑都是ok的
        //如果不设置，用户往左边滑动的时候已经划不动了
        val targetItemPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount
        mViewPager!!.currentItem = targetItemPosition
        switchIndicator(targetItemPosition % itemCount)
        mViewPager!!.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                switchIndicator(position % itemCount)
            }
        })
        startAutoPlay()

    }

    /**
     * 开始自动轮播
     */
    fun startAutoPlay() {
        stopAutoPlay() // 避免重复消息
        if (isAutoPlay) {
            mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration.toLong())
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == View.VISIBLE) {
            startAutoPlay()
        } else {
            stopAutoPlay()
        }
    }


    /**
     * 停止自动轮播
     */
    fun stopAutoPlay() {
        if (isAutoPlay) {
            mHandler.removeMessages(WHAT_AUTO_PLAY)
        }
    }

    private fun setSliderTransformDuration(scrollDuration: Int) {
        try {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val fixedSpeedScroller = FixedSpeedScroller(mViewPager!!.context)
            mScroller.set(mViewPager, fixedSpeedScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> stopAutoPlay()
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> startAutoPlay()
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 切换指示器状态
     *
     * @param currentPosition 当前位置
     */
    private fun switchIndicator(currentPosition: Int) {
        for (i in 0 until indicatorContainer!!.childCount) {
            (indicatorContainer!!.getChildAt(i) as ImageView).setImageDrawable(if (i == currentPosition) selectedDrawable else unSelectedDrawable)
        }
    }


    fun setOnBannerItemClickListener(onBannerItemClickListener: OnBannerItemClickListener) {
        this.mOnBannerItemClickListener = onBannerItemClickListener
    }

    @NonNull
    private fun getFrameLayoutView(context: Context, url: String, title: String?, position: Int): FrameLayout {
        val frameLayout = FrameLayout(getContext())
        val layoutParams = FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        val imageView = ImageView(getContext())
        frameLayout.addView(imageView)
        frameLayout.setOnClickListener {
            if (mOnBannerItemClickListener != null) {
                mOnBannerItemClickListener!!.onItemClick(position)
            }
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        if (defaultImage != 0) {
            Glide.with(getContext())
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
                    .into(imageView)
        } else {
            Glide.with(getContext())
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
                    .into(imageView)
        }
        if (!TextUtils.isEmpty(title)) {
            val textView = TextView(getContext())
            textView.text = title
            textView.setTextColor(titleColor)
            textView.setPadding(titlePadding, titlePadding, titlePadding, titlePadding)
            textView.setBackgroundColor(titleBGColor)
            textView.paint.isFakeBoldText = true
            layoutParams.gravity = Gravity.BOTTOM
            frameLayout.addView(textView, layoutParams)
        }
        return frameLayout
    }

    @NonNull
    private fun getFrameLayoutView(res: Int?, title: String?, position: Int): FrameLayout {
        val frameLayout = FrameLayout(context)
        val layoutParams = FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        val imageView = ImageView(context)
        frameLayout.addView(imageView)
        frameLayout.setOnClickListener {
            if (mOnBannerItemClickListener != null) {
                mOnBannerItemClickListener!!.onItemClick(position)
            }
        }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(context)
                .asBitmap()
                .load(res)
                .apply {
                    RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_fail_img)
                            .error(R.drawable.ic_fail_img)
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                .into(imageView)
        if (!TextUtils.isEmpty(title)) {
            val textView = TextView(context)
            textView.text = title
            textView.setTextColor(titleColor)
            textView.setPadding(titlePadding, titlePadding, titlePadding, titlePadding)
            textView.setBackgroundColor(titleBGColor)
            textView.paint.isFakeBoldText = true
            layoutParams.gravity = Gravity.BOTTOM
            frameLayout.addView(textView, layoutParams)
        }
        return frameLayout
    }

    interface OnBannerItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class LoopPagerAdapter(private val views: List<View>) : PagerAdapter() {

        override fun getCount(): Int {
            //Integer.MAX_VALUE = 2147483647
            return Integer.MAX_VALUE
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any{
            var view:View? = null
            if (views.isNotEmpty()) {
                //position % view.size()是指虚拟的position会在[0，view.size()）之间循环
                view = views[position % views.size]
                if (container == view.getParent()) {
                    container.removeView(view)
                }
                container.addView(view)
                return view
            }
            return view!!
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {}
    }

    inner class FixedSpeedScroller(context: Context) : Scroller(context) {

        private var mDuration = 1000

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        init {
            mDuration = duration
        }
    }

    companion object {

        private const val titlePadding = 20
    }

}