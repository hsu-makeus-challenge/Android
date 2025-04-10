package com.example.floclone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.floclone.data.Song
import com.example.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

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

//    inner class Timer(private val playTime : Int, var isPlaying : Boolean = true): Thread(){
//
//        private var second : Int = 0
//        private var mills : Float = 0f
//
//        override fun run() {
//            super.run()
//            while(true){
//                if(second >= playTime){
//                    break
//                }
//
//                if(isPlaying){
//                    sleep(50)
//                    mills += 50
//
//                    runOnUiThread {
//                        binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
//                    }
//                    if(mills % 1000 == 0f){
//                        second++
//                        song.second++
//
//                        runOnUiThread{
//                            binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
//                        }
//                    }
//                }
//            }
//        }
//    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

        private var mills: Float = song.second * 1000f  // 이전 재생 위치 반영

        override fun run() {
            super.run()

            runOnUiThread {
                binding.songProgressSb.progress =
                    ((mills / (playTime * 1000f)) * 100000).toInt()
            }

            while (true) {
                if (song.second >= playTime) break

                if (isPlaying) {
                    sleep(50)
                    mills += 50

                    val currentSecond = (mills / 1000).toInt()

                    // song.second가 바뀌었을 때만 갱신
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
        }
    }


}