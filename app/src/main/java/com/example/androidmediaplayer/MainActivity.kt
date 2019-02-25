package com.example.androidmediaplayer

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.video_view_holder.view.*
import okhttp3.*
import java.io.IOException



class MainActivity : AppCompatActivity() {

    private lateinit var listVideosRecyclerView: RecyclerView
    lateinit var listVideos: ListVideosModel

    private var loading = true
    private var page = 0
    //region ConfigureUI
    private fun configureUI() {

        listVideosRecyclerView = findViewById(R.id.list_videos_recycler_view)
        listVideosRecyclerView.layoutManager = LinearLayoutManager(this)
        listVideosRecyclerView.setItemViewCacheSize(25)

        listVideosRecyclerView.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (view.title_video_text_view.text == CustomMediaPlayer.getTitle())
                    CustomMediaPlayer.resumeVideo(
                        view.context,
                        view.thumbnail_video_image_view,
                        view.play_image_button,
                        view
                    )
            }


            override fun onChildViewDetachedFromWindow(view: View) {
                if (view.title_video_text_view.text == CustomMediaPlayer.getTitle())
                    CustomMediaPlayer.stopVideo(view.thumbnail_video_image_view, view.play_image_button, view)
            }
        })

        listVideosRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val mLayoutManager = listVideosRecyclerView.layoutManager as LinearLayoutManager
                if (dy > 0) {
                    val visibleItemCount = mLayoutManager.childCount
                    val totalItemCount = mLayoutManager.itemCount
                    val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false
                            println("Last item")
                            page++
                            fetchDataFromApi(page)
                        }
                    }
                }
            }
        })


    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureUI()

    }

    override fun onStart() {
        super.onStart()
        fetchDataFromApi(page)
    }

    //region method
    private fun fetchDataFromApi(page: Int?) {
        val apiURL = arrayOf(
            "http://data2.baomoi.com/api/v2.0/video/byzone?imgsize=a700x&fields=title,description,date,publisherId,publisherName,publisherIcon,videoChannelId,videoChannelName,avatarUrl,avatarWidth,avatarHeight,totalComments,body,shareUrl&zone=v_-1&start=0&size=50&os=android&client_version=212&apikey=d82e4aafdbad07bce95383b732440e2f&ctime=1509939672209&sig=b5a75b50e54861fa5c7c338302dfb6d8",
            "http://data2.baomoi.com/api/v2.0/video/byzone?imgsize=a700x&fields=title,description,date,publisherId,publisherName,publisherIcon,videoChannelId,videoChannelName,avatarUrl,avatarWidth,avatarHeight,totalComments,body,shareUrl&zone=v_-1&start=50&size=50&os=android&client_version=212&apikey=d82e4aafdbad07bce95383b732440e2f&ctime=1509939760474&sig=e6be9224ce5cde5e54fe178ceccaadef",
            "http://data2.baomoi.com/api/v2.0/video/byzone?imgsize=a700x&fields=title,description,date,publisherId,publisherName,publisherIcon,videoChannelId,videoChannelName,avatarUrl,avatarWidth,avatarHeight,totalComments,body,shareUrl&zone=v_-1&start=100&size=50&os=android&client_version=212&apikey=d82e4aafdbad07bce95383b732440e2f&ctime=1509939794725&sig=71036c047075619a2a1287b680cf42f4"
        )

        val request = Request.Builder().url(apiURL[page!!]).build()
        val client = OkHttpClient()

        val dialogLoading = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                dialogLoading.dismiss()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gsonBuilder = GsonBuilder().create()
                dialogLoading.dismiss()
//                 if (body?.contains("Error 404") == true) {
//                    dialogLoading.dismiss()
//                }
//                else {
                dialogLoading.dismiss()
                val temp = gsonBuilder.fromJson(body, ListVideosModel::class.java)
                if (page == 0) {
                    runOnUiThread {
                        listVideosRecyclerView.adapter = ListVideosAdapter(temp)
                    }
                } else if (page < apiURL.size - 1) {
                    runOnUiThread {
                        loading = true
                        val recyclerViewAdapter = listVideosRecyclerView.adapter as ListVideosAdapter
                        recyclerViewAdapter.addList(temp.data)
                    }

//                     }
                }

            }

        })
    }
    //endregion

}
