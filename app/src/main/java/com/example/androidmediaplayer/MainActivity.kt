package com.example.androidmediaplayer

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var listVideosRecyclerView: RecyclerView
    private var playBackPosition = 0
    lateinit var listVideos: ListVideosModel


    //region ConfigureUI
    private fun configureUI() {

        listVideosRecyclerView = findViewById(R.id.list_videos_recycler_view)
        listVideosRecyclerView.layoutManager = LinearLayoutManager(this)

    }
    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureUI()
    }

    override fun onStart() {
        super.onStart()
        fetchDataFromApi(this)
    }

    //region method
//    private fun settingVideoPlayer(videoView:VideoView) {
//        val uri = Uri.parse(videoURL)
//        videoView.setVideoPath(videoURL)
//        videoView.setOnPreparedListener {
//            videoView.seekTo(playBackPosition)
//            videoView.start()
//        }
//        videoView.setOnInfoListener { mp, what, extra ->
//            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
//                progressBar.visibility = View.INVISIBLE
//            true
//        }
//
//    }
    private fun fetchDataFromApi(context: Context) {
        val url1 = "https://data2.baomoi.com/api/v2.0/video/byzone?imgsize=a700x&fields=title,description,date,publisherId,publisherName,publisherIcon,videoChannelId,videoChannelName,avatarUrl,avatarWidth,avatarHeight,totalComments,body,shareUrl&zone=v_-1&start=150&size=50&os=android&client_version=212&apikey=d82e4aafdbad07bce95383b732440e2f&ctime=1509939858355&sig=d98acd71e8f4a50975055810544032b7"
        val url2 = "https://backendbusticket.herokuapp.com/"

        val request = Request.Builder().url(url2).build()
        val client = OkHttpClient()

        val dialogLoading = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )
        val dialogBuilder = AlertDialog.Builder(context)

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val stringFailedToExecuteRequest = getString(R.string.failed_execute_request)
                val stringOK = getString(R.string.OK)
                dialogBuilder.setTitle(stringFailedToExecuteRequest)
                dialogBuilder.setPositiveButton(stringOK)
                { dialog, _ ->
                    dialog.dismiss()

                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gsonBuilder = GsonBuilder().create()

                 if (body?.contains("Error 404") == true) {
                    dialogLoading.dismiss()
                    val stringFailedToExecuteRequest = getString(R.string.failed_execute_request)
                    val stringOK = getString(R.string.OK)
                    dialogBuilder.setTitle(stringFailedToExecuteRequest)
                    dialogBuilder.setPositiveButton(stringOK)
                    { dialog, _ ->
                        dialog.dismiss()
                    }
                }
                else {
                    dialogLoading.dismiss()
                    listVideos = gsonBuilder.fromJson(body, ListVideosModel::class.java)
                    runOnUiThread  {
                        listVideosRecyclerView.adapter = ListVideosAdapter(listVideos)
                    }
                }

            }

        })
    }
    //endregion

}
