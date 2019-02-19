package com.example.androidmediaplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_view_holder.view.*
import android.widget.VideoView
import android.media.MediaPlayer



class ListVideosViewHolder(itemView: View)  :RecyclerView.ViewHolder(itemView) {
    private var playBackPosition = 0
    private var videoURL:String? = null

    val playImageButton = itemView.findViewById<ImageButton>(R.id.play_image_button)

    init {
//        itemView.setOnClickListener{
//            var number: IntArray = intArrayOf(0,0)
//            videoView.getLocationOnScreen(number)
//            println(number[1].toString() + " - " + number [0].toString())
//        }
        playImageButton.setOnClickListener{
            playVideoWhenTap(it.context)
        }
    }

    fun bind(video:Video) {
        val titleTextView = itemView.findViewById<TextView>(R.id.title_video_text_view)
        titleTextView.text = video.title

        val thumbnailImageView = itemView.findViewById<ImageView>(R.id.thumbnail_video_image_view)
        Picasso.get()
                .load(video.body[0].poster)
                .fit()
                .centerCrop()
                .into(thumbnailImageView)

        videoURL = video.body[0].mediaUrl.mp4.hd360
    }

    private fun playVideoWhenTap(context:Context)  {

        val thumbnailImageView = itemView.findViewById<ImageView>(R.id.thumbnail_video_image_view)
        val videoProgressBar = itemView.findViewById<ProgressBar>(R.id.video_progress_bar)


        val thumbnailLayout = thumbnailImageView.layoutParams
        val videoView = VideoView(context)
        videoView.tag = "BaoMoiVideo"
        videoView.setVideoPath(videoURL)
        videoView.layoutParams = thumbnailLayout
        itemView.video_view_holder.addView(videoView)

        thumbnailImageView.visibility = View.INVISIBLE
        videoProgressBar.visibility = View.VISIBLE
        playImageButton.visibility = View.INVISIBLE

        val mediaController = MediaController(context)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnCompletionListener {
            thumbnailImageView.visibility = View.VISIBLE
            videoProgressBar.visibility = View.INVISIBLE
            playImageButton.visibility = View.VISIBLE
        }

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