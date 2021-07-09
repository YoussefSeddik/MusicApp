package com.youssef.musictask.domain.interactors

import android.os.Handler
import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.PrefHelperImpl
import com.youssef.musictask.data.pref.model.Token
import com.youssef.musictask.data.remote.HttpConnectionHelper
import com.youssef.musictask.data.remote.InputStreamResponseReader
import com.youssef.musictask.domain.mapper.convertToSongs
import com.youssef.musictask.domain.mapper.convertToToken
import com.youssef.musictask.domain.models.Song
import java.util.concurrent.Executor

class AppInteractorImpl(private val executor: Executor, private val handler: Handler) :
    AppInteractor {
    private fun getSharedPref() = PrefHelperImpl.instance
    override fun isAccessTokenExpired(): Boolean {
        val savedToken = getSharedPref()?.getToken()
        return savedToken?.isExpired() == true
    }

    override fun getSongs(searchKey: String, callback: (DataResult<MutableList<Song>>) -> Unit) {
        executor.execute {
            try {
                val response = getSynchronousSongsRequest(searchKey)
                handler.post {
                    callback(response)
                }
            } catch (e: Exception) {
                handler.post {
                    callback(DataResult.Error(e))
                }
            }
        }
    }

    override fun getTokenRequest(callback: (DataResult<Token>) -> Unit) {
        executor.execute {
            try {
                val response = getSynchronousTokenRequest()
                handler.post {
                    if (response is DataResult.Success) {
                        getSharedPref()?.setToken(response.data)
                    }
                    callback(response)
                }
            } catch (e: Exception) {
                handler.post {
                    callback(DataResult.Error(e))
                }
            }
        }
    }

    private fun getSynchronousTokenRequest(): DataResult<Token> {
        val httpConnectionHelper = HttpConnectionHelper()
        val token: Token
        return try {
            val httpURLConnection = httpConnectionHelper.getTokenHttpConnection()
            val response = InputStreamResponseReader.getResponse(httpURLConnection)
            token = response.convertToToken()
            DataResult.Success<Token>(token)
        } catch (e: Exception) {
            DataResult.Error(e)
        } finally {
            httpConnectionHelper.releaseAllConnections()
        }
    }

    private fun getSynchronousSongsRequest(searchKey: String): DataResult<MutableList<Song>> {
        val httpConnectionHelper = HttpConnectionHelper()
        val songsList: MutableList<Song>
        return try {
            val httpURLConnection = httpConnectionHelper.getSongsHttpConnection(
                getSharedPref()?.getToken()?.token ?: "",
                searchKey
            )
            val response = InputStreamResponseReader.getResponse(httpURLConnection)
            songsList = response.convertToSongs()
            DataResult.Success<MutableList<Song>>(songsList)
        } catch (e: Exception) {
            DataResult.Error(e)
        } finally {
            httpConnectionHelper.releaseAllConnections()
        }
    }

}