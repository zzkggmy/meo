package com.kai.meo.view

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.kai.meo.bean.NetworkChangeEvent
import com.kai.meo.utils.NetUtils
import com.kai.meo.utils.StatusBarUtil
import com.kai.meo.utils.getThemeColor
import com.kai.meowallpaper.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseActivity : AppCompatActivity() {
    private var coordinatorLayout: CoordinatorLayout? = null
    private var mCheckNetWork = true
    private lateinit var loadingProgress: LoadingProgress
    private lateinit var snackbar: Snackbar
    var mTipView: View? = null
    var mWindowManager: WindowManager? = null
    var mLayoutParams: WindowManager.LayoutParams? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayout())
        coordinatorLayout = CoordinatorLayout(this)
        snackbar = Snackbar.make(coordinatorLayout!!, "已删除一个会话", Snackbar.LENGTH_SHORT)
                .setAction(R.string.know) { Toast.makeText(this, "撤销了删除", Toast.LENGTH_SHORT).show() }
        StatusBarUtil.setColorNoTranslucent(this, Color.parseColor(getThemeColor))
        loadingProgress = LoadingProgress(this)
        initView()
        EventBus.getDefault().register(this)
    }

    abstract fun bindLayout(): Int

    abstract fun initView()

    fun showLoading() {
        loadingProgress.showLoding()
    }

    fun dismissLoading() {
        loadingProgress.dismissLoding()
    }

    override fun onResume() {
        super.onResume()
        hasNetWork(NetUtils.isConnected())
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetWork(event.isConnected)
    }

    private fun hasNetWork(has: Boolean) {
        if (isCheckNetWork()) {
            if (has) {
                showSnackBar("")
            } else {
                dismissSnackbar()
            }
        }
    }

    fun setCheckNetWork(checkNetWork: Boolean) {
        mCheckNetWork = checkNetWork
    }

    private fun isCheckNetWork(): Boolean {
        return mCheckNetWork
    }

    fun showSnackBar(item: String) {
        snackbar.show()
    }

    fun dismissSnackbar() {
        snackbar.dismiss()
    }

}