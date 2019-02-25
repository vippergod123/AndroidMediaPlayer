package com.example.androidmediaplayer

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.view.MotionEvent
import android.view.View.OnTouchListener




@SuppressLint("StaticFieldLeak")
object CustomMediaPlayer {
//    private var mediaController: CustomMediaController? = null
    private var mediaController: MediaController? = null
    private var customMediaController: View? = null
    private var playingVideo: Video? = null
    private var playBackPosition = 0

    private var videoView:VideoView? = null
    private var playingVideoFrameLayout: FrameLayout? = null
    private var titleVideo: String? = null

    @SuppressLint("ClickableViewAccessibility")
    private fun settingVideoPlayer (inputVideo: Video?, context: Context, viewHolder: View) {
        var videoURL: String?
        playingVideo = inputVideo
        playingVideo!!.body[0].mediaUrl.mp4.hd360.let {
            videoURL = it
        }

        titleVideo = viewHolder.tag.toString()

        playingVideoFrameLayout = viewHolder.findViewById(R.id.video_frame_layout)

        videoView = VideoView(context)


        videoView!!.setVideoPath(videoURL)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        videoView!!.layoutParams = params
        playingVideoFrameLayout!!.addView(videoView)


       settingCustomMediaController(context,params, videoView!!, playingVideoFrameLayout!!)


//        playingVideoFrameLayout!!.addView(customMediaController)

        videoView!!.setOnCompletionListener {
            removeMediaControllerAndVideoView()
        }

        videoView!!.setOnPreparedListener {
            videoView!!.seekTo(playBackPosition)
            videoView!!.start()
        }

        videoView!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->  showCustomMediaController()//Do Something
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    fun playVideo(inputVideo: Video? = null, context: Context, viewHolder: View) {
        if( videoView == null) {
            settingVideoPlayer(inputVideo, context,  viewHolder)
        }
        else {
            removeMediaControllerAndVideoView()
            playBackPosition = 0
            settingVideoPlayer(inputVideo,context,viewHolder)
        }

    }

    fun resumeVideo(context: Context,thumbnailImageView:ImageView,playImageButton:ImageButton, viewHolder: View) {
            settingVideoPlayer(playingVideo,context, viewHolder)
    }

    fun stopVideo(thumbnailImageView: ImageView, playImageButton: ImageButton, viewHolder: View) {
        if (videoView != null) {

            playBackPosition = videoView!!.currentPosition

            removeMediaControllerAndVideoView()

            thumbnailImageView.visibility = View.VISIBLE
            playImageButton.visibility = View.VISIBLE
        }
    }

    fun getTitle():String {
        return titleVideo.toString()
    }


    private fun removeMediaControllerAndVideoView() {
        playingVideoFrameLayout!!.removeView(videoView)
        playingVideoFrameLayout!!.removeView(customMediaController)
        customMediaController = null
        videoView= null
    }
    private fun settingCustomMediaController(context:Context, params: ViewGroup.LayoutParams, videoView:VideoView, playingFrameLayout: FrameLayout) {
//        mediaController = MediaController(context)
//        mediaController!!.setAnchorView(videoView)
//        videoView!!.setMediaController(mediaController)
        val layoutInflater = LayoutInflater.from(context)
        customMediaController = layoutInflater.inflate(R.layout.custom_media_controller,null,false)
        customMediaController!!.layoutParams = params
        val playButton = customMediaController!!.findViewById<ImageButton>(R.id.play_pause_image_button)
        playButton.tag = "pause"
        playButton.setOnClickListener {

            if ( playButton.tag == "pause") {
                playButton.tag = "playing"
                videoView.seekTo(playBackPosition)
                videoView.start()
                hideCustomMediaController()
                playButton.setImageResource(android.R.drawable.ic_media_pause);
            }
            else if (playButton.tag == "playing") {
                playButton.tag = "pause"
                playBackPosition = videoView.currentPosition
                videoView.pause()
                playButton.setImageResource(android.R.drawable.ic_media_play);
            }
        }

        val durationTextView = customMediaController!!.findViewById<TextView>(R.id.duration_text_view)
        durationTextView.text = playingVideo!!.body[0].duration

        val currentPositionTextView = customMediaController!!.findViewById<TextView>(R.id.current_position_time_text_view)
        currentPositionTextView.text = playBackPosition.toString()

        playingVideoFrameLayout!!.addView(customMediaController)
    }

    private fun hideCustomMediaController() {
        customMediaController!!.visibility = View.INVISIBLE
        println("hiding")
    }

    private fun showCustomMediaController() {
        customMediaController!!.visibility = View.VISIBLE
        println("showing")
    }

}