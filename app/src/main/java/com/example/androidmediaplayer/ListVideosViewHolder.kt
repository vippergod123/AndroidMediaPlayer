package com.example.androidmediaplayer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.VideoView

class ListVideosViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val titleTextView = itemView.findViewById<TextView>(R.id.title_video_text_view)
    val videoView = itemView.findViewById<VideoView>(R.id.video_view)
}