package com.kai.meo.utils

import android.widget.Toast

val zToast: Toast by lazy { Toast(Common.context) }
fun shortToast(message: String) {
    zToast.setText(message)
}