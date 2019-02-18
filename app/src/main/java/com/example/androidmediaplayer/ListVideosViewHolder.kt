package com.example.androidmediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*

class ListVideosViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    private var playBackPosition = 0
    val titleTextView = itemView.findViewById<TextView>(R.id.title_video_text_view)
    val videoView = itemView.findViewById<VideoView>(R.id.video_view)
    val thumbnailImageView = itemView.findViewById<ImageView>(R.id.thumbnail_video_image_view)
    val videoProgressBar = itemView.findViewById<ProgressBar>(R.id.video_progress_bar)
    val playImageButton = itemView.findViewById<ImageButton>(R.id.play_image_button)
    val context = itemView.context
    init {
        itemView.setOnClickListener{

        }

        playImageButton.setOnClickListener{
            playVideoWhenTap(it.context)
        }
    }

    private fun playVideoWhenTap(context:Context)  {
        val mediaController = MediaController(context)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        thumbnailImageView.visibility = View.INVISIBLE
        videoProgressBar.visibility = View.VISIBLE
        playImageButton.visibility = View.INVISIBLE

        videoView.setOnPreparedListener {
            videoView.seekTo(playBackPosition)
            videoView.start()
        }
        videoView.setOnInfoListener { mp, what, extra ->
            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                videoProgressBar.visibility = View.INVISIBLE
            else if ( what == MediaPlayer.MEDIA_INFO_VIDEO_NOT_PLAYING)
                playImageButton.visibility = View.VISIBLE
                true
        }
    }
}