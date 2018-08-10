package com.kai.meo.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

fun findColor(@ColorRes resId: kotlin.Int): kotlin.Int {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
        return ContextCompat.getColor(Common.context,resId)
    } else {
        return Common.context.getColor(resId)
    }
}

fun getDrawble(drawble: kotlin.Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= 23) ContextCompat.getDrawable(Common.context, drawble) else getDrawble(drawble)
}