package com.example.androidmediaplayer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso

class ListVideosViewHolder(itemView: View)  :RecyclerView.ViewHolder(itemView) {
    private var video:Video? = null
    private val playImageButton = itemView.findViewById<ImageButton>(R.id.play_image_button)
    init {
        playImageButton.setOnClickListener{
            CustomMediaPlayer.playVideo(video,itemView.context, itemView)
        }
    }

    fun bind(inputVideo:Video) {
        val titleTextView = itemView.findViewById<TextView>(R.id.title_video_text_view)
        titleTextView.text = inputVideo.title

        val thumbnailImageView = itemView.findViewById<ImageView>(R.id.thumbnail_video_image_view)
        Picasso.get()
                .load(inputVideo.body[0].poster)
                .fit()
                .centerCrop()
                .into(thumbnailImageView)

        video = inputVideo

        itemView.tag = inputVideo.title
    }
 }