package com.example.androidmediaplayer

import com.google.gson.annotations.SerializedName

class mp4(@SerializedName("360") val hd360:String)
class mediaUrl(var mp4: mp4)

class body(var width:Int, var height:Int,var duration:String, var mediaUrl: mediaUrl, var poster: String)
class Video (var title:String,var description:String,var body:Array<body>)
class ListVideosModel(var data:Array<Video>)
