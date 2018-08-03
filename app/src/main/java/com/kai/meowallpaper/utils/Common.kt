package com.kai.meowallpaper.utils

import android.app.Application

object Common {
    lateinit var context: Application
    fun with(app: Application) {
        this.context = app
    }
}