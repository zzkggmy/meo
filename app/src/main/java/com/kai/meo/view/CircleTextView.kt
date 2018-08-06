package com.kai.meo.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.widget.TextView


@SuppressLint("ViewConstructor")
class CircleTextView(context: Context, var themeColor: String) : TextView(context) {
    private val mBgPaint = Paint()
    private var mContext: Context? = null

    init {
        mContext = context
        mBgPaint.isAntiAlias = true
        mBgPaint.color = Color.parseColor(themeColor)
    }

    var pfd = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        val max = Math.max(measuredWidth, measuredHeight)
        setMeasuredDimension(max, max)
    }


    override fun draw(canvas: Canvas) {
        canvas.drawFilter = pfd
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (Math.max(width, height) / 2).toFloat(), mBgPaint)
        super.draw(canvas)
    }
}