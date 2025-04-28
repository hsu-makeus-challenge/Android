package com.example.study3

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var timeTextView: TextView
    private lateinit var playPauseImage: ImageView
   // private var timerService: TimerService? = null
    private var isBound = false
    // 선언부 -> seekbar , button 등

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
          //  val serviceBinder = binder as TimerService.TimerBinder
            //timerService = serviceBinder.getService()
            isBound = true
           // timerService?.onTimeUpdate = { updateUI(it) }
        }
        //serviceconnection (object)

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
           // timerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        timeTextView = findViewById(R.id.timeTextView)
        seekBar = findViewById(R.id.seekBar)
        seekBar.max = 180

        playPauseImage = findViewById(R.id.imageView12)
//        playPauseImage.setOnClickListener {
//            if (timerService?.isRunning == true) {
//                timerService?.pauseTimer()
//                playPauseImage.setImageResource(R.drawable.btn_miniplay_mvplay)
//            } else {
//                timerService?.startTimer()
//                playPauseImage.setImageResource(R.drawable.btn_miniplay_mvpause)
//            }
//        }

        findViewById<ImageView>(R.id.back).setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
       // bindService(Intent(this, TimerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    private fun updateUI(time: Long) {
        timeTextView.text = formatTime(time)
        seekBar.progress = time.toInt()
    }

    private fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
