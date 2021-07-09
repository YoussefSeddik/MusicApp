package com.youssef.musictask.domain.models

data class Song(
    var title: String,
    var type: String,
    var artistName: String,
    var image: String,
    var publishingDate: String,
    var duration: String,
    var trackNum: String
){
    companion object{
        fun empty() = Song(
            title          = "",
            type           ="",
            artistName     = "",
            image          = "",
            publishingDate = "",
            duration       = "",
            trackNum       = ""
        )
    }
}