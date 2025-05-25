import android.os.Handler
import android.os.Looper

object PlayerState {
    var isPlaying = false
    var progress = 0
    const val maxProgress = 100000

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    var onProgressChanged: ((Int) -> Unit)? = null

    fun start() {
        if (isPlaying) return
        isPlaying = true
        runnable = object : Runnable {
            override fun run() {
                if (isPlaying) {
                    progress += 1000
                    if (progress >= maxProgress) {
                        progress = 0
                        pause()
                    } else {
                        onProgressChanged?.invoke(progress)
                        handler.postDelayed(this, 1000)
                    }
                }
            }
        }
        handler.post(runnable)
    }

    fun pause() {
        isPlaying = false
        handler.removeCallbacks(runnable)
    }

    fun reset() {
        pause()
        progress = 0
        onProgressChanged?.invoke(progress)
    }
}
