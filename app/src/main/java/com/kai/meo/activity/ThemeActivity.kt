package com.kai.meo.activity

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import com.kai.meo.adapter.ThemeAdapter
import com.kai.meo.base.BaseActivity
import com.kai.meo.bean.ThemeBean
import com.kai.meo.utils.findColor
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.activity_theme.*

class ThemeActivity : BaseActivity() {

    private val list: ArrayList<ThemeBean> = ArrayList()
    val name = arrayListOf("白色", "亮蓝", "rem蓝", "ty蓝", "绿色", "红色", "淡灰", "黑色", "知乎蓝", "紫红色", "紫色", "深紫色", "深蓝色", "青色", "淡绿色", "柠檬色", "黄色", "深黄色", "橙色", "网易红", "purple_a200", "purple_a400")
    val color = arrayListOf(R.color.white, R.color.lightBlue, R.color.remBlue, R.color.tianyiBlue, R.color.green, R.color.red, R.color.grey_300, R.color.text_black, R.color.zhihu_primary, R.color.pink, R.color.purple, R.color.deep_purplr, R.color.indigo, R.color.cyan, R.color.teal, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.netease, R.color.purple_a200, R.color.purple_a400)
    val colors = arrayListOf("#ffffff", "#2196f3", "#91bef0", "#66ccff", "#4caf50", "#f44336", "#eeeeee", "#212121", "#1E8AE8", "#fff50057", "#ffaa00ff", "#ff6200ea", "#ff304ffe", "#ff00e5ff", "#ff00bfa5", "#ffaeea00", "#ffffff00", "#ffffab00", "#ffff6d00", "#ff0033")
    override fun bindLayout() = R.layout.activity_theme

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        initList()
        setLeftImage(R.drawable.ic_back_white)
        setCenterText(getString(R.string.theme))
        setCenterTextColor(findColor(R.color.white))
        setRightText("保存")
        setRightTextColor(findColor(R.color.white))
        setTitleRightClick { finish() }
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