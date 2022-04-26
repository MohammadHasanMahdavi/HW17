package com.example.hw17_1.ui.play

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.hw17_1.databinding.ActivityPlayBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayActivity : AppCompatActivity() {
    lateinit var player : SimpleExoPlayer
    lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.title = "Player"
        initPlayer()
    }
    fun initPlayer(){
        val adaptiveTrackSelection = AdaptiveTrackSelection.Factory()
    }
}