package com.example.floclone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.floclone.data.Song
import com.example.floclone.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.util.Timer

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var gson: Gson = Gson()
    private var isRepeat: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setText() 주석
        //binding.songMusicTitleTv.setText(intent.getStringExtra("title"))
        //binding.songSingerNameTv.setText(intent.getStringExtra("singer"))


        //song 객체 초기화
        initSong()
        setSongPlayer()
        setPlayerStatus(song.isPlaying)


        binding.songMiniplayerIv.setOnClickListener(){
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener(){
            setPlayerStatus(false)
        }

        binding.songRepeatIv.setOnClickListener() {
            isRepeat = !isRepeat
            if (isRepeat) {
                binding.songRepeatIv.setColorFilter(ContextCompat.getColor(this, R.color.flo))
            } else {
                binding.songRepeatIv.clearColorFilter()
            }
        }

        binding.songDownIb.setOnClickListener(){
            val resultIntent = Intent().apply {
                putExtra("title", song.title)
                putExtra("singer",song.singer)
                putExtra("second", song.second)
                putExtra("playTime", song.playTime)
                putExtra("isPlaying", song.isPlaying)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun initSong(){
        song = Song(
            intent.getStringExtra("title") ?: "title",
            intent.getStringExtra("singer") ?: "singer",
            intent.getIntExtra("second", 0),
            intent.getIntExtra("playTime", 0),
            intent.getBooleanExtra("isPlaying", false)

        )

        //timer 초기화
        startTimer()
    }

    private fun setSongPlayer() {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = song.second * 1000 / song.playTime

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            //노래 재생
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }else{
            //노래 재생 x
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }
//
//    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
//
//        private var mills: Float = song.second * 1000f  // 이전 재생 위치 반영
//
//        override fun run() {
//            super.run()
//
//            runOnUiThread {
//                binding.songProgressSb.progress =
//                    ((mills / (playTime * 1000f)) * 100000).toInt()
//            }
//
//            while (true) {
//                if (song.second >= playTime) break
//
//                if (isPlaying) {
//                    sleep(50)
//                    mills += 50
//
//                    val currentSecond = (mills / 1000).toInt()
//
//                    // song.second가 바뀌었을 때만 갱신
//                    if (currentSecond != song.second) {
//                        song.second = currentSecond
//                        runOnUiThread {
//                            binding.songStartTimeTv.text =
//                                String.format("%02d:%02d", song.second / 60, song.second % 60)
//                        }
//                    }
//
//                    runOnUiThread {
//                        binding.songProgressSb.progress =
//                            ((mills / (playTime * 1000f)) * 100000).toInt()
//                    }
//                }
//            }
//
//
//        }
//    }
inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

    private var mills: Float = song.second * 1000f

    override fun run() {
        super.run()

        while (!isInterrupted) {
            mills = song.second * 1000f

            runOnUiThread {
                binding.songProgressSb.progress =
                    ((mills / (playTime * 1000f)) * 100000).toInt()
            }

            while (mills < playTime * 1000f && !isInterrupted) {
                if (isPlaying) {
                    sleep(50)
                    mills += 50

                    val currentSecond = (mills / 1000).toInt()
                    if (currentSecond != song.second) {
                        song.second = currentSecond
                        runOnUiThread {
                            binding.songStartTimeTv.text =
                                String.format("%02d:%02d", song.second / 60, song.second % 60)
                        }
                    }

                    runOnUiThread {
                        binding.songProgressSb.progress =
                            ((mills / (playTime * 1000f)) * 100000).toInt()
                    }
                }
            }

            if (!isRepeat || isInterrupted) break

            song.second = 0  // 처음부터 다시 시작
        }
    }
}


    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
    }

    override fun onPause(){
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000

        //내부 저장소에 데이터 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)  //private : 이 앱에서만 사용 가능한 데이터로 저장
        val editor = sharedPreferences.edit()   //에디터
        val songJson = gson.toJson(song)    //gson을 사용하여 json형태로 song데이터 저장
        editor.putString("songData",songJson)

        editor.apply()

    }


}