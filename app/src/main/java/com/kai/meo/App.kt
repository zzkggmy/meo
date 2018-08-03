package com.kai.meo

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        com.kai.meo.utils.Common.with(this)
    }
}