package com.bigstark.audio.waveform.sample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bigstark.audio.waveform.WaveDirection
import kotlinx.android.synthetic.main.activity_sample.*
import java.util.*

class SampleActivity : AppCompatActivity() {

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        waveformView.apply {
            spacing = 4f
            amplitudesSize = 20
            color = Color.parseColor("#DDDDDD")
            maxAmplitude = 100
            direction = WaveDirection.RIGHT_TO_LEFT
        }
        Thread {
            while(true) {
                val number = Math.abs(random.nextInt()) % waveformView.maxAmplitude
                Thread.sleep(100)
                runOnUiThread {
                    waveformView.putAmplitude(number)
                }
            }
        }.start()
    }
}
