package com.youssef.musictask.domain.interactors

import android.os.Handler
import com.youssef.musictask.base.DataResult
import com.youssef.musictask.data.pref.PrefHelperImpl
import com.youssef.musictask.data.pref.model.SavedToken
import com.youssef.musictask.data.remote.ApiHelper
import com.youssef.musictask.domain.Mocks
import com.youssef.musictask.domain.models.Song
import java.util.concurrent.Executor

class AppInteractorImpl(
    private val apiHelper: ApiHelper,
    private val executor: Executor,
    private val handler: Handler
) :
    AppInteractor {
    private fun getSharedPref() = PrefHelperImpl.instance
    override fun isAccessTokenExpired(): Boolean {
        val savedToken = getSharedPref()?.getToken()
        return savedToken?.isExpired() == true
    }

    override fun getSongs(searchKey: String, callback: (DataResult<MutableList<Song>>) -> Unit) {
        executor.execute {
            try {
                val response =
                    apiHelper.getSongs(searchKey, getSharedPref()?.getToken()?.token ?: "")
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

    override fun getMockedSongs(): MutableList<Song> = Mocks.mockSongs()

    override fun getTokenRequest(callback: (DataResult<SavedToken>) -> Unit) {
        executor.execute {
            try {
                val response = apiHelper.getTokenRequest()
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

}