package com.youssef.musictask.data.remote

import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.data.remote.helpers.HttpConnectionHelper
import com.youssef.musictask.data.remote.helpers.InputStreamResponseHelper
import com.youssef.musictask.data.remote.helpers.InputStreamResponseHelper.isRequestSuccess
import com.youssef.musictask.domain.mapper.convertToSongs
import com.youssef.musictask.domain.mapper.convertToToken
import com.youssef.musictask.domain.mapper.getFormattedErrorMessage
import com.youssef.musictask.domain.models.Song
import java.net.HttpURLConnection

class ApiHelperImpl : ApiHelper {
    override fun getTokenRequest(): DataResult<SavedToken> {
        var httpURLConnection: HttpURLConnection? = null
        val savedToken: SavedToken
        return try {
            httpURLConnection = HttpConnectionHelper().getTokenHttpConnection()
            if (httpURLConnection.responseCode.isRequestSuccess()) {
                val response =
                    InputStreamResponseHelper.parseInputStream(httpURLConnection.inputStream)
                savedToken = response?.convertToToken() ?: SavedToken.empty()
                DataResult.Success<SavedToken>(savedToken)
            } else {
                val errorMessage =
                    InputStreamResponseHelper.parseInputStream(httpURLConnection.errorStream)
                DataResult.Error(java.lang.Exception(errorMessage))
            }
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
            if (httpURLConnection.responseCode.isRequestSuccess()) {
                val response =
                    InputStreamResponseHelper.parseInputStream(httpURLConnection.inputStream)
                songsList = response?.convertToSongs() ?: mutableListOf()
                DataResult.Success<MutableList<Song>>(songsList)
            } else {
                val errorResponse =
                    InputStreamResponseHelper.parseInputStream(httpURLConnection.errorStream)
                val errorMessage = errorResponse?.getFormattedErrorMessage()
                DataResult.Error(java.lang.Exception(errorMessage))
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        } finally {
            httpURLConnection?.disconnect()
        }
    }

}