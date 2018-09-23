package com.bigstark.audio.waveform

class DisplayUtils {
    companion object {
        fun getSpringEasing(fraction: Double): Double {
            var fraction = fraction
            fraction /= 1.5
            val p = 0.8
            return Math.pow(2.0, -10 * fraction) * Math.sin((fraction - p / 4) * (2 * Math.PI) / p) + 1
        }
    }
}