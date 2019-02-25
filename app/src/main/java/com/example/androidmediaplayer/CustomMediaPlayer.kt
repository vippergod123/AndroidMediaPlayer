package com.example.androidmediaplayer

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.LinearLayout



@SuppressLint("StaticFieldLeak")
object CustomMediaPlayer {
    private var mediaController: MediaController? = null
    private var videoURL: String? = null
    private var playBackPosition = 0
    private var videoView:VideoView? = null
    private var playingVideoFrameLayout: FrameLayout? = null
    private var titleVideo: String? = null

    private fun settingVideoPlayer (inputVideoURL: String?,context: Context, thumbnailImageView: ImageView, playImageButton: ImageButton, viewHolder: View) {
        titleVideo = viewHolder.tag.toString()

        playingVideoFrameLayout = viewHolder.findViewById(R.id.video_frame_layout)
        val thumbnailLayout = thumbnailImageView.layoutParams

        println("$videoURL - $playBackPosition"  )

        videoView = VideoView(context)
        videoURL = inputVideoURL
        videoView!!.setVideoPath(videoURL)
//        videoView!!.layoutParams = playingVideoFrameLayout.layoutParams

        val param = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        videoView!!.layoutParams = param
        playingVideoFrameLayout!!.addView(videoView)



        mediaController = MediaController(context)
        mediaController!!.setAnchorView(videoView)
        videoView!!.setMediaController(mediaController)
        videoView!!.setOnCompletionListener {
            thumbnailImageView.visibility = View.VISIBLE
            playImageButton.visibility = View.VISIBLE
            playingVideoFrameLayout!!.removeView(videoView)
        }

        videoView!!.setOnPreparedListener {
            videoView!!.seekTo(playBackPosition)
            videoView!!.start()
        }
    }

    fun playVideo(inputVideoURL: String? = null, context: Context, thumbnailImageView: ImageView, playImageButton: ImageButton, viewHolder: View) {
        if( videoView == null) {
            settingVideoPlayer(inputVideoURL, context, thumbnailImageView, playImageButton, viewHolder)
        }
        else {
            playingVideoFrameLayout!!.removeView(videoView)
            videoView = null
            playBackPosition = 0

            settingVideoPlayer(inputVideoURL,context,thumbnailImageView,playImageButton,viewHolder)
        }

    }

    fun resumeVideo(context: Context,thumbnailImageView:ImageView,playImageButton:ImageButton, viewHolder: View) {
//        if (resumeVideoView != null && !resumeVideoView.isPlaying )  {
//            resumeVideoView.visibility = View.VISIBLE
//            thumbnailImageView.visibility = View.INVISIBLE
//            playImageButton.visibility = View.INVISIBLE
//            resumeVideoView.start()
//        }
            settingVideoPlayer(videoURL,context,thumbnailImageView,playImageButton,viewHolder)
    }

    fun stopVideo(thumbnailImageView: ImageView, playImageButton: ImageButton, viewHolder: View) {
        if (videoView != null) {

            playBackPosition = videoView!!.currentPosition

            val videoFrameLayout = viewHolder.findViewById<FrameLayout>(R.id.video_frame_layout)
            videoFrameLayout.removeView(videoView)
            videoView= null
            thumbnailImageView.visibility = View.VISIBLE
            playImageButton.visibility = View.VISIBLE
        }
    }

    fun getPlayBackPosition () {
        println(playBackPosition)
    }

    fun getTitle():String {
        return titleVideo.toString()
    }

}