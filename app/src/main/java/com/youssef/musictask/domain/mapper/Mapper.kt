package com.youssef.musictask.domain.mapper

import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.domain.models.Song
import com.youssef.musictask.utils.Utils
import com.youssef.musictask.utils.Utils.parseStringToReadableData
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


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

fun String.getFormattedErrorMessage(): String {
    var errorMessage = ""
    val jsonObject = JSONTokener(this).nextValue() as JSONObject
    errorMessage = jsonObject.getString("description")
    val specialCharsPattern = Pattern.compile("[^a-zA-Z0-9]")
    val specialCharsMatcher: Matcher = specialCharsPattern.matcher(errorMessage)
    val removeSpacesPattern = Pattern.compile("\\s{2,}")
    val spacesMatcher: Matcher =
        removeSpacesPattern.matcher(specialCharsMatcher.replaceAll(" "))
    return spacesMatcher.replaceAll(" ")
}

fun String.convertToSongs(): MutableList<Song> {
    val songs = mutableListOf<Song>()
    val songsJSONArray: JSONArray = JSONTokener(this).nextValue() as JSONArray
    if (songsJSONArray.length() > 0) {
        for (songIndex in 0 until songsJSONArray.length()) {
            val songJsonObject: JSONObject = songsJSONArray.getJSONObject(songIndex)
            val song = songJsonObject.convertToSong()
            songs.add(song)
        }
    }
    return songs
}

fun JSONObject.convertToSong(): Song {
    val song = Song.empty()
    song.id = getString("id")
    song.title = getString("title")
    song.type = getString("type")
    if (song.type == "release") {
        song.type = "album"
    }
    song.artistName = getJSONObject("mainArtist").getString("name").trim()
    song.image = "https:" + getJSONObject("cover").getString("medium")
    song.publishingDate = parseStringToReadableData(getString("publishingDate"))
    song.duration = Utils.getDurationInMinutesAndSecondsFormat(getString("duration"))
    if (has("numberOfTracks")) {
        song.trackNum = getString("numberOfTracks")
    }
    if (has("trackNumber")) {
        song.trackNum = getString("trackNumber")
    }
    return song

}