package com.kai.meowallpaper.utils

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kai.meowallpaper.R


var options = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_fail_img)
        .error(R.drawable.ic_fail_img)
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
