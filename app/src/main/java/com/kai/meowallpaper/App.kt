package com.kai.meowallpaper

import android.app.Application
import com.kai.meowallpaper.utils.Common

class App : Application() {
    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Common.with(this)
    }
}