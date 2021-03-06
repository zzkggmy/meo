package com.kai.meo.utils

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kai.meowallpaper.R

var picDetailsOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_fail_img)
        .error(R.drawable.ic_fail_img)
        .priority(Priority.HIGH)
        .override(ScreenUtil.getScreeWidth(),ScreenUtil.getScreeHeight())
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)