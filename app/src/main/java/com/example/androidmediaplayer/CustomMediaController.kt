package com.example.androidmediaplayer

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import java.text.SimpleDateFormat
import java.util.*


class CustomMediaController (context: Context): View(context) {
    private var playPauseButton = findViewById<Button>(R.id.play_pause_image_button)
    private var timeSeekBar = findViewById<SeekBar>(R.id.time_seek_bar)
    private var currentPositionTextView = findViewById<TextView>(R.id.current_position_time_text_view)
    private var durationTextView = findViewById<TextView>(R.id.duration_text_view)

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.custom_media_controller,null,false)
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        super.onCreateContextMenu(menu)
    }
    fun settingMediaController (videoView:VideoView, params: ViewGroup.LayoutParams, playingVideo: Video) {
        layoutParams = params
        durationTextView.text = playingVideo.body[0].duration
}
}