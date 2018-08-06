package com.kai.meo.activity

import android.support.v7.widget.LinearLayoutManager
import com.kai.meo.adapter.ThemeAdapter
import com.kai.meo.bean.ThemeBean
import com.kai.meo.utils.setThemeColor
import com.kai.meo.view.BaseActivity
import com.kai.meo.view.CircleTextView
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_theme.*

class ThemeActivity : BaseActivity() {

    private val list: ArrayList<ThemeBean> = ArrayList()
    val name = arrayListOf<String>("白色", "亮蓝", "rem蓝", "ty蓝", "绿色", "红色", "淡灰", "黑色", "知乎蓝", "紫红色", "紫色", "深紫色", "深蓝色", "青色", "淡绿色", "柠檬色", "黄色", "深黄色", "橙色", "网易红")
    val color = arrayListOf<String>("#ffffff", "#2196f3", "#91bef0", "#66ccff", "#4caf50", "#f44336", "#eeeeee", "#212121", "#1E8AE8", "#fff50057", "#ffaa00ff", "#ff6200ea", "#ff304ffe", "#ff00e5ff", "#ff00bfa5", "#ffaeea00", "#ffffff00", "#ffffab00", "#ffff6d00", "#ff0033")
    val isSelect = arrayListOf<Boolean>(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
    override fun bindLayout() = R.layout.activity_theme

    override fun initView() {
        initList()
        rv_theme.layoutManager = LinearLayoutManager(this)
        rv_theme.adapter = ThemeAdapter(list) { view, position ->

        }
    }

    private fun initList() {
        for (i in 0 until name.size) {
            val themeBean = ThemeBean(name[i], color[i])
            list.add(themeBean)
        }
    }
}