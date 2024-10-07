package com.mertyazi.exoplayerxml

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.mertyazi.exoplayerxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var localPlayer: ExoPlayer
    private lateinit var onlinePlayer: ExoPlayer
    private lateinit var livePlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeLocalPlayer()
        initializeOnlinePlayer()
        initializeLivePlayer()
    }

    @OptIn(UnstableApi::class)
    private fun initializeLocalPlayer() {
        localPlayer = ExoPlayer.Builder(this).build()
        binding.pvLocalSourcePlayer.player = localPlayer

        val mediaItem = MediaItem.fromUri(
            "android.resource://${application.packageName}/${R.raw.sample_video}"
        )
        localPlayer.setMediaItem(mediaItem)
        localPlayer.prepare()
        localPlayer.play()
    }

    @OptIn(UnstableApi::class)
    private fun initializeOnlinePlayer() {
        onlinePlayer = ExoPlayer.Builder(this).build()
        binding.pvOnlineSourcePlayer.player = onlinePlayer

        val mediaItem = MediaItem.fromUri(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        )
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                .createMediaSource(mediaItem)

        onlinePlayer.setMediaSource(mediaSource)
        onlinePlayer.prepare()
        onlinePlayer.play()
    }

    @OptIn(UnstableApi::class)
    private fun initializeLivePlayer() {
        livePlayer = ExoPlayer.Builder(this).build()
        binding.pvLiveStreamPlayer.player = livePlayer

        val mediaItem = MediaItem.fromUri(
            "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8"
        )
        val mediaSource: MediaSource =
            HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                .createMediaSource(mediaItem)

        livePlayer.setMediaSource(mediaSource)
        livePlayer.prepare()
        livePlayer.play()
    }

    override fun onStart() {
        super.onStart()
        localPlayer.playWhenReady = true
        onlinePlayer.playWhenReady = true
        localPlayer.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        localPlayer.playWhenReady = false
        onlinePlayer.playWhenReady = false
        localPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        localPlayer.release()
        onlinePlayer.release()
        localPlayer.release()
    }

}