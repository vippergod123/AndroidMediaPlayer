package com.example.androidmediaplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


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

        val video = listVideos.data[position]
        viewHolder.bind(video)

    }

    fun addList ( data : ArrayList<Video>) {
        for (each in data) {
            listVideos.data.add(each)
        }
        notifyDataSetChanged()
    }
}

