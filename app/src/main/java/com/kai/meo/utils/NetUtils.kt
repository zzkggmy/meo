package com.kai.meo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetUtils {
    fun isConnected(): Boolean {
        val connectivity = Common.context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = connectivity.activeNetworkInfo
        if (null != info && info.isConnected) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
}