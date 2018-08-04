package com.kai.meo.view

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.loading_view.view.*


class LoadingDialog(context: Context, themeResId: Int) : AlertDialog(context, themeResId) {

    class Builder(val context: Context) {
        fun create(): LoadingDialog {
            val dialog = LoadingDialog(context, R.style.LoadingDialog)
            val view = LayoutInflater.from(context).inflate(R.layout.loading_view, null, false)

            val objectAnimator = ObjectAnimator.ofFloat(view.iv_loading, "rotation", 360f)
            dialog.addContentView(view, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            objectAnimator.apply {
                setAutoCancel(false)
                setDuration(2000)
                start()
            }
            objectAnimator.repeatCount = Int.MAX_VALUE
            dialog.setContentView(view)
            dialog.setCanceledOnTouchOutside(true)
            return dialog
        }
    }
}