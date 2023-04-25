package com.kfeavel.homebrew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kfeavel.homebrew.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        emitBubbles()
        startWaves()
    }

    override fun onStop() {
        super.onStop()
        isActive = false
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