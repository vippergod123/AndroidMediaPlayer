package com.example.androidmediaplayer

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.widget.*
import android.R.attr.duration
import android.view.*
import android.view.GestureDetector.OnDoubleTapListener


@SuppressLint("StaticFieldLeak")
object CustomMediaPlayer {

    private var customMediaController: View? = null
    private var playingVideo: Video? = null
    private var playBackPosition = 0
    private var videoView: VideoView? = null
    private var playingVideoFrameLayout: FrameLayout? = null
    private var titleVideo: String? = null
    private var showAndHideMediaControllerHandler:Handler = Handler()


    @SuppressLint("ClickableViewAccessibility")
    private fun settingVideoPlayer(inputVideo: Video?, context: Context, viewHolder: View) {
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

        videoView!!.setOnCompletionListener {
            removeMediaControllerAndVideoView()
            // when complete , scroll effect not play the previous video
            titleVideo = null
            playBackPosition = 0
        }


        videoView!!.start()
        settingCustomMediaController(context, params, videoView!!)

        val playPauseButton = customMediaController!!.findViewById<ImageButton>(R.id.play_pause_image_button)
        videoView!!.setOnPreparedListener {
            videoView!!.seekTo(playBackPosition)
            startTrackingPositionVideoView()


            playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
        }

        videoView!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (playPauseButton.visibility == View.VISIBLE)
                            hideCustomMediaController()
                        else if (playPauseButton.visibility == View.INVISIBLE)
                            showCustomMediaController()
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })


    }

    fun playVideo(inputVideo: Video? = null, context: Context, viewHolder: View) {
        playBackPosition = 0
        if (videoView == null) {
            settingVideoPlayer(inputVideo, context, viewHolder)
        } else {
            removeMediaControllerAndVideoView()
            settingVideoPlayer(inputVideo, context, viewHolder)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun settingCustomMediaController(context: Context, params: ViewGroup.LayoutParams, videoView: VideoView) {


        val layoutInflater = LayoutInflater.from(context)
        customMediaController = layoutInflater.inflate(R.layout.custom_media_controller, null, false)
        customMediaController!!.layoutParams = params


        val durationTextView = customMediaController!!.findViewById<TextView>(R.id.duration_text_view)
        val currentPositionTextView = customMediaController!!.findViewById<TextView>(R.id.current_position_time_text_view)
        val playPauseButton = customMediaController!!.findViewById<ImageButton>(R.id.play_pause_image_button)
        val loadingProgressBar = customMediaController!!.findViewById<ProgressBar>(R.id.loading_progress_bar)
        val timeSeekBar = customMediaController!!.findViewById<SeekBar>(R.id.time_seek_bar)
        val videoDuration = HandleDateTime.timeToMiliSeconds(playingVideo!!.body[0].duration).toInt()

        durationTextView.text = playingVideo!!.body[0].duration.split(".")[0]
        timeSeekBar.max = videoDuration
        currentPositionTextView.text = HandleDateTime.miliSecondToTime(playBackPosition)
        playingVideoFrameLayout!!.addView(customMediaController)

        playPauseButton.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.seekTo(playBackPosition)
                videoView.start()
                startTrackingPositionVideoView()
                hideCustomMediaController()
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause)

            }
            else if (videoView.isPlaying) {
                playBackPosition = videoView.currentPosition
                videoView.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
                showCustomMediaController()
            }
        }

        videoView.setOnInfoListener { mp, what, extra ->
            when (what) {
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                    playPauseButton!!.visibility = View.VISIBLE
                    loadingProgressBar.visibility = View.GONE
                    startTrackingPositionVideoView()

                }
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                    playPauseButton!!.visibility = View.INVISIBLE
                    loadingProgressBar.visibility = View.VISIBLE
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                    playPauseButton!!.visibility = View.INVISIBLE
                    loadingProgressBar.visibility = View.GONE
                }
                else -> loadingProgressBar.visibility = View.GONE
            }
            true
        }


        timeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentPositionTextView.text = HandleDateTime.miliSecondToTime(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                videoView.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = HandleDateTime.timeToMiliSeconds(currentPositionTextView.text.toString())
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
                videoView.seekTo(progress.toInt())
                videoView.start()
                startTrackingPositionVideoView()
            }
        })



    }

    //region Resume, Pause Video
    fun resumeVideo() {
        videoView!!.visibility = View.VISIBLE
        customMediaController!!.visibility = View.VISIBLE
        videoView!!.seekTo(playBackPosition)
        videoView!!.start()
    }

    fun pauseVideo() {
        videoView!!.visibility = View.INVISIBLE
        customMediaController!!.visibility = View.INVISIBLE
        playBackPosition = videoView!!.currentPosition
        videoView!!.pause()
    }

    fun getPlayingTitleVideo(): String? {
        return titleVideo
    }
    //endregion

    //region Show, Hide, Remove, Tracking MediaController

    private fun hideCustomMediaController() {
        val playPauseButton = customMediaController!!.findViewById<ImageButton>(R.id.play_pause_image_button)
        playPauseButton.visibility = View.INVISIBLE
        showAndHideMediaControllerHandler.removeCallbacksAndMessages(null)
    }
    private fun showCustomMediaController() {
        val playPauseButton = customMediaController!!.findViewById<ImageButton>(R.id.play_pause_image_button)
        playPauseButton.visibility = View.VISIBLE
        showAndHideMediaControllerHandler.postDelayed({
            playPauseButton.visibility = View.INVISIBLE
        }, 3000)
    }

    private fun startTrackingPositionVideoView (){
        val currentPositionTextView = customMediaController!!.findViewById<TextView>(R.id.current_position_time_text_view)
        val timeSeekBar = customMediaController!!.findViewById<SeekBar>(R.id.time_seek_bar)

        Thread(Runnable {
            if ( videoView != null) {
                do {
                    if (videoView == null) break
                    if (!videoView!!.isPlaying) break

                    currentPositionTextView.post {
                        timeSeekBar.progress = videoView!!.currentPosition
                        playBackPosition = videoView!!.currentPosition
                        currentPositionTextView.text = HandleDateTime.miliSecondToTime(videoView!!.currentPosition)
                    }
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }


                } while ( videoView != null && videoView!!.currentPosition < duration)
            }
        }).start()
    }


    private fun removeMediaControllerAndVideoView() {
        playingVideoFrameLayout!!.removeView(videoView)
        playingVideoFrameLayout!!.removeView(customMediaController)
        customMediaController = null
        videoView = null
    }
    //endregion
}

