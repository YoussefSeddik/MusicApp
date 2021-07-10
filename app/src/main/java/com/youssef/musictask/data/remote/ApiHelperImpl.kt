package com.youssef.musictask.data.remote

import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.data.remote.helpers.HttpConnectionHelper
import com.youssef.musictask.data.remote.helpers.InputStreamResponseReader
import com.youssef.musictask.domain.mapper.convertToSongs
import com.youssef.musictask.domain.mapper.convertToToken
import com.youssef.musictask.domain.models.Song
import java.net.HttpURLConnection

class ApiHelperImpl : ApiHelper {
    override fun getTokenRequest(): DataResult<SavedToken> {
        var httpURLConnection: HttpURLConnection? = null
        val savedToken: SavedToken
        return try {
            httpURLConnection = HttpConnectionHelper().getTokenHttpConnection()
            val response = InputStreamResponseReader.getResponse(httpURLConnection)
            savedToken = response.convertToToken()
            DataResult.Success<SavedToken>(savedToken)
        } catch (e: Exception) {
            DataResult.Error(e)
        } finally {
            httpURLConnection?.disconnect()
        }
    }

    override fun getSongs(searchKey: String, token: String): DataResult<MutableList<Song>> {
        var httpURLConnection: HttpURLConnection? = null
        val songsList: MutableList<Song>
        return try {
            httpURLConnection = HttpConnectionHelper().getSongsHttpConnection(
                searchText = searchKey,
                accessToken = token
            )
            val response = InputStreamResponseReader.getResponse(httpURLConnection)
            songsList = response.convertToSongs()
            DataResult.Success<MutableList<Song>>(songsList)
        } catch (e: Exception) {
            DataResult.Error(e)
        } finally {
            httpURLConnection?.disconnect()
        }
    }

}