package com.example.androidmediaplayer

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.squareup.picasso.Picasso
import android.R.attr.bottom
import android.support.v7.widget.LinearLayoutManager


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

class CustomScrollListener: RecyclerView.OnScrollListener(){


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> println("The RecyclerView is not scrolling")
            RecyclerView.SCROLL_STATE_DRAGGING -> println("Scrolling now")
            RecyclerView.SCROLL_STATE_SETTLING -> println("Scroll Settling")
            RecyclerView.SCROLL_INDICATOR_END -> println("End")
            RecyclerView.SCROLL_INDICATOR_BOTTOM-> println("SCROLL_INDICATOR_BOTTOM")
        }
    }
}

abstract class PaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }//                    && totalItemCount >= ClothesFragment.itemsCount
        }
    }
    abstract fun loadMoreItems()
}

