package com.kai.meo.view

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kai.meo.utils.findColor
import com.kai.meo.utils.getThemeColor
import com.kai.meowallpaper.R
import kotlinx.android.synthetic.main.title_bar.view.*

class TitleBar(context: Context) : FrameLayout(context) {

    init {
        setBackgroundColor(findColor(getThemeColor))
        addView(View.inflate(context, R.layout.title_bar, null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    fun setLeftText(text: String) {
        tv_title_left.visibility = View.VISIBLE
        tv_title_left.text = text
    }

    fun setLeftTextColor(color: Int) {
        if (color == R.color.white) {
            tv_title_left.setTextColor(findColor(R.color.text_black))
        } else {
            tv_title_left.setTextColor(color)
        }
    }

    fun setLeftImage(drawble: Int) {
        iv_title_left.visibility = View.VISIBLE
        iv_title_left.setImageDrawable(ContextCompat.getDrawable(context, drawble))
    }

    fun setCenterText(text: String) {
        tv_title_center.visibility = View.VISIBLE
        tv_title_center.text = text
    }

    fun setCenterTextColor(color: Int) {
        if (color == R.color.white) {
            tv_title_center.setTextColor(findColor(R.color.text_black))
        } else {
            tv_title_center.setTextColor(color)
        }
    }

    fun setCenterImage(drawble: Int) {
        iv_title_center.visibility = View.VISIBLE
        iv_title_center.setImageDrawable(ContextCompat.getDrawable(context, drawble))
    }

    fun setRightText(text: String) {
        tv_title_right.visibility = View.VISIBLE
        tv_title_right.text = text
    }

    fun setRightTextColor(color: Int) {
        if (color == R.color.white) {
            tv_title_right.setTextColor(findColor(R.color.text_black))
        } else {
            tv_title_right.setTextColor(color)
        }
    }

    fun setRightImage(drawble: Int) {
        iv_title_right.visibility = View.VISIBLE
        iv_title_right.setImageDrawable(ContextCompat.getDrawable(context, drawble))
    }

    fun setTitleLeftClick(func: (() -> Unit)?) = ll_left_title.setOnClickListener { func?.invoke() }
    fun setTitleCenterClick(func: (() -> Unit)?) = ll_left_title.setOnClickListener { func?.invoke() }
    fun setTitleRightClick(func: (() -> Unit)?) = ll_left_title.setOnClickListener { func?.invoke() }

}