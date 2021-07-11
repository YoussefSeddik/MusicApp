package com.youssef.musictask.domain.models

import com.youssef.musictask.utils.Utils.getRandomString
import java.io.Serializable

data class Song(
    var id: String,
    var title: String,
    var type: String,
    var artistName: String,
    var image: String,
    var publishingDate: String,
    var duration: String,
    var trackNum: String
) : Serializable {
    val artistNameFirstChar: String
        get() = artistName.firstOrNull()?.toString() ?: "M"

    fun getSharedElementName() = getRandomString(8) + title + trackNum + image + trackNum

    companion object {
        fun empty() = Song(
            id = "",
            title = "",
            type = "",
            artistName = "",
            image = "",
            publishingDate = "",
            duration = "00:00",
            trackNum = "0"
        )
    }
}