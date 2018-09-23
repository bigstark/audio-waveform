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
        waveformView.spacing = 4f
        waveformView.amplitudesSize = 20
        waveformView.color = Color.parseColor("#DDDDDD")
        Thread {
            while(true) {
                val number = Math.abs(random.nextInt()) % 100
                Thread.sleep(64)
                runOnUiThread {
                    waveformView.putAmplitude(number)
                }
            }
        }.start()
    }
}
