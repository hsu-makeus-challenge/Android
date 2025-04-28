//package com.example.study3
//
//import android.app.Service
//import android.content.Intent
//import android.os.Binder
//import android.os.Handler
//import android.os.IBinder
//import android.os.Looper
//import kotlinx.coroutines.*
//
//class TimerService : Service() {
//
//    private val binder = TimerBinder()
//    private var time = 0L
//    var isRunning = false
//    private var job: Job? = null
//    private val handler = Handler(Looper.getMainLooper())
//    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job()) // CoroutineScope 정의
//
//    inner class TimerBinder : Binder() {
//        fun getService(): TimerService = this@TimerService
//    }
//
//    override fun onBind(intent: Intent?): IBinder {
//        return binder
//    }
//
//    fun startTimer() {
//        if (isRunning) return
//        isRunning = true
//        job = coroutineScope.launch {
//            while (isRunning) {
//                delay(1000)
//                time += 1
//                onTimeUpdate?.invoke(time) // UI에 시간 업데이트 알림
//            }
//        }
//    }
//
//    fun pauseTimer() {
//        isRunning = false
//        job?.cancel()
//    }
//
//    fun resetTimer() {
//        time = 0L
//        onTimeUpdate?.invoke(time)
//    }
//
//    fun getTime(): Long = time
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // 서비스 종료 시 코루틴 취소
//        job?.cancel()
//        isRunning = false
//    }
//
//    var onTimeUpdate: ((Long) -> Unit)? = null
//}
