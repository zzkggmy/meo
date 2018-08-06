package com.kai.meo.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kai.meo.bean.NetworkChangeEvent
import com.kai.meo.utils.NetUtils
import org.greenrobot.eventbus.EventBus


class NetworkConnectChangedReceiver : BroadcastReceiver() {
    private val TAG = "INTENRNRT_STATUS"
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(p0: Context?, p1: Intent?) {
        val isConnected = NetUtils.isConnected()
EventBus.getDefault().post(NetworkChangeEvent(isConnected))
    }
}