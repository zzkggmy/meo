package com.kai.meo.utils

import com.kai.meowallpaper.R

fun setToken(token: String) {
    spSetString("token", token)
}

val getToken = getString("token","")

fun setThemeColor(color: Int) {
    spSetInt("color", color)
}

val getThemeColor = getInt("color", R.color.zhihu_primary)