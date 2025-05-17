package com.example.floclone

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.floclone.data.Song
import com.example.floclone.data.SongDatabase
import com.example.floclone.databinding.ActivitySongBinding
import com.google.gson.Gson


class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    private var gson: Gson = Gson()
    private var isRepeat: Boolean = false

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setText() 주석
        //binding.songMusicTitleTv.setText(intent.getStringExtra("title"))
        //binding.songSingerNameTv.setText(intent.getStringExtra("singer"))


        //song 객체 초기화
        initPlayList()
        initSong()
        setSongPlayer()
        setPlayerStatus(songs[nowPos].isPlaying)


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
            finish()
        }
        binding.songNextIv.setOnClickListener {
            moveSong(1)
        }
        binding.songPreviousIv.setOnClickListener{
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())
        //timer 초기화
        startTimer()
    }

    private fun moveSong(direct: Int){
        if(nowPos + direct < 0){
            Toast.makeText(this, "first song",Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this, "last song",Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct

        timer.interrupt()
        startTimer()

        setSongPlayer()
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setSongPlayer() {
        binding.songMusicTitleTv.text = songs[nowPos].title
        binding.songSingerNameTv.text = songs[nowPos].singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", songs[nowPos].second / 60, songs[nowPos].second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", songs[nowPos].playTime / 60, songs[nowPos].playTime % 60)
        binding.songAlbumIv.setImageResource(songs[nowPos].coverImg!!)
        binding.songProgressSb.progress = songs[nowPos].second * 1000 / songs[nowPos].playTime

        if(songs[nowPos].isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
        setPlayerStatus(songs[nowPos].isPlaying)
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
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

inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

    private var mills: Float = songs[nowPos].second * 1000f

    override fun run() {
        super.run()

        while (!isInterrupted) {
            mills = songs[nowPos].second * 1000f

            runOnUiThread {
                binding.songProgressSb.progress =
                    ((mills / (playTime * 1000f)) * 100000).toInt()
            }

            while (mills < playTime * 1000f && !isInterrupted) {
                if (isPlaying) {
                    sleep(50)
                    mills += 50

                    val currentSecond = (mills / 1000).toInt()
                    if (currentSecond != songs[nowPos].second) {
                        songs[nowPos].second = currentSecond
                        runOnUiThread {
                            binding.songStartTimeTv.text =
                                String.format("%02d:%02d", songs[nowPos].second / 60, songs[nowPos].second % 60)
                        }
                    }

                    runOnUiThread {
                        binding.songProgressSb.progress =
                            ((mills / (playTime * 1000f)) * 100000).toInt()
                    }
                }
            }

            if (!isRepeat || isInterrupted) break

            songs[nowPos].second = 0  // 처음부터 다시 시작
        }
    }
}


    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    override fun onPause(){
        super.onPause()

        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        //내부 저장소에 데이터 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)  //private : 이 앱에서만 사용 가능한 데이터로 저장
        val editor = sharedPreferences.edit()   //에디터

        editor.putInt("songId", songs[nowPos].id)

        editor.apply()

    }


}