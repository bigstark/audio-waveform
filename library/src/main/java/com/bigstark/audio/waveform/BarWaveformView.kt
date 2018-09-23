package com.bigstark.audio.waveform

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log


class BarWaveformView : BaseWaveformView {

    private val COLOR_DEFAULT = Color.parseColor("#7382C8")
    private val ZERO_HEIGHT = 2f

    var color: Int = COLOR_DEFAULT
        set(value) {
            field = value
            initPaint()
            postInvalidate()
        }

    var spacing: Float = 5f
        set (value) {
            field = value
            postInvalidate()
        }

    private val paint: Paint = Paint()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    private fun initPaint() {
        paint.reset()

        paint.isAntiAlias = false
        paint.style = Paint.Style.FILL
        paint.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        val width = width - paddingStart - paddingEnd
        val height = height - paddingTop - paddingBottom

        if ((spacing * amplitudesSize - 1) > width) {
            throw IllegalStateException("Spacing is too long. Please reduce the spacing or amplitudeSize")
        }

        val amplitudes = getAmplitudes()
        val rectWidth = (width.toFloat() - spacing * (amplitudesSize - 1)) / amplitudesSize

        for (i in amplitudes.indices) {
            val rate = amplitudes[i] / maxAmplitude.toFloat()
            val rectHeight = if (height * rate == 0f) ZERO_HEIGHT else height * rate

            drawBar(canvas, i, rectWidth, rectHeight, amplitudes[i] == 0)
        }
    }

    private fun drawBar(canvas: Canvas, index: Int, rectWidth: Float, rectHeight: Float, drawLine: Boolean) {
        val bottom = (height - rectHeight) / 2
        val top = rectHeight + bottom
        val left = (index - 1) * (rectWidth + spacing)
        val right = left + rectWidth

        canvas.drawRect(left, top, right, bottom, paint)
        canvas.drawRoundRect(left, top, right, bottom, 5f, 5f, paint)
        if (drawLine) {
            val lineBottom = (height - ZERO_HEIGHT) / 2
            val lineTop = lineBottom + ZERO_HEIGHT
            canvas.drawRect(right, lineTop, right + spacing, lineBottom, paint)
        }
    }
}
