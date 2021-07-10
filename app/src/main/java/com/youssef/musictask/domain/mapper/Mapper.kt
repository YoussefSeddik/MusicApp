package com.youssef.musictask.domain.mapper

import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.domain.models.Song
import com.youssef.musictask.utils.Utils.getRandomString
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*


fun String.convertToToken(): SavedToken {
    val token = SavedToken.empty()
    val jsonObject = JSONTokener(this).nextValue() as JSONObject
    token.token = jsonObject.getString("accessToken")
    val validUntilInSeconds = jsonObject.getString("expiresIn").toInt()
    token.expiredAt =
        Calendar.getInstance()
            .apply { add(Calendar.SECOND, validUntilInSeconds) }.timeInMillis
    return token
}

fun String.convertToSongs(): MutableList<Song> {
    val songs = mutableListOf<Song>()
    val songsJSONArray = JSONTokener(this).nextValue() as JSONArray
    if (songsJSONArray.length() > 0) {
        val song = Song.empty()
        for (songIndex in 0 until songsJSONArray.length()) {
            song.id = getRandomString(8)
            song.title = songsJSONArray.getJSONObject(songIndex).getString("title")
            song.type = songsJSONArray.getJSONObject(songIndex).getString("type")
            song.artistName = songsJSONArray.getJSONObject(songIndex).getJSONObject("mainArtist").getString("name")
            song.image = songsJSONArray.getJSONObject(songIndex).getJSONObject("cover").getString("medium")
            song.publishingDate = songsJSONArray.getJSONObject(songIndex).getString("publishingDate")
            song.duration = songsJSONArray.getJSONObject(songIndex).getString("duration")
            song.trackNum = songsJSONArray.getJSONObject(songIndex).getString("trackNumber")
            songs.add(song)
        }
    }
    return songs
}