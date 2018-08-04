package com.kai.meo.utils


object ScreenUtil {

    val dm = Common.context.resources.displayMetrics
    fun getScreeHeight(): Int {

        return dm.heightPixels
    }

    fun getScreeWidth(): Int {
        return dm.widthPixels
    }

}