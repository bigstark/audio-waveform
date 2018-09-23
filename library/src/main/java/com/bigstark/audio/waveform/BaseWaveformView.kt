package com.bigstark.audio.waveform

import android.content.Context
import android.util.AttributeSet
import android.view.View

abstract class BaseWaveformView : View {

    companion object {
        const val DEFAULT_AMPLITUDES_SIZE = 30
        const val ENQUEUE_DELAYED_TIME = 100
    }
    private val DEFAULT_DIRECTION = WaveDirection.RIGHT_TO_LEFT

    var amplitudesSize = DEFAULT_AMPLITUDES_SIZE
        set(size) {
            field = size
            amplitudes = MutableList(size) { 0 }
            postInvalidate()
        }

    var direction = DEFAULT_DIRECTION
        set(direction) {
            if (field == direction) {
                return
            }
            field = direction
            amplitudes.reverse()
            postInvalidate()
        }

    var maxAmplitude = 100
        set(value) {
            field = value
            postInvalidate()
        }

    private var amplitudes = MutableList(DEFAULT_AMPLITUDES_SIZE) { 0 }
    private var lastPutTime = System.currentTimeMillis()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    protected fun getAmplitudes(): List<Int> {
        return amplitudes
    }

    open fun putAmplitude(amplitude: Int) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastPutTime < ENQUEUE_DELAYED_TIME) {
            return
        }

        lastPutTime = currentTimeMillis

        when (direction) {
            WaveDirection.LEFT_TO_RIGHT -> {
                amplitudes.add(0, amplitude)
                amplitudes.removeAt(amplitudesSize)
            }
            WaveDirection.RIGHT_TO_LEFT -> {
                amplitudes.add(amplitudesSize, amplitude)
                amplitudes.removeAt(0)
            }
        }

        postInvalidate()
    }

}
