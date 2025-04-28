package com.example.study3

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var seekBar2: SeekBar
    private lateinit var imageView: ImageView
//    private var timerService: TimerService? = null
    private var isBound = false
//
//    private val serviceConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//            val serviceBinder = binder as TimerService.TimerBinder
//            timerService = serviceBinder.getService()
//            isBound = true
//            timerService?.onTimeUpdate = { updateSeekBar(it) }
//            timerService?.startTimer()
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) {
//            isBound = false
//            timerService = null
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar2 = findViewById(R.id.seekBar2)
        seekBar2.max = 180

        imageView = findViewById(R.id.closebutton)
        imageView.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> { loadFragment(HomeFragment()); true }
                R.id.search -> { loadFragment(SearchFragment()); true }
                R.id.somelse -> { loadFragment(SomelseFragment()); true }
                R.id.stack -> { loadFragment(StackFragment()); true }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    override fun onStart() {
        super.onStart()
//        bindService(Intent(this, TimerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
//            unbindService(serviceConnection)
            isBound = false
        }
    }

    private fun updateSeekBar(time: Long) {
        seekBar2.progress = time.toInt()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
