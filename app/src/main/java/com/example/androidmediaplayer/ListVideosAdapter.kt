package com.example.androidmediaplayer

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
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

        val video = listVideos.data[position]
        viewHolder.bind(video)

        }
}

class CustomScrollListener: RecyclerView.OnScrollListener(){

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> println("The RecyclerView is not scrolling")
            RecyclerView.SCROLL_STATE_DRAGGING -> println("Scrolling now")
            RecyclerView.SCROLL_STATE_SETTLING -> println("Scroll Settling")
        }
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        println("$dx  -  $dy")
        if (dx > 0) {
            println("Scrolled Right")
        } else if (dx < 0) {
            println("Scrolled Left")
        } else {
            println("No Horizontal Scrolled")
        }

        if (dy > 0) {
            println("Scrolled Downwards")
        } else if (dy < 0) {
            println("Scrolled Upwards")
        } else {
            println("No Vertical Scrolled")
        }
    }
}

