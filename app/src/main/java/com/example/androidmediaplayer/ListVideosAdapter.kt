package com.example.androidmediaplayer

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController

class ListVideosAdapter(val listVideos:ListVideosModel) :RecyclerView.Adapter<ListVideosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVideosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_view_holder,parent,false)
        return ListVideosViewHolder(cellForRow)

        mediaController = MediaController(viewHolder.
            mediaController.setAnchorView(frameLayout)
                videoView.setMediaController(mediaController)
    }

    override fun getItemCount(): Int {
        return listVideos.data.size
    }

    override fun onBindViewHolder(viewHolder: ListVideosViewHolder, position: Int) {
        viewHolder.titleTextView.text = listVideos.data[position].title

        val videoURL = listVideos.data[position].body[0].mediaUrl.mp4
        viewHolder.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")

        viewHolder.videoView.setOnPreparedListener {
            viewHolder.videoView.seekTo(1)
            viewHolder.videoView.stopPlayback()
        }
    }
}