package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class UpgradeMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btn_start: Button // 늦은 초기화
    private lateinit var btn_restart: Button
    private lateinit var tv_minute: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_millisecond: TextView

    var isRunning = false
    var timer: Timer? = null
    var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upgrade)

        btn_start = findViewById(R.id.btn_start)
        btn_restart = findViewById(R.id.btn_restart)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_restart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> {
                if(isRunning) {
                    pause()
                } else {
                    start()
                }
            }
            R.id.btn_restart -> {
                restart()
            }
        }
    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause_eng)
        isRunning = true

        timer = timer(period = 10) {
            time++

            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {
                if (isRunning) {
                    tv_minute.text = if (minute < 10) "0${minute}" else "${minute}"
                    tv_second.text = if (second < 10) ":0${second}" else ":${second}"
                    tv_millisecond.text =
                        if (milli_second < 10) ".0${milli_second}" else ".${milli_second}"
                }
            }
        }
    }

    private fun restart() {
        timer?.cancel()
        time = 0

        btn_start.text = getString(R.string.btn_start_eng)
        isRunning = false

        tv_minute.text = "00"
        tv_second.text = ":00"
        tv_millisecond.text = ",00"
    }

    private fun pause() {
        btn_start.text = getString(R.string.btn_start_eng)

        isRunning = false
        timer?.cancel()
    }
}
