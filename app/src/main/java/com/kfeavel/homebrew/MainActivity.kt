package com.kfeavel.homebrew

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kfeavel.homebrew.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isActive = true
    private var intro: MediaPlayer? = null
    private var loop: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        emitBubbles()
        startWaves()
        startAudio()
    }

    override fun onStop() {
        super.onStop()
        isActive = false
        // Stop audio
        intro?.stop()
        intro?.release()
        loop?.stop()
        loop?.release()
    }

    private fun startAudio() {
        intro = MediaPlayer.create(this, R.raw.intro).also { intro ->
            intro.setOnCompletionListener {
                loop = MediaPlayer.create(this@MainActivity, R.raw.loop).also { loop ->
                    loop.isLooping = true
                    loop.start()
                }
            }

            intro.start()
        }
    }

    private fun startWaves() {
        binding.waveView.start()
    }

    private fun emitBubbles() {
        binding.bubbleEmitter.setColors(
            getColor(R.color.bubble),
            getColor(R.color.bubble),
            getColor(R.color.bubble))

        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                val size = nextInt(20, 80)
                binding.bubbleEmitter.emitBubble(size)
                delay(250L)
            }
        }
    }
}