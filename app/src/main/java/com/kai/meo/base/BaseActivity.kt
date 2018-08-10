package com.kai.meo.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.utils.findColor
import com.kai.meo.utils.getThemeColor
import com.kai.meo.view.LoadingProgress
import com.kai.meo.view.TitleBar
import com.kai.meowallpaper.R


abstract class BaseActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var titleBar: TitleBar
    private lateinit var loadingProgress: LoadingProgress
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingProgress = LoadingProgress(this)
        linearLayout = LinearLayout(this)
        titleBar = TitleBar(this)
        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        titleBar.fitsSystemWindows = true
        linearLayout.addView(titleBar, params)
        setContentView(bindLayout())
        titleBar.elevation = 10f
        StatusBarUtil.setColorNoTranslucent(titleBar,this, findColor(getThemeColor))
        setTitleBarBackgroundColor(findColor(getThemeColor))
        initView()
        setTitleBarLeftClick { finish() }
        setTitleCenterClick { finish() }
        setTitleRightClick { finish() }
    }


    abstract fun bindLayout(): Int


    abstract fun initView()

    protected open fun useTitleBar(): Boolean = true

    override fun setContentView(view: View?) {
        if (useTitleBar()) {
            linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            super.setContentView(linearLayout)
        } else {
            super.setContentView(view)
        }
    }

    override fun setContentView(layoutResID: Int) {
        if (useTitleBar()) {
            val view = layoutInflater.inflate(layoutResID, linearLayout, false)
            linearLayout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            super.setContentView(linearLayout)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        if (useTitleBar()) {
            linearLayout.addView(view, params)
            super.setContentView(linearLayout)
        } else {
            super.setContentView(view, params)
        }
    }

    fun setTitleBarBackgroundColor(themeColor: Int) = titleBar.setBackgroundColor(themeColor)

    fun setLeftText(text: String) = titleBar.setLeftText(text)
    fun setLeftImage(image: Int) = titleBar.setLeftImage(image)
    fun setLeftTextColor(color: Int) = titleBar.setLeftTextColor(color)

    fun setCenterText(text: String) = titleBar.setCenterText(text)
    fun setCenterImage(image: Int) = titleBar.setCenterImage(image)
    fun setCenterTextColor(color: Int) = titleBar.setCenterTextColor(color)

    fun setRightText(text: String) = titleBar.setRightText(text)
    fun setRightImage(image: Int) = titleBar.setRightImage(image)
    fun setRightTextColor(color: Int) = titleBar.setRightTextColor(color)
    protected fun setTitleBarLeftClick(func: () -> Unit) = titleBar.setTitleLeftClick(func)
    protected fun setTitleCenterClick(func: () -> Unit) = titleBar.setTitleCenterClick (func)
    protected fun setTitleRightClick(func: () -> Unit) = titleBar.setTitleRightClick (func)

    fun showLoading() {
        loadingProgress.showLoding()
    }

    fun dismissLoading() {
        loadingProgress.dismissLoding()
    }

    protected fun userTitleBar() = true




    private fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setImmersiveStatusBar(view: View) {
        setTranslucentStatus()
        setDark()
        checkFont()
        view.setBackgroundResource(R.color.zhihu_primary)
        if (Build.VERSION.SDK_INT > 22) {
            val window: Window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            view.setPadding(0, getStatusBarHeight(), 0, 0)
        }
    }

    private fun setDark() {

        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    }

    private fun setTranslucentStatus() {
        val window: Window = this.window
        if (Build.VERSION.SDK_INT >= 23) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 22) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    fun checkFont() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getThemeColor == findColor(R.color.white)) {
                this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }


}