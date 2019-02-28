package com.example.androidmediaplayer

object HandleDateTime {
    fun timeToMiliSeconds(time:String):Float {
        val timeSplit = time.split(":")
        return timeSplit[0].toFloat() * 60 * 60 * 1000 + timeSplit[1].toFloat() * 60 * 1000 + timeSplit[2].toFloat() * 1000
    }

    fun miliSecondToTime (mil: Int):String {
        val hours = mil / 1000 / 1000 / 60
        val minutes = mil / 1000 / 60
        val seconds = mil / 1000 % 60

        var hourString = hours.toString()
        var minString = minutes.toString()
        var secString = seconds.toString()

        if (hours < 10) {
            hourString = "0$hours"
        }

        if ( minutes < 10) {
            minString = "0$minutes"
        }

        if (seconds < 10) {
            secString = "0$seconds"
        }
        return "$hourString:$minString:$secString"
    }
 }