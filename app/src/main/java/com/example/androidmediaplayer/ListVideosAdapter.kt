package com.example.androidmediaplayer

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import com.squareup.picasso.Picasso

class ListVideosAdapter(val listVideos:ListVideosModel) :RecyclerView.Adapter<ListVideosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVideosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_view_holder,parent,false)
        return ListVideosViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return listVideos.data.size
    }

    override fun onBindViewHolder(viewHolder: ListVideosViewHolder, position: Int) {
        val videoURL = listVideos.data[position].body[0].mediaUrl.mp4
        val poster = listVideos.data[position].body[0].poster
        viewHolder.titleTextView.text = listVideos.data[position].title
        Picasso.get().load(poster).into(viewHolder.thumbnailImageView)

        // Configure videoView
        val videoView = viewHolder.videoView
        val context = viewHolder.context
        viewHolder.videoView.setVideoPath("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
    }


}